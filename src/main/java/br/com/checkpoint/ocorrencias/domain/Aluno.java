package br.com.checkpoint.ocorrencias.domain;
import jakarta.persistence.*; import java.time.LocalDate;
@Entity @Table(name="alunos")
public class Aluno {
 @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
 @Column(nullable=false,length=120) private String nome;
 @Column(nullable=false) private LocalDate dataNascimento;
 @Column(length=500) private String fotoUrl;
 @Column(nullable=false) private boolean ativo=true;
 protected Aluno(){}
 public Aluno(String nome,LocalDate dataNascimento,String fotoUrl){this.nome=nome;this.dataNascimento=dataNascimento;this.fotoUrl=fotoUrl;}
 public Long getId(){return id;} public String getNome(){return nome;} public LocalDate getDataNascimento(){return dataNascimento;} public String getFotoUrl(){return fotoUrl;} public boolean isAtivo(){return ativo;}
 public void atualizar(String nome,LocalDate nascimento,String foto){this.nome=nome;this.dataNascimento=nascimento;this.fotoUrl=foto;} public void setAtivo(boolean a){ativo=a;}
}
