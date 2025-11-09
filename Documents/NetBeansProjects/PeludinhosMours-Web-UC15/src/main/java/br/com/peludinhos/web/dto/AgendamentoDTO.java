package br.com.peludinhos.web.dto;
import jakarta.validation.constraints.*;

/**
 * DTO para criação de agendamentos.
 *
 * <p>Projeto: PeludinhosMours-Web-UC15</p>
 * <p>Autor: Karol</p>
 * <p>Data: 2025-10-30</p>
 * <p>Versão: 1.0</p>
 */

public class AgendamentoDTO {

    @NotNull
    private Long clienteId;

    @NotNull
    private Long servicoId;

    @NotBlank // yyyy-MM-dd
    private String data;

    @NotBlank // HH:mm
    private String horario;

    private String observacoes;

    public Long getClienteId() { return clienteId; }
    public void setClienteId(Long clienteId) { this.clienteId = clienteId; }

    public Long getServicoId() { return servicoId; }
    public void setServicoId(Long servicoId) { this.servicoId = servicoId; }

    public String getData() { return data; }
    public void setData(String data) { this.data = data; }

    public String getHorario() { return horario; }
    public void setHorario(String horario) { this.horario = horario; }

    public String getObservacoes() { return observacoes; }
    public void setObservacoes(String observacoes) { this.observacoes = observacoes; }
}
