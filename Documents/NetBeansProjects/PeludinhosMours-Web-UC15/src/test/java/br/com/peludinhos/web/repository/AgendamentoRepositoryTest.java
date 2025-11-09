package br.com.peludinhos.web.repository;
import br.com.peludinhos.web.model.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import java.math.BigDecimal;
import java.time.*;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Teste JPA do repositório de agendamento.
 *
 * <p>Projeto: PeludinhosMours-Web-UC15</p>
 * <p>Autor: Karol</p>
 * <p>Data: 2025-10-30</p>
 * <p>Versão: 1.0</p>
 */

@DataJpaTest
class AgendamentoRepositoryTest {

    @Autowired AgendamentoRepository agendamentos;
    @Autowired ClienteRepository clientes;
    @Autowired ServicoRepository servicos;

    @Test
    void salvaEConsultaPorData() {
        Cliente c = new Cliente();
        c.setNome("Ana");
        c.setEmail("ana@exemplo.com");
        clientes.save(c);

        Servico s = new Servico();
        s.setNome("Banho");
        s.setPreco(BigDecimal.valueOf(50));
        servicos.save(s);

        Agendamento a = new Agendamento();
        a.setCliente(c);
        a.setServico(s);
        a.setData(LocalDate.now());
        a.setHorario(LocalTime.of(10,30));
        agendamentos.save(a);

        List<Agendamento> lista = agendamentos.findByData(LocalDate.now());
        assertFalse(lista.isEmpty());
    }
}
