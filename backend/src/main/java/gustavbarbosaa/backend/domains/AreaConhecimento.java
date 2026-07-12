package gustavbarbosaa.backend.domains;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "area_conhecimento")
public class AreaConhecimento {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(length = 120, nullable = false, unique = true)
    private String nome;

    @Column(length = 500)
    private String descricao;

    @Column(nullable = false, columnDefinition = "boolean default true")
    private boolean ativo = true;

    @Column(name = "criado_em", nullable = false)
    private LocalDateTime criadoEm;

    @Column(name = "atualizado_em")
    private LocalDateTime atualizadoEm;
}
