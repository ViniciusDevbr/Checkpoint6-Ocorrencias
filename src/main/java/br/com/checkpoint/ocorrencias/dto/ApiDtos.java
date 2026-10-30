package br.com.checkpoint.ocorrencias.dto;
import br.com.checkpoint.ocorrencias.domain.*;
import jakarta.validation.constraints.*;
import java.time.*;
public final class ApiDtos {
 private ApiDtos(){}
 public record LoginRequest(@NotBlank String login,@NotBlank String senha){}
 public record LoginResponse(String token,String tipo,Long id,String nome,String login,Perfil perfil){}
 public record UsuarioRequest(@NotBlank String nome,@NotBlank String login,@Size(min=8) String senha,@NotNull Perfil perfil){}
 public record UsuarioView(Long id,String nome,String login,Perfil perfil,boolean ativo){}
 public record AlunoRequest(@NotBlank String nome,@NotNull LocalDate dataNascimento,String fotoUrl){}
 public record AlunoView(Long id,String nome,LocalDate dataNascimento,String fotoUrl,boolean ativo){}
 public record TurmaRequest(@NotBlank String nome,@NotNull Turno turno,@NotNull @Min(2000) Integer ano,@NotNull @Min(1) @Max(2) Integer semestre){}
 public record TurmaView(Long id,String nome,Turno turno,Integer ano,Integer semestre,boolean ativa){}
 public record CategoriaRequest(@NotBlank String nome,String descricao){}
 public record CategoriaView(Long id,String nome,String descricao,boolean ativa){}
 public record TipoRequest(@NotBlank String nome,String descricao,@NotNull Long categoriaId){}
 public record TipoView(Long id,String nome,String descricao,Long categoriaId,String categoria,boolean ativo){}
 public record MatriculaRequest(@NotNull Long alunoId,@NotNull Long turmaId){}
 public record MatriculaView(Long id,Long alunoId,String aluno,Long turmaId,String turma,LocalDate dataMatricula,boolean ativa){}
 public record OcorrenciaRequest(@NotNull Long turmaId,@NotNull Long alunoId,@NotNull Long categoriaId,@NotNull Long tipoId,@NotNull LocalDate data,@NotNull LocalTime hora,@NotBlank @Size(max=3000) String descricao){}
 public record OcorrenciaView(Long id,Long turmaId,String turma,Long alunoId,String aluno,Long professorId,String professor,Long categoriaId,String categoria,Long tipoId,String tipo,LocalDate data,LocalTime hora,String descricao,LocalDateTime criadaEm){}
 public record DashboardView(long totalOcorrencias,java.util.Map<String,Long> porCategoria,java.util.Map<String,Long> porTurma,java.util.List<java.util.Map<String,Object>> reincidentes,java.util.Map<String,Long> porMes){}
}
