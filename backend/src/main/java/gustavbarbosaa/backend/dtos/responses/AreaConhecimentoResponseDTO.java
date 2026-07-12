package gustavbarbosaa.backend.dtos.responses;

import java.time.LocalDateTime;
import java.util.UUID;

public record AreaConhecimentoResponseDTO(
        UUID id,
        String nome,
        String descricao,
        boolean ativo,
        LocalDateTime criadoEm,
        LocalDateTime atualizadoEm) { }
