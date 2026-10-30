package br.com.checkpoint.ocorrencias.repository;
import br.com.checkpoint.ocorrencias.domain.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.*;
public interface UsuarioRepository extends JpaRepository<Usuario,Long> { Optional<Usuario> findByLogin(String login); boolean existsByLogin(String login); }
