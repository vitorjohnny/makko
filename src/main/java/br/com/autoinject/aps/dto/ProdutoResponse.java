package br.com.autoinject.aps.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProdutoResponse {

    private Long id;
    private String codigo;
    private String descricao;
    private String unidadeMedida;
    private Boolean montado;
    private Boolean ativo;
    private Long clienteId;

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class ClienteResumido {
        private Long id;
        private String codigo;
        private String razaoSocial;
    }
}