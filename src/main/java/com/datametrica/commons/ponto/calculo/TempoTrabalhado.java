package com.datametrica.commons.ponto.calculo;

import com.datametrica.commons.ponto.model.TempoTrabalhadoModel;

import java.util.List;

import static com.datametrica.commons.utils.Constantes.HORA_ZERADA;

public final class TempoTrabalhado {

    private TempoTrabalhado() {
        throw new IllegalStateException("Utility class");
    }

    public static String calcular(List<TempoTrabalhadoModel> tempoTrabalhadoModelList) {
        return processar(tempoTrabalhadoModelList);
    }

    private static String processar(List<TempoTrabalhadoModel> tempoTrabalhadoModelList) {
        String somaHoras = HORA_ZERADA;
        for (TempoTrabalhadoModel tempoTrabalhadoModel : tempoTrabalhadoModelList) {
            String subtracaoHoras = TratativaHoras.subtracaoDataHora(tempoTrabalhadoModel.getDataHoraInicio(),
                    tempoTrabalhadoModel.getDataHoraFim());
            somaHoras = TratativaHoras.somaHoras(somaHoras, subtracaoHoras);
        }
        return somaHoras;
    }

}
