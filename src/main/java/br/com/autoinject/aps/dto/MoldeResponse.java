package br.com.autoinject.aps.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MoldeResponse {

    private Long id;
    private String codigo;
    private String descricao;
    private Integer cavidadesPadrao;
    private String situacao;
    private Boolean ativo;
}