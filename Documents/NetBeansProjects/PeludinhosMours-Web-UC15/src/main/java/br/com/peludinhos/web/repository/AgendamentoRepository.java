package br.com.peludinhos.web.repository;
import br.com.peludinhos.web.model.Agendamento;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDate;
import java.util.List;

/**
 * Repository de Agendamento.
 *
 * <p>Projeto: PeludinhosMours-Web-UC15</p>
 * <p>Autor: Karol</p>
 * <p>Data: 2025-10-30</p>
 * <p>Versão: 1.0</p>
 */

public interface AgendamentoRepository extends JpaRepository<Agendamento, Long> {
    // Usado pelos testes
    List<Agendamento> findByData(LocalDate data);

    // (opcional) úteis em serviços/listagens
    List<Agendamento> findTop10ByOrderByIdDesc();
}
