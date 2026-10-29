package br.com.autoinject.aps.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "itens_planejamento")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class ItemPlanejamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @Column(name = "planejamento", nullable = false)
    private String planejamento;

    @Column(name = "ordem_producao", nullable = false)
    private String ordemProducao;

    @Column(name = "maquina", nullable = false)
    private String maquina;

    @Column(name = "molde")
    private String molde;

    @Column(name = "inicio_previsto", nullable = false)
    private String inicioPrevisto;

    @Column(name = "fim_previsto", nullable = false)
    private String fimPrevisto;

    @Column(name = "inicio_real")
    private String inicioReal;

    @Column(name = "fim_real")
    private String fimReal;

    @Column(name = "quantidade_programada", nullable = false)
    private Integer quantidadeProgramada;

    @Column(name = "sequencia", nullable = false)
    private Integer sequencia;

    @Column(name = "status", nullable = false, length = 20)
    private String status;

    @Builder.Default
    @Column(name = "ativo", nullable = false)
    private Boolean ativo = true;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}