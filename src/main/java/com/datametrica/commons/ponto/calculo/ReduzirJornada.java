package com.datametrica.commons.ponto.calculo;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public final class ReduzirJornada {

    private ReduzirJornada() {
        throw new IllegalStateException("Utility class");
    }

    public static String reduzir(String escala, String valorReduzir) {
        return ajustarJornada(escala, valorReduzir);
    }

    private static String ajustarJornada(String escala, String valorReduzir) {
        final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        final LocalTime dtEscala = LocalTime.parse(escala, formatter);
        final LocalTime dtValorReduzir = LocalTime.parse(valorReduzir, formatter);

        if (dtValorReduzir.isAfter(dtEscala)) {
            return "00:00";
        }

        return dtEscala.minusHours(dtValorReduzir.getHour())
                .minusMinutes(dtValorReduzir.getMinute())
                .minusSeconds(dtValorReduzir.getSecond()).toString();

    }

}
