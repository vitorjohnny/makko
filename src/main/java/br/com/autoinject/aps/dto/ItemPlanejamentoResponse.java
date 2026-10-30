
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

    private Long planejamentoId;
    private String planejamento;

    private Long ordemProducaoId;
    private String ordemProducao;

    private Long maquinaId;
    private String maquina;

    private Long moldeId;
    private String molde;

    private LocalDateTime inicioPrevisto;
    private LocalDateTime fimPrevisto;
    private LocalDateTime inicioReal;
    private LocalDateTime fimReal;

    private Integer quantidadeProgramada;
    private Integer sequencia;

    private String status;
    private Boolean riscoAtraso;
    private Boolean ativo;
}
