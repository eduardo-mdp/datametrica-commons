package ponto.calculo;

import com.datametrica.commons.ponto.calculo.TempoTrabalhado;
import com.datametrica.commons.ponto.model.TempoTrabalhadoModel;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

@ExtendWith(MockitoExtension.class)
class TempoTrabalhadoTest {

    @Test
    void deve_retornar_soma_zero() {
        List<TempoTrabalhadoModel> tempoTrabalhadoModelList = new ArrayList<>();
        Assertions.assertEquals("00:00", TempoTrabalhado.calcular(tempoTrabalhadoModelList));
    }

    @Test
    void deve_retornar_soma_positiva() {
        List<TempoTrabalhadoModel> tempoTrabalhadoModelList = new ArrayList<>();
        tempoTrabalhadoModelList.add(TempoTrabalhadoModel.builder()
                .dataHoraInicio("2021-01-01 06:00")
                .dataHoraFim("2021-01-01 09:00")
                .build());

        tempoTrabalhadoModelList.add(TempoTrabalhadoModel.builder()
                .dataHoraInicio("2021-01-01 09:20")
                .dataHoraFim("2021-01-01 12:20")
                .build());
        Assertions.assertEquals("06:00", TempoTrabalhado.calcular(tempoTrabalhadoModelList));
    }

    @Test
    void deve_retornar_soma_positiva_durante_virada_do_dia() {
        List<TempoTrabalhadoModel> tempoTrabalhadoModelList = new ArrayList<>();
        tempoTrabalhadoModelList.add(TempoTrabalhadoModel.builder()
                .dataHoraInicio("2021-01-01 22:00")
                .dataHoraFim("2021-01-02 00:00")
                .build());

        tempoTrabalhadoModelList.add(TempoTrabalhadoModel.builder()
                .dataHoraInicio("2021-01-02 00:20")
                .dataHoraFim("2021-01-02 04:20")
                .build());
        Assertions.assertEquals("06:00", TempoTrabalhado.calcular(tempoTrabalhadoModelList));
    }


}

