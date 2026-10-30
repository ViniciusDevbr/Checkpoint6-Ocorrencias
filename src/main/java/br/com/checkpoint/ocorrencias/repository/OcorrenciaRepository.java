package br.com.checkpoint.ocorrencias.repository;
import br.com.checkpoint.ocorrencias.domain.Ocorrencia;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.*;
public interface OcorrenciaRepository extends JpaRepository<Ocorrencia,Long> { List<Ocorrencia> findAllByOrderByDataDescHoraDesc(); List<Ocorrencia> findByProfessorIdOrderByDataDescHoraDesc(Long professorId); List<Ocorrencia> findByAlunoIdOrderByDataDescHoraDesc(Long alunoId); }
