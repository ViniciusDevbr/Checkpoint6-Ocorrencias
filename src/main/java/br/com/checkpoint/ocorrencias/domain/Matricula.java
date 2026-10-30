package br.com.checkpoint.ocorrencias.domain;
import jakarta.persistence.*; import java.time.LocalDate;
@Entity @Table(name="matriculas", uniqueConstraints=@UniqueConstraint(columnNames={"aluno_id","turma_id"}))
public class Matricula {
 @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
 @ManyToOne(optional=false) private Aluno aluno;
 @ManyToOne(optional=false) private Turma turma;
 @Column(nullable=false) private LocalDate dataMatricula=LocalDate.now();
 private LocalDate dataEncerramento;
 @Column(nullable=false) private boolean ativa=true;
 protected Matricula(){}
 public Matricula(Aluno a,Turma t){aluno=a;turma=t;}
 public Long getId(){return id;} public Aluno getAluno(){return aluno;} public Turma getTurma(){return turma;} public LocalDate getDataMatricula(){return dataMatricula;} public LocalDate getDataEncerramento(){return dataEncerramento;} public boolean isAtiva(){return ativa;}
 public void encerrar(){ativa=false;dataEncerramento=LocalDate.now();}
}
