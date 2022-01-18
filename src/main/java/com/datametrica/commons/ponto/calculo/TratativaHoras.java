package com.datametrica.commons.ponto.calculo;

import com.datametrica.commons.ponto.model.SubtracaoHorasModel;
import lombok.experimental.UtilityClass;

import java.time.DateTimeException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;

import static com.datametrica.commons.utils.Constantes.DATE_TIME_FORMATTER;
import static com.datametrica.commons.utils.Constantes.TIME_FORMATTER;

@UtilityClass
public class TratativaHoras {

    public static SubtracaoHorasModel reducaoHoras(String horaBase, String reducao) {
        LocalTime lTHoraBase = LocalTime.parse(horaBase, TIME_FORMATTER);
        LocalTime lTReducao = LocalTime.parse(reducao, TIME_FORMATTER);
        if (lTReducao.isAfter(lTHoraBase)) {
            return SubtracaoHorasModel.builder()
                    .negativo(true)
                    .tempo(lTReducao.minusHours(lTHoraBase.getHour())
                            .minusMinutes(lTHoraBase.getMinute())
                            .minusSeconds(lTHoraBase.getSecond())
                            .toString())
                    .build();
        }
        return SubtracaoHorasModel.builder()
                .negativo(false)
                .tempo(lTHoraBase.minusHours(lTReducao.getHour())
                        .minusMinutes(lTReducao.getMinute())
                        .minusSeconds(lTReducao.getSecond())
                        .toString())
                .build();

    }

    public static String subtracaoDataHora(String horaInicio, String horaFim) {
        try {
            LocalDateTime dataHoraInicio = LocalDateTime.parse(horaInicio, DATE_TIME_FORMATTER);
            LocalDateTime dataHoraFim = LocalDateTime.parse(horaFim, DATE_TIME_FORMATTER);
            long totalSegundos = ChronoUnit.SECONDS.between(dataHoraInicio, dataHoraFim);
            long hours = totalSegundos / 3600;
            long minutes = (totalSegundos % 3600) / 60;

            return String.format("%02d:%02d", hours, minutes);

        } catch (Exception e) {
            throw new DateTimeException(String.format("Erro ao subtrair %s e %s .", horaInicio, horaFim));
        }

    }

    public static String somaHoras(String tempo, String tempoSoma) {
        LocalTime horaInicial = LocalTime.parse(tempo, TIME_FORMATTER);
        LocalTime horaSoma = LocalTime.parse(tempoSoma, TIME_FORMATTER);
        return horaInicial.plusHours(horaSoma.getHour())
                .plusMinutes(horaSoma.getMinute())
                .plusSeconds(horaSoma.getSecond()).toString();
    }

}
