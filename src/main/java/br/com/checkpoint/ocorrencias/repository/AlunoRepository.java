package br.com.checkpoint.ocorrencias.repository;
import br.com.checkpoint.ocorrencias.domain.Aluno;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.*;
public interface AlunoRepository extends JpaRepository<Aluno,Long> { List<Aluno> findByAtivoTrue(); }
