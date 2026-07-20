package gustavbarbosaa.backend.domains;

import gustavbarbosaa.backend.enums.PrioridadeEnum;
import gustavbarbosaa.backend.enums.StatusEnum;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "dificuldade")
public class Dificuldade {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_area_conhecimento", nullable = false, referencedColumnName = "id")
    private AreaConhecimento areaConhecimento;

    @Column(length = 120, nullable = false)
    private String tema;

    @Column(length = 500)
    private String descricao;

    @Column(length = 500)
    private String contexto;

    @Column(length = 500, nullable = false)
    private String objetivo;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PrioridadeEnum prioridade;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private StatusEnum status = StatusEnum.PENDENTE;

    @Column(name = "criado_em", nullable = false)
    @CreationTimestamp
    private LocalDateTime criadoEm;

    @Column(name = "atualizado_em")
    @UpdateTimestamp
    private LocalDateTime atualizadoEm;
}
