package br.com.peludinhos.web.repository;
import br.com.peludinhos.web.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

/**
 * Repository de Cliente.
 *
 * <p>Projeto: PeludinhosMours-Web-UC15</p>
 * <p>Autor: Karol</p>
 * <p>Data: 2025-10-30</p>
 * <p>Versão: 1.0</p>
 */


public interface ClienteRepository extends JpaRepository<Cliente, Long> {
    Optional<Cliente> findFirstByEmailIgnoreCase(String email);
}
