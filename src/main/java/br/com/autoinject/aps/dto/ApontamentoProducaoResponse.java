package br.com.autoinject.aps.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ApontamentoProducaoResponse {

    private Long id;
    private String ordem;
    private String operador;
    private String maquina;
    private Integer quantidade;
    private String turno;
    private LocalDateTime dataHora;
    private Boolean ativo;
}