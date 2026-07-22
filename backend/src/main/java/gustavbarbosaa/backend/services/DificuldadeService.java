package gustavbarbosaa.backend.services;

import gustavbarbosaa.backend.domains.Dificuldade;
import gustavbarbosaa.backend.dtos.responses.DificuldadeResponseDTO;
import gustavbarbosaa.backend.exceptions.RecursoNaoEncontradoException;
import gustavbarbosaa.backend.mappers.DificuldadeMapper;
import gustavbarbosaa.backend.repositories.DificuldadeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DificuldadeService {
    private final DificuldadeRepository dificuldadeRepository;
    private final DificuldadeMapper dificuldadeMapper;

    public DificuldadeResponseDTO buscarPorId(UUID id) {
        return dificuldadeMapper.toDTO(this.buscarDificuldadePorId(id));
    }

    public Dificuldade buscarDificuldadePorId(UUID id) {
        return dificuldadeRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Não foi possível encontrar Dificuldade"));
    }
}
