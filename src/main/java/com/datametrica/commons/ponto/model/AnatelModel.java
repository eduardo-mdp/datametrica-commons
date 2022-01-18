package com.datametrica.commons.ponto.model;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AnatelModel {

    private SaldoLiquidoModel saldoLiquido;
    private TempoTrabalhadoAnatelModel tempoTrabalhado;

}
