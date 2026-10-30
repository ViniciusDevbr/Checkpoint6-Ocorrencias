package br.com.checkpoint.ocorrencias.repository;
import br.com.checkpoint.ocorrencias.domain.TipoOcorrencia;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.*;
public interface TipoOcorrenciaRepository extends JpaRepository<TipoOcorrencia,Long> { List<TipoOcorrencia> findByCategoriaIdAndAtivoTrue(Long categoriaId); }
