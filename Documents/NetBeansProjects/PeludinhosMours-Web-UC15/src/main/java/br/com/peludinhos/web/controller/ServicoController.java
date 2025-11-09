package br.com.peludinhos.web.controller;
import br.com.peludinhos.web.model.Servico;
import br.com.peludinhos.web.repository.ServicoRepository;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 * API de Serviços.
 *
 * <p>Projeto: PeludinhosMours-Web-UC15</p>
 * <p>Autor: Karol</p>
 * <p>Data: 2025-10-30</p>
 * <p>Versão: 1.0</p>
 */

@RestController
@RequestMapping("/api/servicos")
public class ServicoController {

    private final ServicoRepository repo;

    public ServicoController(ServicoRepository repo) { this.repo = repo; }

    @GetMapping
    public List<Servico> listar() { return repo.findAll(); }
}
