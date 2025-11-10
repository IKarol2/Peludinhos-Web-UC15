package br.com.peludinhos.web.controller;
import br.com.peludinhos.web.dto.AgendamentoDTO;
import br.com.peludinhos.web.model.Cliente;
import br.com.peludinhos.web.model.Servico;
import br.com.peludinhos.web.model.Agendamento;
import br.com.peludinhos.web.repository.AgendamentoRepository;
import br.com.peludinhos.web.repository.ClienteRepository;
import br.com.peludinhos.web.repository.ServicoRepository;
import br.com.peludinhos.web.service.AgendamentoService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Teste MockMvc da API de agendamentos.
 *
 * <p>Projeto: PeludinhosMours-Web-UC15</p>
 * <p>Autor: Karol</p>
 * <p>Data: 2025-10-30</p>
 * <p>Versão: 1.0</p>
 */

@WebMvcTest(controllers = AgendamentoController.class)
class AgendamentoControllerTest {

    @Autowired MockMvc mvc;
    @MockBean AgendamentoRepository agendamentoRepository;
    @MockBean ClienteRepository clienteRepository;
    @MockBean ServicoRepository servicoRepository;

    @Test
    void postCriaAgendamento_201() throws Exception {
        Cliente cliente = new Cliente();
        cliente.setId(1L);
        Servico servico = new Servico();
        servico.setId(1L);
        when(clienteRepository.findById(1L)).thenReturn(java.util.Optional.of(cliente));
        when(servicoRepository.findById(1L)).thenReturn(java.util.Optional.of(servico));
        Agendamento agendamento = new Agendamento();
        agendamento.setId(123L);
        when(agendamentoRepository.save(any(Agendamento.class))).thenReturn(agendamento);
        String body = "{\"clienteId\":1,\"servicoId\":1,\"data\":\"2025-11-01\",\"horario\":\"10:30\",\"observacoes\":\"ok\"}";
        mvc.perform(post("/api/agendamentos").contentType(MediaType.APPLICATION_JSON).content(body))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.mensagem").value("Agendamento criado com sucesso!"));
    }

    @Test
    void postAgendamento_invalido_400() throws Exception {
        String body = "{\"clienteId\":null,\"servicoId\":1,\"data\":\"\",\"horario\":\"\"}";
        mvc.perform(post("/api/agendamentos").contentType(MediaType.APPLICATION_JSON).content(body))
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.mensagem").value("Verifique os dados informados."));
    }
}
