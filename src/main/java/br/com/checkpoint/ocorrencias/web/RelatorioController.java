package br.com.checkpoint.ocorrencias.web;
import br.com.checkpoint.ocorrencias.domain.*; import br.com.checkpoint.ocorrencias.dto.ApiDtos.DashboardView; import br.com.checkpoint.ocorrencias.repository.OcorrenciaRepository;
import org.springframework.format.annotation.DateTimeFormat; import org.springframework.security.access.prepost.PreAuthorize; import org.springframework.web.bind.annotation.*;
import java.time.*; import java.time.format.DateTimeFormatter; import java.util.*; import java.util.stream.*;
@RestController @RequestMapping("/api/relatorios")
@PreAuthorize("hasAnyRole('ANALISTA_QUALIDADE','COORDENADOR')")
public class RelatorioController {
 private final OcorrenciaRepository repo; public RelatorioController(OcorrenciaRepository r){repo=r;}
 @GetMapping("/dashboard") @PreAuthorize("hasRole('COORDENADOR')") public DashboardView dashboard(@RequestParam(required=false) @DateTimeFormat(iso=DateTimeFormat.ISO.DATE) LocalDate inicio,@RequestParam(required=false) @DateTimeFormat(iso=DateTimeFormat.ISO.DATE) LocalDate fim){
  var dados=filtrar(inicio,fim);Map<String,Long> cat=dados.stream().collect(Collectors.groupingBy(o->o.getCategoria().getNome(),TreeMap::new,Collectors.counting()));
  Map<String,Long> turma=dados.stream().collect(Collectors.groupingBy(o->o.getTurma().getNome(),TreeMap::new,Collectors.counting()));
  Map<String,Long> mes=dados.stream().collect(Collectors.groupingBy(o->o.getData().format(DateTimeFormatter.ofPattern("MM/yyyy")),TreeMap::new,Collectors.counting()));
  List<Map<String,Object>> reinc=dados.stream().collect(Collectors.groupingBy(o->o.getAluno().getId()+"|"+o.getAluno().getNome(),Collectors.counting())).entrySet().stream().filter(e->e.getValue()>1).sorted(Map.Entry.<String,Long>comparingByValue().reversed()).limit(10).map(e->{String[] p=e.getKey().split("\\|",2);return Map.<String,Object>of("alunoId",Long.valueOf(p[0]),"aluno",p[1],"total",e.getValue());}).toList();
  return new DashboardView(dados.size(),cat,turma,reinc,mes);
 }
 @GetMapping("/ocorrencias") public List<Map<String,Object>> relatorio(@RequestParam(required=false) @DateTimeFormat(iso=DateTimeFormat.ISO.DATE) LocalDate inicio,@RequestParam(required=false) @DateTimeFormat(iso=DateTimeFormat.ISO.DATE) LocalDate fim,@RequestParam(required=false) Long turmaId,@RequestParam(required=false) Long alunoId,@RequestParam(required=false) Long categoriaId){
  return filtrar(inicio,fim).stream().filter(o->turmaId==null||o.getTurma().getId().equals(turmaId)).filter(o->alunoId==null||o.getAluno().getId().equals(alunoId)).filter(o->categoriaId==null||o.getCategoria().getId().equals(categoriaId)).map(o->Map.<String,Object>of("id",o.getId(),"data",o.getData(),"hora",o.getHora(),"aluno",o.getAluno().getNome(),"turma",o.getTurma().getNome(),"categoria",o.getCategoria().getNome(),"tipo",o.getTipo().getNome(),"professor",o.getProfessor().getNome(),"descricao",o.getDescricao())).toList();
 }
 private List<Ocorrencia> filtrar(LocalDate i,LocalDate f){if(i!=null&&f!=null&&f.isBefore(i))throw new IllegalArgumentException("Período inválido");return repo.findAll().stream().filter(o->i==null||!o.getData().isBefore(i)).filter(o->f==null||!o.getData().isAfter(f)).toList();}
}
