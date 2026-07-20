package gustavbarbosaa.backend.dtos.requests;

import gustavbarbosaa.backend.enums.PrioridadeEnum;
import gustavbarbosaa.backend.enums.StatusEnum;
import jakarta.validation.constraints.NotBlank;

import java.util.UUID;

public record DificuldadeRequestDTO(
        @NotBlank(message = "Uma dificuldade precisa estar vinculada à uma área de conhecimento.")
        UUID idAreaConhecimento,
        @NotBlank(message = "O tema é obrigatório.")
        String tema,
        String descricao,
        String contexto,
        @NotBlank(message = "O objetivo é obrigatório.")
        String objetivo,
        @NotBlank(message = "É necessário informar uma prioridade.")
        PrioridadeEnum prioridade,
        @NotBlank(message = "É necessário informar um status.")
        StatusEnum status
) { }
