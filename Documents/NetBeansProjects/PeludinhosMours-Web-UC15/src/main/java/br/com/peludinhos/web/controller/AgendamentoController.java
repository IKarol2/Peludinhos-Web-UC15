package br.com.peludinhos.web.controller;
import br.com.peludinhos.web.model.Agendamento;
import br.com.peludinhos.web.model.Cliente;
import br.com.peludinhos.web.model.Servico;
import br.com.peludinhos.web.repository.AgendamentoRepository;
import br.com.peludinhos.web.repository.ClienteRepository;
import br.com.peludinhos.web.repository.ServicoRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

/**
 * Controlador responsável por gerenciar os agendamentos.
 * Recebe dados do formulário e salva no MySQL.
 * Também expõe listagem via API (para testes no Postman).
 *
 * <p>Projeto: PeludinhosMours-Web-UC15</p>
 * <p>Autor: Karol</p>
 * <p>Data: 2025-10-30</p>
 * <p>Versão: 1.0</p>
 */


@RestController
@RequestMapping("/api")
public class AgendamentoController {

    private final AgendamentoRepository agendamentos;
    private final ClienteRepository clientes;
    private final ServicoRepository servicos;

    public AgendamentoController(AgendamentoRepository agendamentos,
                                 ClienteRepository clientes,
                                 ServicoRepository servicos) {
        this.agendamentos = agendamentos;
        this.clientes = clientes;
        this.servicos = servicos;
    }

    @PostMapping("/agendamentos")
    public ResponseEntity<?> criar(@RequestBody Map<String, Object> body) {
        try {
            Long clienteId = asLong(body.get("clienteId"));
            Long servicoId = asLong(body.get("servicoId"));

            Cliente cliente;
            Servico servico;

            if (clienteId != null && servicoId != null) {
                cliente = clientes.findById(clienteId)
                        .orElseThrow(() -> new IllegalArgumentException("Cliente não encontrado."));
                servico = servicos.findById(servicoId)
                        .orElseThrow(() -> new IllegalArgumentException("Serviço não encontrado."));
            } else {
                String nome = asText(body.get("nome"));
                String email = asText(body.get("email"));
                String telefone = asText(body.get("telefone"));
                String servicoNome = normServico(asText(body.get("servico")));

                if (isBlank(nome)) throw new IllegalArgumentException("Informe o nome completo.");
                if (isBlank(email)) throw new IllegalArgumentException("Informe o e-mail.");
                if (isBlank(servicoNome)) throw new IllegalArgumentException("Informe o serviço.");

                cliente = clientes.findFirstByEmailIgnoreCase(email)
                        .orElseGet(() -> {
                            Cliente c = new Cliente();
                            c.setNome(nome);
                            c.setEmail(email);
                            c.setTelefone(telefone);
                            return clientes.save(c);
                        });

                servico = servicos.findFirstByNomeIgnoreCase(servicoNome)
                        .orElseThrow(() -> new IllegalArgumentException("Serviço inválido: " + servicoNome));
            }

            String dataStr = asText(body.get("data"));
            if (isBlank(dataStr)) throw new IllegalArgumentException("Informe a data.");
            LocalDate data = parseDataFlex(dataStr);

            String horaStr = asText(body.getOrDefault("horario", body.get("hora")));
            if (isBlank(horaStr)) throw new IllegalArgumentException("Informe o horário.");
            LocalTime horario = parseHora(horaStr);

            String observacoes = asText(body.get("observacoes"));

            Agendamento ag = new Agendamento();
            ag.setCliente(cliente);
            ag.setServico(servico);
            ag.setData(data);
            ag.setHorario(horario);
            ag.setObservacoes(observacoes);

            Agendamento salvo = agendamentos.save(ag);

            Map<String, Object> ok = new HashMap<>();
            ok.put("id", salvo.getId());
            ok.put("mensagem", "Agendamento criado com sucesso!");
            ok.put("message", "Agendamento criado com sucesso!");
            return ResponseEntity.status(201).body(ok);

        } catch (Exception e) {
            Map<String, Object> erro = new HashMap<>();
            erro.put("mensagem", "Verifique os dados informados.");
            erro.put("message", "Verifique os dados informados.");
            erro.put("error", "Falha ao processar agendamento");
            erro.put("detail", e.getMessage());
            return ResponseEntity.badRequest().body(erro);
        }
    }

    @GetMapping("/agendamentos!")
    public ResponseEntity<?> listar() {
        return ResponseEntity.ok(agendamentos.findAll());
    }

    // === Funções auxiliares ===
    private static boolean isBlank(String s) { return s == null || s.trim().isEmpty(); }

    private static String asText(Object o) {
        return o == null ? null : String.valueOf(o).trim();
    }

    private static Long asLong(Object o) {
        try { return o == null ? null : Long.valueOf(String.valueOf(o)); }
        catch (Exception e) { return null; }
    }

    // --- Normalização de nomes de serviços ---
    private static String normServico(String s) {
        if (isBlank(s)) return null;
        String u = s.trim().toUpperCase();
        switch (u) {
            case "BANHO": return "Banho";
            case "TOSA": return "Tosa";
            case "BANHO E TOSA":
            case "BANHO&TOSA":
            case "BANHO/TOSA":
            case "BANHO + TOSA":
                return "Banho e Tosa";
            default:
                String t = s.trim().toLowerCase();
                return t.substring(0, 1).toUpperCase() + t.substring(1);
        }
    }

    // --- Datas e horários ---
    private static final DateTimeFormatter ISO_DATE = DateTimeFormatter.ISO_LOCAL_DATE;             // yyyy-MM-dd
    private static final DateTimeFormatter BR_DATE  = DateTimeFormatter.ofPattern("dd/MM/yyyy");    // dd/MM/yyyy
    private static final DateTimeFormatter HHMM     = DateTimeFormatter.ofPattern("HH:mm");         // HH:mm

    private static LocalDate parseDataFlex(String v) {
        try {
            return LocalDate.parse(v, ISO_DATE);
        } catch (Exception ignore) {
            try {
                return LocalDate.parse(v, BR_DATE);
            } catch (Exception e) {
                throw new IllegalArgumentException("Data inválida. Use dd/MM/aaaa ou yyyy-MM-dd.");
            }
        }
    }

    private static LocalTime parseHora(String v) {
        try {
            return LocalTime.parse(v, HHMM);
        } catch (Exception e) {
            throw new IllegalArgumentException("Horário inválido. Use HH:mm.");
        }
    }
}


