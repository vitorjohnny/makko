package br.com.autoinject.aps.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ItemPlanejamentoResponse {

    private Long id;
    private String planejamento;
    private String ordemProducao;
    private String maquina;
    private String molde;
    private String inicioPrevisto;
    private String fimPrevisto;
    private String inicioReal;
    private String fimReal;
    private Integer quantidadeProgramada;
    private Integer sequencia;
    private String status;
    private Boolean ativo;
}