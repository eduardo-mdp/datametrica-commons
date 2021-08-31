package com.datametrica.commons.ponto.model;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TempoTrabalhadoModel {

    @NonNull
    private String dataHoraInicio;
    @NonNull
    private String dataHoraFim;

}
