package br.com.checkpoint.ocorrencias.web;
import br.com.checkpoint.ocorrencias.domain.*; import br.com.checkpoint.ocorrencias.dto.ApiDtos.*; import br.com.checkpoint.ocorrencias.repository.*;
import jakarta.validation.Valid; import org.springframework.format.annotation.DateTimeFormat; import org.springframework.security.access.prepost.PreAuthorize; import org.springframework.web.bind.annotation.*;
import java.time.*; import java.util.*; import java.util.stream.*;
@RestController @RequestMapping("/api/ocorrencias")
public class OcorrenciaController {
 private final OcorrenciaRepository ocorrencias; private final AlunoRepository alunos; private final TurmaRepository turmas; private final CategoriaRepository categorias; private final TipoOcorrenciaRepository tipos; private final MatriculaRepository matriculas;
 public OcorrenciaController(OcorrenciaRepository o,AlunoRepository a,TurmaRepository t,CategoriaRepository c,TipoOcorrenciaRepository tp,MatriculaRepository m){ocorrencias=o;alunos=a;turmas=t;categorias=c;tipos=tp;matriculas=m;}
 @PostMapping @PreAuthorize("hasRole('PROFESSOR')") public OcorrenciaView criar(@Valid @RequestBody OcorrenciaRequest r,org.springframework.security.core.Authentication auth){
  Usuario u=(Usuario)auth.getPrincipal();Turma t=turmas.findById(r.turmaId()).orElseThrow(()->new NoSuchElementException("Turma não encontrada"));Aluno a=alunos.findById(r.alunoId()).orElseThrow(()->new NoSuchElementException("Aluno não encontrado"));Categoria c=categorias.findById(r.categoriaId()).orElseThrow(()->new NoSuchElementException("Categoria não encontrada"));TipoOcorrencia tp=tipos.findById(r.tipoId()).orElseThrow(()->new NoSuchElementException("Tipo não encontrado"));
  if(!t.isAtiva()||!a.isAtivo()||!c.isAtiva()||!tp.isAtivo())throw new IllegalArgumentException("Não é permitido usar registros inativos");
  if(matriculas.findByAlunoIdAndTurmaId(a.getId(),t.getId()).filter(Matricula::isAtiva).isEmpty())throw new IllegalArgumentException("O aluno não possui matrícula ativa nesta turma");
  if(!tp.getCategoria().getId().equals(c.getId()))throw new IllegalArgumentException("O tipo selecionado não pertence à categoria informada");
  return view(ocorrencias.save(new Ocorrencia(t,a,u,c,tp,r.data(),r.hora(),r.descricao())));
 }
 @GetMapping @PreAuthorize("hasAnyRole('PROFESSOR','ANALISTA_QUALIDADE','COORDENADOR')")
 public List<OcorrenciaView> listar(@RequestParam(required=false) Long alunoId,@RequestParam(required=false) Long turmaId,@RequestParam(required=false) Long categoriaId,@RequestParam(required=false) Long tipoId,@RequestParam(required=false) @DateTimeFormat(iso=DateTimeFormat.ISO.DATE) LocalDate inicio,@RequestParam(required=false) @DateTimeFormat(iso=DateTimeFormat.ISO.DATE) LocalDate fim,org.springframework.security.core.Authentication auth){
  if(inicio!=null&&fim!=null&&fim.isBefore(inicio))throw new IllegalArgumentException("A data final não pode ser anterior à inicial");
  return ocorrencias.findAll().stream()
   .filter(o->alunoId==null||o.getAluno().getId().equals(alunoId)).filter(o->turmaId==null||o.getTurma().getId().equals(turmaId))
   .filter(o->categoriaId==null||o.getCategoria().getId().equals(categoriaId)).filter(o->tipoId==null||o.getTipo().getId().equals(tipoId))
   .filter(o->inicio==null||!o.getData().isBefore(inicio)).filter(o->fim==null||!o.getData().isAfter(fim))
   .sorted(Comparator.comparing(Ocorrencia::getData).thenComparing(Ocorrencia::getHora).reversed()).map(this::view).toList();
 }
 @GetMapping("/{id}") @PreAuthorize("hasAnyRole('PROFESSOR','ANALISTA_QUALIDADE','COORDENADOR')")
 public OcorrenciaView detalhar(@PathVariable Long id,org.springframework.security.core.Authentication auth){var o=buscar(id);return view(o);}
 @PutMapping("/{id}") @PreAuthorize("hasRole('PROFESSOR')") public OcorrenciaView atualizar(@PathVariable Long id,@Valid @RequestBody OcorrenciaRequest r,org.springframework.security.core.Authentication auth){var o=buscar(id);Usuario u=(Usuario)auth.getPrincipal();dono(o,u);var t=turmas.findById(r.turmaId()).orElseThrow(()->new NoSuchElementException("Turma não encontrada"));var a=alunos.findById(r.alunoId()).orElseThrow(()->new NoSuchElementException("Aluno não encontrado"));var c=categorias.findById(r.categoriaId()).orElseThrow(()->new NoSuchElementException("Categoria não encontrada"));var tp=tipos.findById(r.tipoId()).orElseThrow(()->new NoSuchElementException("Tipo não encontrado"));if(matriculas.findByAlunoIdAndTurmaId(a.getId(),t.getId()).filter(Matricula::isAtiva).isEmpty())throw new IllegalArgumentException("Aluno sem matrícula ativa na turma");if(!tp.getCategoria().getId().equals(c.getId()))throw new IllegalArgumentException("Tipo não pertence à categoria");o.atualizar(t,a,c,tp,r.data(),r.hora(),r.descricao());return view(ocorrencias.save(o));}
 @DeleteMapping("/{id}") @PreAuthorize("hasRole('PROFESSOR')") public void excluir(@PathVariable Long id,org.springframework.security.core.Authentication auth){var o=buscar(id);dono(o,(Usuario)auth.getPrincipal());ocorrencias.delete(o);}
 private Ocorrencia buscar(Long id){return ocorrencias.findById(id).orElseThrow(()->new NoSuchElementException("Ocorrência não encontrada"));}
 private void dono(Ocorrencia o,Usuario u){if(!o.getProfessor().getId().equals(u.getId()))throw new org.springframework.security.access.AccessDeniedException("Só é permitido alterar ou excluir suas próprias ocorrências");}
 private OcorrenciaView view(Ocorrencia o){return new OcorrenciaView(o.getId(),o.getTurma().getId(),o.getTurma().getNome(),o.getAluno().getId(),o.getAluno().getNome(),o.getProfessor().getId(),o.getProfessor().getNome(),o.getCategoria().getId(),o.getCategoria().getNome(),o.getTipo().getId(),o.getTipo().getNome(),o.getData(),o.getHora(),o.getDescricao(),o.getCriadaEm());}
}
