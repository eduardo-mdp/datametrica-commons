package com.datametrica.commons.utils;

public final class ZerosEsquerda {

    public static String aplicarZerosEsquerda(String texto, int quantidadeZeros) {
        return ajustar(texto, quantidadeZeros);
    }

    private static String ajustar(String texto, int quantidadeZeros) {
        int qtdNumerosZeros = Math.abs(texto.length() - quantidadeZeros);
        return criarZeros(qtdNumerosZeros) +
                texto;
    }

    private static String criarZeros(int quantidade) {
        StringBuilder retorno = new StringBuilder().append("0");
        for (int i = 1; i < quantidade; i++) {
            retorno.append("0");
        }
        return retorno.toString();
    }

}

