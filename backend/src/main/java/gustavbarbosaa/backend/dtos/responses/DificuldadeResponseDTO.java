package gustavbarbosaa.backend.dtos.responses;

import gustavbarbosaa.backend.enums.PrioridadeEnum;
import gustavbarbosaa.backend.enums.StatusEnum;

import java.util.UUID;

public record DificuldadeResponseDTO(
        UUID id,
        UUID idAreaConhecimento,
        String tema,
        String descricao,
        String contexto,
        String objetivo,
        PrioridadeEnum prioridade,
        StatusEnum status
) { }
