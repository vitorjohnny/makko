package br.com.autoinject.aps.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrdemProducaoResponse {

    private Long id;
    private String numero;
    private Long clienteId;
    private String clienteCodigo;
    private String clienteRazaoSocial;
    private Long produtoId;
    private String produtoCodigo;
    private String produtoDescricao;
    private Integer quantidadePlanejada;
    private Integer quantidadeProduzida;
    private LocalDateTime dataEntrega;
    private Integer prioridade;
    private String status;
    private String arquivoOrigem;
    private LocalDateTime dataImportacao;
    private Boolean ativo;
}