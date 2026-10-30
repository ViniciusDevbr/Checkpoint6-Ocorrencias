package br.com.checkpoint.ocorrencias.config;
import br.com.checkpoint.ocorrencias.domain.*; import br.com.checkpoint.ocorrencias.repository.*;
import org.springframework.boot.CommandLineRunner; import org.springframework.context.annotation.Bean; import org.springframework.context.annotation.Configuration; import org.springframework.security.crypto.password.PasswordEncoder;
@Configuration
public class DataInitializer {
 @Bean CommandLineRunner seed(UsuarioRepository usuarios,CategoriaRepository categorias,TipoOcorrenciaRepository tipos,PasswordEncoder encoder){
  return args->{
   criarUsuario(usuarios,encoder,"Administrador","admin","Admin@123","ADMINISTRATIVO");
   criarUsuario(usuarios,encoder,"Professor Demonstração","professor","Prof@123","PROFESSOR");
   criarUsuario(usuarios,encoder,"Analista Demonstração","analista","Analista@123","ANALISTA_QUALIDADE");
   criarUsuario(usuarios,encoder,"Coordenador Demonstração","coordenador","Coord@123","COORDENADOR");
   if(categorias.count()==0){
    var d=categorias.save(new Categoria("Disciplinar","Ocorrências relacionadas à disciplina e convivência"));
    var p=categorias.save(new Categoria("Pedagógica","Ocorrências relacionadas ao processo de aprendizagem"));
    var a=categorias.save(new Categoria("Administrativa","Ocorrências administrativas"));
    tipos.save(new TipoOcorrencia("Indisciplina","Descumprimento de regras",d));
    tipos.save(new TipoOcorrencia("Atraso","Chegada após o horário",d));
    tipos.save(new TipoOcorrencia("Rendimento","Dificuldade ou destaque no rendimento",p));
    tipos.save(new TipoOcorrencia("Documentação","Pendência documental",a));
   }
  };
 }
 private void criarUsuario(UsuarioRepository repo,PasswordEncoder enc,String nome,String login,String senha,String perfil){
  if(repo.findByLogin(login).isEmpty())repo.save(new Usuario(nome,login,enc.encode(senha),Perfil.valueOf(perfil)));
 }
}
