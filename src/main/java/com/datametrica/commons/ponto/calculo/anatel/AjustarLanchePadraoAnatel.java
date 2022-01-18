package com.datametrica.commons.ponto.calculo.anatel;

import com.datametrica.commons.ponto.model.SaldoLiquidoModel;
import com.datametrica.commons.ponto.model.SubtracaoHorasModel;
import com.datametrica.commons.ponto.model.TempoTrabalhadoAnatelModel;
import lombok.experimental.UtilityClass;

import static com.datametrica.commons.ponto.calculo.TratativaHoras.reducaoHoras;
import static com.datametrica.commons.ponto.calculo.TratativaHoras.somaHoras;
import static com.datametrica.commons.utils.Constantes.*;
import static com.datametrica.commons.utils.Validacoes.isAfter;
import static com.datametrica.commons.utils.Validacoes.isBefore;

@UtilityClass
public final class AjustarLanchePadraoAnatel {

    public static void ajustar(SaldoLiquidoModel saldo, TempoTrabalhadoAnatelModel tempos) {

        switch (saldo.getHorasEscaladas()) {
            case ESCALA_0600:
            case ESCALA_0500:
                ajustarPorEscala(saldo, tempos, DURACAO_HE_MINIMO, DURACAO_LANCHE_MINIMO_HE);
                break;
            case ESCALA_0712:
                ajustarPorEscala(saldo, tempos, DURACAO_HE_MINIMO_0712, DURACAO_LANCHE_MINIMO_HE_0712);
                break;
            default:
                break;
        }

    }

    private void ajustarPorEscala(SaldoLiquidoModel saldo, TempoTrabalhadoAnatelModel tempos, String hEMinimo, String lancheMinimo) {
        boolean heMinima = isAfter(saldo.getSaldoLiquido(), hEMinimo) || saldo.getSaldoLiquido().equals(hEMinimo);
        SubtracaoHorasModel intervaloLanche = reducaoHoras(tempos.getRLanche(), tempos.getSLanche());
        boolean lancheMenorMinimo = isBefore(intervaloLanche.getTempo(), lancheMinimo);
        if (!saldo.isNegativo() && heMinima && lancheMenorMinimo) {
            String novoRetornoLanche = somaHoras(tempos.getSLanche(), lancheMinimo);
            tempos.setRLanche(novoRetornoLanche);
            SaldoLiquidoAnatel.calcular(saldo, tempos);
        }
    }

}
