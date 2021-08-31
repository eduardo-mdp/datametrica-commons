package ponto.calculo;

import com.datametrica.commons.ponto.calculo.SaldoLiquido;
import com.datametrica.commons.ponto.model.SaldoLiquidoModel;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class SaldoLiquidoTest {

    @Test
    void deveRetornarSaldoZerado() {
        final String horaZerada = "00:00";
        final SaldoLiquidoModel tempos = SaldoLiquidoModel.builder()
                .abonaDia(false)
                .horasAbono(horaZerada)
                .horasEscaladas(horaZerada)
                .horasTrabalhadas(horaZerada)
                .horasAfastamento(horaZerada)
                .horasAbonoGestor(horaZerada)
                .build();

        final SaldoLiquidoModel retornoEsperado = SaldoLiquidoModel.builder()
                .abonaDia(false)
                .horasAbono(horaZerada)
                .horasEscaladas(horaZerada)
                .horasTrabalhadas(horaZerada)
                .horasAfastamento(horaZerada)
                .saldoLiquido(horaZerada)
                .horasAbonoGestor(horaZerada)
                .negativo(false)
                .build();
        Assertions.assertEquals(retornoEsperado, SaldoLiquido.calcular(tempos));
    }

    @Test
    void trabalhado_maior_que_escalado_deve_retornar_saldo_com_hora_extra() {
        final String horaZerada = "00:00";
        final String horaTrabalhada = "07:00";
        final String horaEscalada = "06:00";
        final SaldoLiquidoModel tempos = SaldoLiquidoModel.builder()
                .abonaDia(false)
                .horasAbono(horaZerada)
                .horasEscaladas(horaEscalada)
                .horasTrabalhadas(horaTrabalhada)
                .horasAfastamento(horaZerada)
                .horasAbonoGestor(horaZerada)
                .build();

        final SaldoLiquidoModel retornoEsperado = SaldoLiquidoModel.builder()
                .abonaDia(false)
                .horasAbono(horaZerada)
                .horasEscaladas(horaEscalada)
                .horasTrabalhadas(horaTrabalhada)
                .horasAfastamento(horaZerada)
                .horasAbonoGestor(horaZerada)
                .saldoLiquido("01:00")
                .negativo(false)
                .build();
        Assertions.assertEquals(retornoEsperado, SaldoLiquido.calcular(tempos));
    }

    @Test
    void trabalhado_menor_que_escalado_deve_retornar_saldo_negativo() {
        final String horaZerada = "00:00";
        final String horaTrabalhada = "06:00";
        final String horaEscalada = "08:00";
        final SaldoLiquidoModel tempos = SaldoLiquidoModel.builder()
                .abonaDia(false)
                .horasAbono(horaZerada)
                .horasEscaladas(horaEscalada)
                .horasTrabalhadas(horaTrabalhada)
                .horasAfastamento(horaZerada)
                .horasAbonoGestor(horaZerada)
                .build();

        final SaldoLiquidoModel retornoEsperado = SaldoLiquidoModel.builder()
                .abonaDia(false)
                .horasAbono(horaZerada)
                .horasEscaladas(horaEscalada)
                .horasTrabalhadas(horaTrabalhada)
                .horasAfastamento(horaZerada)
                .horasAbonoGestor(horaZerada)
                .saldoLiquido("02:00")
                .negativo(true)
                .build();
        Assertions.assertEquals(retornoEsperado, SaldoLiquido.calcular(tempos));
    }

    @Test
    void trabalhado_menor_que_escalado_com_abono_gestor_deve_retornar_saldo_positivo() {
        final String horaZerada = "00:00";
        final String horaTrabalhada = "06:00";
        final String horaEscalada = "08:00";
        final SaldoLiquidoModel tempos = SaldoLiquidoModel.builder()
                .abonaDia(false)
                .horasAbono(horaZerada)
                .horasEscaladas(horaEscalada)
                .horasTrabalhadas(horaTrabalhada)
                .horasAfastamento(horaZerada)
                .horasAbonoGestor("02:00")
                .build();

        final SaldoLiquidoModel retornoEsperado = SaldoLiquidoModel.builder()
                .abonaDia(false)
                .horasAbono(horaZerada)
                .horasEscaladas(horaEscalada)
                .horasTrabalhadas(horaTrabalhada)
                .horasAfastamento(horaZerada)
                .horasAbonoGestor("02:00")
                .saldoLiquido(horaZerada)
                .negativo(false)
                .build();
        Assertions.assertEquals(retornoEsperado, SaldoLiquido.calcular(tempos));
    }

    @Test
    void trabalhado_menor_que_escalado_com_abono_parcial_gestor_deve_retornar_saldo_negativo() {
        final String horaZerada = "00:00";
        final String horaTrabalhada = "06:00";
        final String horaEscalada = "08:00";
        final SaldoLiquidoModel tempos = SaldoLiquidoModel.builder()
                .abonaDia(false)
                .horasAbono(horaZerada)
                .horasEscaladas(horaEscalada)
                .horasTrabalhadas(horaTrabalhada)
                .horasAfastamento(horaZerada)
                .horasAbonoGestor("01:00")
                .build();

        final SaldoLiquidoModel retornoEsperado = SaldoLiquidoModel.builder()
                .abonaDia(false)
                .horasAbono(horaZerada)
                .horasEscaladas(horaEscalada)
                .horasTrabalhadas(horaTrabalhada)
                .horasAfastamento(horaZerada)
                .horasAbonoGestor("01:00")
                .saldoLiquido("01:00")
                .negativo(true)
                .build();
        Assertions.assertEquals(retornoEsperado, SaldoLiquido.calcular(tempos));
    }

}

