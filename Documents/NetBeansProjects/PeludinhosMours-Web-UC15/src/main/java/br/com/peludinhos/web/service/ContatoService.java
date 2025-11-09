package br.com.peludinhos.web.service;
import br.com.peludinhos.web.dto.ContatoDTO;
import br.com.peludinhos.web.model.ContatoMensagem;
import br.com.peludinhos.web.repository.ContatoMensagemRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Serviço de registro de mensagens de contato.
 *
 * <p>Projeto: PeludinhosMours-Web-UC15</p>
 * <p>Autor: Karol</p>
 * <p>Data: 2025-10-30</p>
 * <p>Versão: 1.0</p>
 */


@Service
public class ContatoService {

    private final ContatoMensagemRepository repos;

    public ContatoService(ContatoMensagemRepository repos) {
        this.repos = repos;
    }

    @Transactional
    public void registrar(ContatoDTO dto) {
        ContatoMensagem m = new ContatoMensagem();
        m.setNome(dto.getNome());
        m.setEmail(dto.getEmail());
        m.setMensagem(dto.getMensagem());
        repos.save(m);
    }
}
