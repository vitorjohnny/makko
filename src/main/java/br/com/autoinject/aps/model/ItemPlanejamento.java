
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "planejamento_id", nullable = false)
    private Planejamento planejamento;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ordem_producao_id", nullable = false)
    private OrdemProducao ordemProducao;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "maquina_id", nullable = false)
    private Maquina maquina;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "molde_id")
    private Molde molde;

    @Column(name = "inicio_previsto", nullable = false)
    private LocalDateTime inicioPrevisto;

    @Column(name = "fim_previsto", nullable = false)
    private LocalDateTime fimPrevisto;

    @Column(name = "inicio_real")
    private LocalDateTime inicioReal;

    @Column(name = "fim_real")
    private LocalDateTime fimReal;

    @Column(name = "quantidade_programada", nullable = false)
    private Integer quantidadeProgramada;

    @Column(name = "sequencia", nullable = false)
    private Integer sequencia;

    @Column(name = "status", nullable = false, length = 20)
    private String status;

    @Column(name = "risco_atraso", nullable = false)
    @Builder.Default
    private Boolean riscoAtraso = false;

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
