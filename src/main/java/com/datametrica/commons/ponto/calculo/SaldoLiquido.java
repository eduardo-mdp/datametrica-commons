package com.datametrica.commons.ponto.calculo;

import com.datametrica.commons.ponto.model.SaldoLiquidoModel;
import com.datametrica.commons.ponto.model.SubtracaoHorasModel;

import java.time.LocalTime;

import static com.datametrica.commons.utils.Constantes.CORTE_PARA_NAO_ABONAR;
import static com.datametrica.commons.utils.Constantes.HORA_ZERADA;
import static com.datametrica.commons.utils.Validacoes.isBefore;
import static com.datametrica.commons.utils.Validacoes.validarFormato;

public final class SaldoLiquido {

    private SaldoLiquido() {
        throw new IllegalStateException("Utility class");
    }

    public static SaldoLiquidoModel calcular(SaldoLiquidoModel tempos) {
        processar(tempos);
        return tempos;
    }

    private static void processar(SaldoLiquidoModel tempos) {
        ajustarJornada(tempos);
        horaInicioMenosHoraFim(tempos, validarFormato(tempos.getHorasTrabalhadas()),
                validarFormato(tempos.getHorasEscaladas()));
        abonarJornadaIncompletaAbonoLegal(tempos);
        abonarJornadaIncompletaAbonoGestao(tempos);
        zerarSaldoSeMenorOuIgual(tempos);
    }

    private static void horaInicioMenosHoraFim(SaldoLiquidoModel tempos, String horaInicio, String horaFim) {
        SubtracaoHorasModel saldo = TratativaHoras.reducaoHoras(horaInicio,
                horaFim);
        tempos.setNegativo(saldo.isNegativo());
        tempos.setSaldoLiquido(saldo.getTempo());
    }


    private static void ajustarJornada(SaldoLiquidoModel tempos) {
        if (!tempos.getHorasAfastamento().equals(HORA_ZERADA)) {
            tempos.setHorasEscaladas(ReduzirJornada.reduzir(tempos.getHorasEscaladas(),
                    tempos.getHorasAfastamento()));
        }
        if (tempos.isAbonaDia()) {
            tempos.setHorasEscaladas(HORA_ZERADA);
        }
    }

    private static void abonarJornadaIncompletaAbonoLegal(SaldoLiquidoModel tempos) {
        final boolean diaNegativoMasSemFalta = tempos.isNegativo() && !tempos.getHorasEscaladas().equals(tempos.getHorasTrabalhadas());
        if (diaNegativoMasSemFalta && !tempos.getHorasAbono().equals(HORA_ZERADA)) {
            tempos.setSaldoLiquido(HORA_ZERADA);
            tempos.setNegativo(false);
        }
    }

    private static void abonarJornadaIncompletaAbonoGestao(SaldoLiquidoModel tempos) {
        final LocalTime abonoGestor = LocalTime.parse(tempos.getHorasAbonoGestor());
        final LocalTime saldoAtual = LocalTime.parse(tempos.getSaldoLiquido());

        if (tempos.isNegativo() && !tempos.getHorasAbonoGestor().equals(HORA_ZERADA)) {
            if (abonoGestor.isBefore(saldoAtual)) {
                horaInicioMenosHoraFim(tempos, validarFormato(tempos.getHorasAbonoGestor()),
                        validarFormato(tempos.getSaldoLiquido()));
            } else {
                horaInicioMenosHoraFim(tempos, HORA_ZERADA, HORA_ZERADA);
            }
        }
    }

    private static void zerarSaldoSeMenorOuIgual(SaldoLiquidoModel tempos) {
        if (isBefore(tempos.getSaldoLiquido(), CORTE_PARA_NAO_ABONAR)) {
            tempos.setSaldoLiquido(HORA_ZERADA);
            tempos.setNegativo(false);
        }
    }

}
