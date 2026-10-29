package br.com.autoinject.aps.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ParametroProducaoResponse {

    private Long id;
    private Long produtoId;
    private String produtoCodigo;
    private String produtoDescricao;
    private Long maquinaId;
    private String maquinaCodigo;
    private String maquinaNome;
    private Long moldeId;
    private String moldeCodigo;
    private String moldeDescricao;
    private Integer cicloSegundos;
    private Boolean ativo;
}