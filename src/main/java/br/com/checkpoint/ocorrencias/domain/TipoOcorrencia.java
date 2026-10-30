package br.com.checkpoint.ocorrencias.domain;
import jakarta.persistence.*;
@Entity @Table(name="tipos_ocorrencia",uniqueConstraints=@UniqueConstraint(columnNames={"nome","categoria_id"}))
public class TipoOcorrencia {
 @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
 @Column(nullable=false,length=100) private String nome;
 @Column(length=300) private String descricao;
 @ManyToOne(optional=false) private Categoria categoria;
 @Column(nullable=false) private boolean ativo=true;
 protected TipoOcorrencia(){}
 public TipoOcorrencia(String n,String d,Categoria c){nome=n;descricao=d;categoria=c;}
 public Long getId(){return id;} public String getNome(){return nome;} public String getDescricao(){return descricao;} public Categoria getCategoria(){return categoria;} public boolean isAtivo(){return ativo;}
 public void atualizar(String n,String d,Categoria c){nome=n;descricao=d;categoria=c;} public void setAtivo(boolean a){ativo=a;}
}
