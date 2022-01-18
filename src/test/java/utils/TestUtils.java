package utils;

import com.datametrica.commons.ponto.model.SaldoLiquidoModel;
import com.datametrica.commons.ponto.model.TempoTrabalhadoAnatelModel;
import lombok.experimental.UtilityClass;

import static com.datametrica.commons.utils.Constantes.ESCALA_0600;
import static com.datametrica.commons.utils.Constantes.HORA_ZERADA;

@UtilityClass
public class TestUtils {

    public static SaldoLiquidoModel saldoLiquidoModel() {
        return SaldoLiquidoModel.builder()
                .abonaDia(false)
                .horasAbono(HORA_ZERADA)
                .horasEscaladas(ESCALA_0600)
                .horasTrabalhadas(HORA_ZERADA)
                .horasAfastamento(HORA_ZERADA)
                .horasAbonoGestor(HORA_ZERADA)
                .build();
    }

    public static TempoTrabalhadoAnatelModel apontamentosSemHoraExtra0600() {
        return TempoTrabalhadoAnatelModel.builder()
                .login("08:00")
                .sLanche("12:00")
                .rLanche("12:20")
                .logout("14:20")
                .build();
    }

    public static TempoTrabalhadoAnatelModel apontamentosComHoraExtra_0100_0600() {
        return TempoTrabalhadoAnatelModel.builder()
                .login("08:00")
                .sLanche("12:00")
                .rLanche("12:20")
                .logout("13:20")
                .build();
    }

}
