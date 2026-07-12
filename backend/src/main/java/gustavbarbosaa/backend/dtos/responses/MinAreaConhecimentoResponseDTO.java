package gustavbarbosaa.backend.dtos.responses;

import java.util.UUID;

public record MinAreaConhecimentoResponseDTO(
        UUID id,
        String nome,
        String descricao,
        boolean ativo) {
}
