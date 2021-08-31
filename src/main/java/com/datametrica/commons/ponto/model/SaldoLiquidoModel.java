package com.datametrica.commons.ponto.model;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SaldoLiquidoModel {

    @NonNull
    private String horasEscaladas;
    @NonNull
    private String horasTrabalhadas;
    @NonNull
    private boolean abonaDia;
    @NonNull
    private String horasAbono;
    @NonNull
    private String horasAfastamento;
    @NonNull
    private String horasAbonoGestor;
    private String saldoLiquido;
    private boolean negativo;

}
