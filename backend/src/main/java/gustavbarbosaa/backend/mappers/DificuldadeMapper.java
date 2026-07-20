package gustavbarbosaa.backend.mappers;

import gustavbarbosaa.backend.domains.Dificuldade;
import gustavbarbosaa.backend.dtos.requests.DificuldadeRequestDTO;
import gustavbarbosaa.backend.dtos.responses.DificuldadeResponseDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface DificuldadeMapper {
    Dificuldade toEntity(DificuldadeRequestDTO dto);

    DificuldadeResponseDTO toDTO(Dificuldade entity);
}
