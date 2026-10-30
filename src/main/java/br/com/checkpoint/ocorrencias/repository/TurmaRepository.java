package br.com.checkpoint.ocorrencias.repository;
import br.com.checkpoint.ocorrencias.domain.Turma;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.*;
public interface TurmaRepository extends JpaRepository<Turma,Long> { List<Turma> findByAtivaTrue(); }
