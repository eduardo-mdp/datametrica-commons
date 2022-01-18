package ponto.calculo;

import com.datametrica.commons.ponto.calculo.SaldoLiquido;
import com.datametrica.commons.ponto.calculo.anatel.SaldoLiquidoAnatel;
import com.datametrica.commons.ponto.model.AnatelModel;
import com.datametrica.commons.ponto.model.SaldoLiquidoModel;
import com.datametrica.commons.ponto.model.TempoTrabalhadoAnatelModel;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.datametrica.commons.utils.Constantes.HORA_ZERADA;
import static utils.TestUtils.saldoLiquidoModel;
import static utils.TestUtils.apontamentosSemHoraExtra0600;


@ExtendWith(MockitoExtension.class)
class SaldoLiquidoAnatelTest {

    @Test
    void deveRetornarSaldoZerado() {
        final TempoTrabalhadoAnatelModel pausas = TempoTrabalhadoAnatelModel.builder()
                .login("08:00")
                .sLanche("12:00")
                .rLanche("12:20")
                .logout("14:20")
                .build();
        final SaldoLiquidoModel tempos = SaldoLiquidoModel.builder()
                .abonaDia(false)
                .horasAbono(HORA_ZERADA)
                .horasEscaladas("06:00")
                .horasTrabalhadas(HORA_ZERADA)
                .horasAfastamento(HORA_ZERADA)
                .horasAbonoGestor(HORA_ZERADA)
                .build();

        final SaldoLiquidoModel retornoEsperado = SaldoLiquidoModel.builder()
                .abonaDia(false)
                .horasAbono(HORA_ZERADA)
                .horasEscaladas(HORA_ZERADA)
                .horasTrabalhadas(HORA_ZERADA)
                .horasAfastamento(HORA_ZERADA)
                .saldoLiquido(HORA_ZERADA)
                .horasAbonoGestor(HORA_ZERADA)
                .negativo(false)
                .build();

        AnatelModel retorno = SaldoLiquidoAnatel.calcular(saldoLiquidoModel(), apontamentosSemHoraExtra0600());
        System.out.println(retorno.getTempoTrabalhado());
        System.out.println(retorno.getSaldoLiquido());
        Assertions.assertEquals(retornoEsperado, retorno);
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

    @Test
    void desconsiderar_minutos_saldo_liquido() {
        SaldoLiquidoModel saldoLiquidoModel = saldoLiquidoModel();
        saldoLiquidoModel.setHorasTrabalhadas("07:55");

        SaldoLiquidoModel saldoLiquidoModelRetorno = saldoLiquidoModel();
        saldoLiquidoModelRetorno.setHorasTrabalhadas("07:55");
        saldoLiquidoModelRetorno.setSaldoLiquido("00:00");
        saldoLiquidoModelRetorno.setNegativo(false);

        Assertions.assertEquals(saldoLiquidoModelRetorno, SaldoLiquido.calcular(saldoLiquidoModel));
    }

}

