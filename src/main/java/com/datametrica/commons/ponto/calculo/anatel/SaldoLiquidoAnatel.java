package com.datametrica.commons.ponto.calculo.anatel;

import com.datametrica.commons.ponto.calculo.SaldoLiquido;
import com.datametrica.commons.ponto.calculo.TratativaHoras;
import com.datametrica.commons.ponto.model.AnatelModel;
import com.datametrica.commons.ponto.model.SaldoLiquidoModel;
import com.datametrica.commons.ponto.model.SubtracaoHorasModel;
import com.datametrica.commons.ponto.model.TempoTrabalhadoAnatelModel;
import lombok.experimental.UtilityClass;

import java.time.DateTimeException;

import static com.datametrica.commons.utils.Constantes.*;
import static com.datametrica.commons.utils.Validacoes.checarSeNulo;
import static com.datametrica.commons.utils.Validacoes.isAfter;
import static com.datametrica.commons.utils.Validacoes.isBefore;
import static com.datametrica.commons.utils.Validacoes.validarFormato;

@UtilityClass
public final class SaldoLiquidoAnatel {

    public static AnatelModel calcular(SaldoLiquidoModel saldoLiquidoModel, TempoTrabalhadoAnatelModel tempos) {
        processar(saldoLiquidoModel, tempos);
        return AnatelModel.builder().saldoLiquido(saldoLiquidoModel).tempoTrabalhado(tempos).build();
    }

    private static void processar(SaldoLiquidoModel saldoLiquidoModel, TempoTrabalhadoAnatelModel tempos) {
        validarCamposLanches(tempos);
        validarDuracaoLanche(tempos);
        saldoLiquidoModel.setHorasTrabalhadas(tempoTrabalhado(tempos));
        SaldoLiquido.calcular(saldoLiquidoModel);
        AjustarLanchePadraoAnatel.ajustar(saldoLiquidoModel, tempos);
    }

    private static String tempoTrabalhado(TempoTrabalhadoAnatelModel tempos) {
        return TempoTrabalhadoAnatel.calcular(tempos);
    }

    private static void validarCamposLanches(TempoTrabalhadoAnatelModel tempos) {
        checarSeNulo(tempos.getSLanche());
        checarSeNulo(tempos.getRLanche());
        validarFormato(tempos.getSLanche());
        validarFormato(tempos.getRLanche());
    }

    private static void validarDuracaoLanche(TempoTrabalhadoAnatelModel tempos) {
        SubtracaoHorasModel saldo = TratativaHoras.reducaoHoras(tempos.getRLanche(),
                tempos.getSLanche());
        if (saldo.isNegativo()) {
            throw new DateTimeException("Saldo não pode ficar negativo no intervalo de lanche.");
        }
        if (isBefore(saldo.getTempo(), DURACAO_LANCHE_MINIMO)) {
            throw new DateTimeException("Duração do lanche inferior ao limite mínimo " + DURACAO_LANCHE_MINIMO);
        }
        if (isAfter(saldo.getTempo(), DURACAO_LANCHE_MAXIMO)) {
            throw new DateTimeException("Duração do lanche superior ao limite máximo " + DURACAO_LANCHE_MAXIMO);
        }
        if (isAfter(tempos.getSLanche(), tempos.getLogout())) {
            throw new DateTimeException("Fim do lanche não pode ser superior ao logout.");
        }
    }

}
