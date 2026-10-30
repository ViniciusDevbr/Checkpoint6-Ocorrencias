package br.com.checkpoint.ocorrencias.domain;
import jakarta.persistence.*; import java.time.LocalDate; import java.time.LocalTime; import java.time.LocalDateTime;
@Entity @Table(name="ocorrencias")
public class Ocorrencia {
 @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
 @ManyToOne(optional=false) private Turma turma;
 @ManyToOne(optional=false) private Aluno aluno;
 @ManyToOne(optional=false) private Usuario professor;
 @ManyToOne(optional=false) private Categoria categoria;
 @ManyToOne(optional=false) private TipoOcorrencia tipo;
 @Column(nullable=false) private LocalDate data;
 @Column(nullable=false) private LocalTime hora;
 @Column(nullable=false,length=3000) private String descricao;
 @Column(nullable=false) private LocalDateTime criadaEm=LocalDateTime.now();
 protected Ocorrencia(){}
 public Ocorrencia(Turma turma,Aluno aluno,Usuario professor,Categoria categoria,TipoOcorrencia tipo,LocalDate data,LocalTime hora,String descricao){this.turma=turma;this.aluno=aluno;this.professor=professor;this.categoria=categoria;this.tipo=tipo;this.data=data;this.hora=hora;this.descricao=descricao;}
 public Long getId(){return id;} public Turma getTurma(){return turma;} public Aluno getAluno(){return aluno;} public Usuario getProfessor(){return professor;} public Categoria getCategoria(){return categoria;} public TipoOcorrencia getTipo(){return tipo;} public LocalDate getData(){return data;} public LocalTime getHora(){return hora;} public String getDescricao(){return descricao;} public LocalDateTime getCriadaEm(){return criadaEm;}
 public void atualizar(Turma t,Aluno a,Categoria c,TipoOcorrencia tp,LocalDate d,LocalTime h,String desc){turma=t;aluno=a;categoria=c;tipo=tp;data=d;hora=h;descricao=desc;}
}
