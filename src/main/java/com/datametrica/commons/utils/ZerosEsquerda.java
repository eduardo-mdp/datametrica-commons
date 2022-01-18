package com.datametrica.commons.utils;

import lombok.experimental.UtilityClass;
import org.apache.commons.lang3.StringUtils;

import static com.datametrica.commons.ponto.constants.Constantes.QUANTIDADE_ZEROS;
import static com.datametrica.commons.ponto.constants.Constantes.ZERO;

@UtilityClass
public class ZerosEsquerda {

    public static String aplicarZerosEsquerda(String texto) {
        return StringUtils.leftPad(texto, QUANTIDADE_ZEROS, ZERO);
    }

}

