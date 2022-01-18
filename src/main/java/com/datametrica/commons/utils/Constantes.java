package com.datametrica.commons.utils;

import lombok.experimental.UtilityClass;

import java.time.format.DateTimeFormatter;

@UtilityClass
public class Constantes {

    public static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm");
    public static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    public static final String HORA_ZERADA = "00:00";
    public static final String CORTE_PARA_NAO_ABONAR = "00:10";
    public static final String EXPRESSAO = "(0[0-9]|1[0-9]|2[0-3]):[0-5][0-9]";
    public static final String DURACAO_LANCHE_MINIMO = "00:20";
    public static final String DURACAO_LANCHE_MAXIMO = "02:00";
    public static final String DURACAO_LANCHE_MINIMO_HE = "00:30";
    public static final String DURACAO_LANCHE_MINIMO_HE_0712 = "01:10";
    public static final String DURACAO_HE_MINIMO = "01:00";
    public static final String DURACAO_HE_MINIMO_0712 = "01:40";

    public static final String ESCALA_0600 = "06:00";
    public static final String ESCALA_0500 = "05:00";
    public static final String ESCALA_0712 = "07:12";
}
