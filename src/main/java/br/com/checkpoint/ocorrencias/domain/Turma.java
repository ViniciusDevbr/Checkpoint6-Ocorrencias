package br.com.checkpoint.ocorrencias.domain;
import jakarta.persistence.*;
@Entity @Table(name="turmas")
public class Turma {
 @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
 @Column(nullable=false,length=80) private String nome;
 @Enumerated(EnumType.STRING) @Column(nullable=false) private Turno turno;
 @Column(nullable=false) private Integer ano;
 @Column(nullable=false) private Integer semestre;
 @Column(nullable=false) private boolean ativa=true;
 protected Turma(){}
 public Turma(String nome,Turno turno,Integer ano,Integer semestre){this.nome=nome;this.turno=turno;this.ano=ano;this.semestre=semestre;}
 public Long getId(){return id;} public String getNome(){return nome;} public Turno getTurno(){return turno;} public Integer getAno(){return ano;} public Integer getSemestre(){return semestre;} public boolean isAtiva(){return ativa;}
 public void atualizar(String n,Turno t,Integer a,Integer s){nome=n;turno=t;ano=a;semestre=s;} public void setAtiva(boolean a){ativa=a;}
}
