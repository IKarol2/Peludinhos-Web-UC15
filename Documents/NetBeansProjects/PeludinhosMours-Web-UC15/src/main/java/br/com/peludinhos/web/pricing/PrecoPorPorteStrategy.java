package br.com.peludinhos.web.pricing;

/**
 * Implementação da estratégia de preço por porte.
 *
 * <p>Projeto: PeludinhosMours-Web-UC15</p>
 * <p>Autor: Karol</p>
 * <p>Data: 2025-10-30</p>
 * <p>Versão: 1.0</p>
 */


public class PrecoPorPorteStrategy implements PrecoStrategy {

    @Override
    public double calcular(double precoBase, Porte porte) {
        if (Double.isNaN(precoBase)) {
            throw new IllegalArgumentException("Preço base inválido");
        }
        if (porte == null) {
            throw new IllegalArgumentException("Porte nulo");
        }

        return switch (porte) {
            case PEQUENO -> precoBase;
            case MEDIO   -> precoBase * 1.20;
            case GRANDE  -> precoBase * 1.40;
        };
    }
}
