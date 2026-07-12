package gustavbarbosaa.backend.dtos.requests;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public record AreaConhecimentoRequestDTO(
        @NotEmpty(message = "O campo nome é obrigatório.")
        @Size(message = "O campo deve ter no máximo 120 caracteres.")
        String nome,
        @Size(message = "O campo deve ter no máximo 500 caracteres.")
        String descricao
) { }
