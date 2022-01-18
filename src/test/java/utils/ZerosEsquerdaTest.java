package utils;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.datametrica.commons.utils.ZerosEsquerda.aplicarZerosEsquerda;

@ExtendWith(MockitoExtension.class)
class ZerosEsquerdaTest {

    @Test
    void aplicarTeste() {
        final String matricula = "000";
        final String retornoEsperado = "000000";
        Assertions.assertEquals(retornoEsperado, aplicarZerosEsquerda(matricula));
    }

    @Test
    void aplicarTeste2() {
        final String matricula = "000000";
        final String retornoEsperado = "000000";
        Assertions.assertEquals(retornoEsperado, aplicarZerosEsquerda(matricula));
    }
}
