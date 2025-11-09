package br.com.peludinhos.web.pricing;

/**
 * Contrato da estratégia de preço.
 *
 * <p>Projeto: PeludinhosMours-Web-UC15</p>
 * <p>Autor: Karol</p>
 * <p>Data: 2025-10-30</p>
 * <p>Versão: 1.0</p>
 */


public interface PrecoStrategy {

    double calcular(double precoBase, Porte porte);

    enum Porte { PEQUENO, MEDIO, GRANDE }
}
