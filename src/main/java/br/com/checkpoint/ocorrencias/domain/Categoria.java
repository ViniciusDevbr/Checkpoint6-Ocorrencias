package br.com.checkpoint.ocorrencias.domain;
import jakarta.persistence.*;
@Entity @Table(name="categorias",uniqueConstraints=@UniqueConstraint(columnNames="nome"))
public class Categoria {
 @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
 @Column(nullable=false,unique=true,length=80) private String nome;
 @Column(length=300) private String descricao;
 @Column(nullable=false) private boolean ativa=true;
 protected Categoria(){}
 public Categoria(String n,String d){nome=n;descricao=d;}
 public Long getId(){return id;} public String getNome(){return nome;} public String getDescricao(){return descricao;} public boolean isAtiva(){return ativa;}
 public void atualizar(String n,String d){nome=n;descricao=d;} public void setAtiva(boolean a){ativa=a;}
}
