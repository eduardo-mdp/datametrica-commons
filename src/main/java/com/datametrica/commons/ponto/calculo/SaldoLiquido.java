package com.datametrica.commons.ponto.calculo;

import com.datametrica.commons.ponto.model.SaldoLiquidoModel;
import com.datametrica.commons.ponto.model.SubtracaoHorasModel;

import java.time.DateTimeException;
import java.time.LocalTime;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class SaldoLiquido {

    private static final String EXPRESSAO = "(0[0-9]|1[0-9]|2[0-3]):[0-5][0-9]";
    private static final String HORA_ZERADA = "00:00";

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
    }

    private static void horaInicioMenosHoraFim(SaldoLiquidoModel tempos, String horaInicio, String horaFim) {
        SubtracaoHorasModel saldo = TratativaHoras.subtracaoHoras(horaInicio,
                horaFim);
        tempos.setNegativo(saldo.isNegativo());
        tempos.setSaldoLiquido(saldo.getTempo());
    }


    private static String validarFormato(final String time) {
        Pattern pattern = Pattern.compile(EXPRESSAO);
        Matcher matcher = pattern.matcher(time);
        if (!matcher.matches()) {
            throw new DateTimeException("Formato aceito HH:mm .");
        }
        return time;
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

}
