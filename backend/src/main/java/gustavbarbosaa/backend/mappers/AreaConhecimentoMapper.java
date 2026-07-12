package gustavbarbosaa.backend.mappers;

import gustavbarbosaa.backend.domains.AreaConhecimento;
import gustavbarbosaa.backend.dtos.requests.AreaConhecimentoRequestDTO;
import gustavbarbosaa.backend.dtos.responses.AreaConhecimentoResponseDTO;
import gustavbarbosaa.backend.dtos.responses.MinAreaConhecimentoResponseDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AreaConhecimentoMapper {
    AreaConhecimento toEntity(AreaConhecimentoRequestDTO dto);

    AreaConhecimentoResponseDTO toDTO(AreaConhecimento entity);

    MinAreaConhecimentoResponseDTO toMinDTO(AreaConhecimento entity);
}
