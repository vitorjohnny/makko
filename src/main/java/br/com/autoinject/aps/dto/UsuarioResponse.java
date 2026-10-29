package br.com.autoinject.aps.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UsuarioResponse {

    private Long id;
    private String nome;
    private String email;
    private String perfil;
    private Boolean ativo;
}