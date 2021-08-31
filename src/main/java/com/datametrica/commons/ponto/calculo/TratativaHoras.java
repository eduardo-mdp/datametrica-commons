package com.datametrica.commons.ponto.calculo;

import com.datametrica.commons.ponto.model.SubtracaoHorasModel;

import java.time.DateTimeException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

public class TratativaHoras {

    private TratativaHoras() {
        throw new IllegalStateException("Utility class");
    }

    static SubtracaoHorasModel subtracaoHoras(String horaInicio, String horaFim) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        LocalTime dtHoraInicio = LocalTime.parse(horaInicio, formatter);
        LocalTime dtHoraFim = LocalTime.parse(horaFim, formatter);
        if (dtHoraFim.isAfter(dtHoraInicio)) {
            return SubtracaoHorasModel.builder()
                    .negativo(true)
                    .tempo(dtHoraFim.minusHours(dtHoraInicio.getHour())
                            .minusMinutes(dtHoraInicio.getMinute())
                            .minusSeconds(dtHoraInicio.getSecond())
                            .toString())
                    .build();
        }
        return SubtracaoHorasModel.builder()
                .negativo(false)
                .tempo(dtHoraInicio.minusHours(dtHoraFim.getHour())
                        .minusMinutes(dtHoraFim.getMinute())
                        .minusSeconds(dtHoraFim.getSecond())
                        .toString())
                .build();

    }

    static String subtracaoDataHora(String horaInicio, String horaFim) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
            LocalDateTime dataHoraInicio = LocalDateTime.parse(horaInicio, formatter);
            LocalDateTime dataHoraFim = LocalDateTime.parse(horaFim, formatter);
            long totalSegundos = ChronoUnit.SECONDS.between(dataHoraInicio, dataHoraFim);
            long hours = totalSegundos / 3600;
            long minutes = (totalSegundos % 3600) / 60;

            return String.format("%02d:%02d", hours, minutes);

        } catch (Exception e) {
            throw new DateTimeException(String.format("Erro ao subtrair %s e %s .", horaInicio, horaFim));
        }

    }

    static String somaHoras(String horaInicial, String horaSoma) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        LocalTime dtHoraInicial = LocalTime.parse(horaInicial, formatter);
        LocalTime dtHoraSoma = LocalTime.parse(horaSoma, formatter);
        return dtHoraInicial.plusHours(dtHoraSoma.getHour())
                .plusMinutes(dtHoraSoma.getMinute())
                .plusSeconds(dtHoraSoma.getSecond()).toString();
    }

}
