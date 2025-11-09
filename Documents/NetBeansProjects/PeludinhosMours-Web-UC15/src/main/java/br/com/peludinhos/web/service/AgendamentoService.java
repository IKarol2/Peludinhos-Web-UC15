package br.com.peludinhos.web.service;
import br.com.peludinhos.web.dto.AgendamentoDTO;
import br.com.peludinhos.web.model.*;
import br.com.peludinhos.web.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.*;

/**
 * Regras de negócio do agendamento.
 *
 * <p>Projeto: PeludinhosMours-Web-UC15</p>
 * <p>Autor: Karol</p>
 * <p>Data: 2025-10-30</p>
 * <p>Versão: 1.0</p>
 */

@Service
public class AgendamentoService {

    private final AgendamentoRepository agendamentos;
    private final ClienteRepository clientes;
    private final ServicoRepository servicos;

    public AgendamentoService(AgendamentoRepository agendamentos,
                              ClienteRepository clientes,
                              ServicoRepository servicos) {
        this.agendamentos = agendamentos;
        this.clientes = clientes;
        this.servicos = servicos;
    }

    @Transactional
    public Agendamento criar(AgendamentoDTO dto) {
        Cliente cli = clientes.findById(dto.getClienteId())
                .orElseThrow(() -> new IllegalArgumentException("Cliente não encontrado"));

        Servico ser = servicos.findById(dto.getServicoId())
                .orElseThrow(() -> new IllegalArgumentException("Serviço não encontrado"));

        Agendamento ag = new Agendamento();
        ag.setCliente(cli);
        ag.setServico(ser);
        ag.setData(LocalDate.parse(dto.getData()));
        ag.setHorario(LocalTime.parse(dto.getHorario()));
        ag.setObservacoes(dto.getObservacoes());

        return agendamentos.save(ag);
    }
}
