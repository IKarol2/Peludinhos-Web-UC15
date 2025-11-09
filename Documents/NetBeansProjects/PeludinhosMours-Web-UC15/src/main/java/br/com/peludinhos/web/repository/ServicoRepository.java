package br.com.peludinhos.web.repository;
import br.com.peludinhos.web.model.Servico;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

/**
 * Repository de Servico.
 *
 * <p>Projeto: PeludinhosMours-Web-UC15</p>
 * <p>Autor: Karol</p>
 * <p>Data: 2025-10-30</p>
 * <p>Versão: 1.0</p>
 */


public interface ServicoRepository extends JpaRepository<Servico, Long> {
    Optional<Servico> findFirstByNomeIgnoreCase(String nome);
}
