package com.datametrica.commons.ponto.model;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TempoTrabalhadoAnatelModel {

    @NonNull
    private String login;
    private String sDesc1;
    private String rDesc1;
    private String sLanche;
    private String rLanche;
    private String sDesc2;
    private String rDesc2;
    @NonNull
    private String logout;

}
