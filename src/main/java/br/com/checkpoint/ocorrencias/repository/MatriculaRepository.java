package br.com.checkpoint.ocorrencias.repository;
import br.com.checkpoint.ocorrencias.domain.Matricula;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.*;
public interface MatriculaRepository extends JpaRepository<Matricula,Long> { Optional<Matricula> findByAlunoIdAndTurmaId(Long alunoId, Long turmaId); List<Matricula> findByTurmaIdAndAtivaTrue(Long turmaId); List<Matricula> findByAlunoIdAndAtivaTrue(Long alunoId); List<Matricula> findByAlunoId(Long alunoId); }
