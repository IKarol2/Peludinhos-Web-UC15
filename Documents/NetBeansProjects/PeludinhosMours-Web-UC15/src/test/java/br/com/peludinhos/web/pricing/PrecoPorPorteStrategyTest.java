package br.com.peludinhos.web.pricing;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Teste unitário da estratégia de preço.
 *
 * <p>Projeto: PeludinhosMours-Web-UC15</p>
 * <p>Autor: Karol</p>
 * <p>Data: 2025-10-30</p>
 * <p>Versão: 1.0</p>
 */

public class PrecoPorPorteStrategyTest {

    @Test
    void calcula_pequeno() {
        var s = new PrecoPorPorteStrategy();
        assertEquals(50.0, s.calcular(50.0, PrecoStrategy.Porte.PEQUENO));
    }

    @Test
    void calcula_medio() {
        var s = new PrecoPorPorteStrategy();
        assertEquals(60.0, s.calcular(50.0, PrecoStrategy.Porte.MEDIO), 0.0001);
    }

    @Test
    void calcula_grande() {
        var s = new PrecoPorPorteStrategy();
        assertEquals(70.0, s.calcular(50.0, PrecoStrategy.Porte.GRANDE), 0.0001);
    }

    @Test
    void erro_porte_nulo() {
        var s = new PrecoPorPorteStrategy();
        assertThrows(IllegalArgumentException.class, () -> s.calcular(50.0, null));
    }

    @Test
    void erro_preco_invalido() {
        var s = new PrecoPorPorteStrategy();
        assertThrows(IllegalArgumentException.class, () -> s.calcular(Double.NaN, PrecoStrategy.Porte.PEQUENO));
    }
}
