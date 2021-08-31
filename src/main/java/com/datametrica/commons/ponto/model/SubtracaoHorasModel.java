package com.datametrica.commons.ponto.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SubtracaoHorasModel {

    private String tempo;
    private boolean negativo;

}
