package br.com.checkpoint.ocorrencias.repository;
import br.com.checkpoint.ocorrencias.domain.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.*;
public interface CategoriaRepository extends JpaRepository<Categoria,Long> { List<Categoria> findByAtivaTrue(); }
