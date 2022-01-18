package com.datametrica.commons.ponto.calculo.anatel;

import com.datametrica.commons.ponto.calculo.TratativaHoras;
import com.datametrica.commons.ponto.model.SubtracaoHorasModel;
import com.datametrica.commons.ponto.model.TempoTrabalhadoAnatelModel;
import lombok.experimental.UtilityClass;

import java.time.DateTimeException;

@UtilityClass
public final class TempoTrabalhadoAnatel {

    public static String calcular(TempoTrabalhadoAnatelModel tempos) {
        return processar(tempos);
    }

    private static String processar(TempoTrabalhadoAnatelModel tempos) {
        SubtracaoHorasModel intervalo1 = TratativaHoras.reducaoHoras(tempos.getSLanche(), tempos.getLogin());
        SubtracaoHorasModel intervalo2 = TratativaHoras.reducaoHoras(tempos.getLogout(), tempos.getRLanche());

        if (intervalo1.isNegativo() || intervalo2.isNegativo()) {
            throw new DateTimeException("Saldo não pode ficar negativo no intervalo de tempo trabalhado.");
        }

        return TratativaHoras.somaHoras(intervalo1.getTempo(), intervalo2.getTempo());
    }

}
