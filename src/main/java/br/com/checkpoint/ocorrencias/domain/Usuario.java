package br.com.checkpoint.ocorrencias.domain;
import jakarta.persistence.*;
import java.time.LocalDateTime;
@Entity @Table(name="usuarios", uniqueConstraints=@UniqueConstraint(columnNames="login"))
public class Usuario {
 @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
 @Column(nullable=false,length=120) private String nome;
 @Column(nullable=false,length=80,unique=true) private String login;
 @Column(nullable=false) private String senha;
 @Enumerated(EnumType.STRING) @Column(nullable=false,length=30) private Perfil perfil;
 @Column(nullable=false) private boolean ativo=true;
 @Column(nullable=false) private LocalDateTime criadoEm=LocalDateTime.now();
 protected Usuario(){}
 public Usuario(String nome,String login,String senha,Perfil perfil){this.nome=nome;this.login=login;this.senha=senha;this.perfil=perfil;}
 public Long getId(){return id;} public String getNome(){return nome;} public String getLogin(){return login;} public String getSenha(){return senha;} public Perfil getPerfil(){return perfil;} public boolean isAtivo(){return ativo;} public LocalDateTime getCriadoEm(){return criadoEm;}
 public void atualizar(String nome,String login,Perfil perfil){this.nome=nome;this.login=login;this.perfil=perfil;}
 public void setSenha(String senha){this.senha=senha;} public void setAtivo(boolean ativo){this.ativo=ativo;}
}
