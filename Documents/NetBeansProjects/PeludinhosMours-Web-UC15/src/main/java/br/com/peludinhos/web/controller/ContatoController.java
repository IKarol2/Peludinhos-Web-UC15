package br.com.peludinhos.web.controller;
import br.com.peludinhos.web.model.ContatoMensagem;
import br.com.peludinhos.web.repository.ContatoMensagemRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * Controlador do formulário de Contato.
 * Salva a mensagem no MySQL e expõe listagem via API.
 *
 * <p>Projeto: PeludinhosMours-Web-UC15</p>
 * <p>Autor: Karol</p>
 * <p>Data: 2025-10-30</p>
 * <p>Versão: 1.0</p>
 */

@RestController
@RequestMapping("/api")
public class ContatoController {

    private final ContatoMensagemRepository contatos;

    public ContatoController(ContatoMensagemRepository contatos) {
        this.contatos = contatos;
    }

    @PostMapping("/contato")
    public ResponseEntity<?> enviarMensagem(@RequestBody ContatoMensagem mensagem) {
        try {
            // Validação simples
            if (mensagem.getNome() == null || mensagem.getNome().trim().isEmpty()) {
                throw new IllegalArgumentException("Informe o nome completo.");
            }
            if (mensagem.getEmail() == null || mensagem.getEmail().trim().isEmpty()) {
                throw new IllegalArgumentException("Informe o e-mail.");
            }
            if (mensagem.getTelefone() == null || mensagem.getTelefone().trim().isEmpty()) {
                throw new IllegalArgumentException("Informe o telefone.");
            }
            if (mensagem.getMensagem() == null || mensagem.getMensagem().trim().isEmpty()) {
                throw new IllegalArgumentException("Escreva uma mensagem antes de enviar.");
            }

            // Set timestamp if missing
            if (mensagem.getCreatedAt() == null) {
                mensagem.setCreatedAt(java.time.OffsetDateTime.now());
            }

            // Salvar no banco
            ContatoMensagem salvo = contatos.save(mensagem);

            Map<String, Object> ok = new HashMap<>();
            ok.put("id", salvo.getId());
            ok.put("mensagem", "Mensagem enviada com sucesso!");
            ok.put("message", "Mensagem enviada com sucesso!");
            return ResponseEntity.status(201).body(ok);

        } catch (Exception e) {
            Map<String, Object> erro = new HashMap<>();
            erro.put("mensagem", "Falha ao enviar mensagem. Verifique os dados informados.");
            erro.put("detail", e.getMessage());
            return ResponseEntity.badRequest().body(erro);
        }
    }

    @GetMapping("/contato")
    public ResponseEntity<?> listarMensagens() {
        return ResponseEntity.ok(contatos.findAll());
    }
}
