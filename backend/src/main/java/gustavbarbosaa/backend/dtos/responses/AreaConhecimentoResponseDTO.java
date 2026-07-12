package gustavbarbosaa.backend.dtos.responses;

import java.time.LocalDateTime;

public record AreaConhecimentoResponseDTO(
        String nome,
        String descricao,
        boolean ativo,
        LocalDateTime criadoEm,
        LocalDateTime atualizadoEm) { }
