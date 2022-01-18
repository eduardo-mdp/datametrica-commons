package com.datametrica.commons.utils;

import lombok.experimental.UtilityClass;
import org.apache.commons.lang3.StringUtils;

import java.time.DateTimeException;
import java.time.LocalTime;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.datametrica.commons.utils.Constantes.EXPRESSAO;
import static com.datametrica.commons.utils.Constantes.TIME_FORMATTER;


@UtilityClass
public class Validacoes {

    public static void checarSeNulo(String texto) {
        if (StringUtils.isEmpty(texto)) {
            throw new NullPointerException("Campo nulo para validação.");
        }
    }

    public static String validarFormato(final String time) {
        Pattern pattern = Pattern.compile(EXPRESSAO);
        Matcher matcher = pattern.matcher(time);
        if (!matcher.matches()) {
            throw new DateTimeException("Formato aceito HH:mm .");
        }
        return time;
    }

    public static boolean isBefore(String t1, String t2) {
        LocalTime hm1 = LocalTime.parse(t1, TIME_FORMATTER);
        LocalTime hm2 = LocalTime.parse(t2, TIME_FORMATTER);
        return hm1.isBefore(hm2);
    }

    public static boolean isAfter(String t1, String t2) {
        LocalTime hm1 = LocalTime.parse(t1, TIME_FORMATTER);
        LocalTime hm2 = LocalTime.parse(t2, TIME_FORMATTER);
        return hm1.isAfter(hm2);
    }

}

