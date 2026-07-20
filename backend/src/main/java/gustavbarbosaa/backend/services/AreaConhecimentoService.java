package gustavbarbosaa.backend.services;

import gustavbarbosaa.backend.domains.AreaConhecimento;
import gustavbarbosaa.backend.dtos.requests.AreaConhecimentoRequestDTO;
import gustavbarbosaa.backend.dtos.responses.AreaConhecimentoResponseDTO;
import gustavbarbosaa.backend.dtos.responses.MinAreaConhecimentoResponseDTO;
import gustavbarbosaa.backend.exceptions.NomeAreaConhecimentoExistenteException;
import gustavbarbosaa.backend.exceptions.RecursoNaoEncontradoException;
import gustavbarbosaa.backend.mappers.AreaConhecimentoMapper;
import gustavbarbosaa.backend.repositories.AreaConhecimentoRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AreaConhecimentoService {
    private final AreaConhecimentoRepository areaConhecimentoRepository;
    private final AreaConhecimentoMapper areaConhecimentoMapper;

    public AreaConhecimentoResponseDTO buscarPorId(UUID id) {
        return areaConhecimentoMapper.toDTO(this.buscaAreaConhecimentoPorId(id));
    }

    public List<AreaConhecimentoResponseDTO> listarTodos() {
        return areaConhecimentoRepository.findAll()
                .stream()
                .map(areaConhecimentoMapper::toDTO)
                .toList();
    }

    public List<AreaConhecimentoResponseDTO> listarAtivos() {
        return areaConhecimentoRepository.findAllByAtivoTrue()
                .stream()
                .map(areaConhecimentoMapper::toDTO)
                .toList();
    }

    @Transactional
    public MinAreaConhecimentoResponseDTO criar(@Valid AreaConhecimentoRequestDTO request) {
        this.validaNomeEmAreasDeConhecimentoExistentes(request, null);

        AreaConhecimento areaConhecimento = areaConhecimentoMapper.toEntity(request);
        return areaConhecimentoMapper.toMinDTO(areaConhecimentoRepository.save(areaConhecimento));
    }

    @Transactional
    public AreaConhecimentoResponseDTO editar(UUID id, @Valid AreaConhecimentoRequestDTO request) {
        AreaConhecimento areaConhecimento = this.buscaAreaConhecimentoPorId(id);

        this.validaNomeEmAreasDeConhecimentoExistentes(request, id);

        areaConhecimento.setNome(request.nome());
        areaConhecimento.setDescricao(request.descricao());

        return areaConhecimentoMapper.toDTO(areaConhecimentoRepository.save(areaConhecimento));
    }

    @Transactional
    public void remover(UUID id) {
        AreaConhecimento areaConhecimento = this.buscaAreaConhecimentoPorId(id);
        areaConhecimento.setAtivo(false);
        areaConhecimentoRepository.save(areaConhecimento);
    }

    private AreaConhecimento buscaAreaConhecimentoPorId(UUID id) {
        return areaConhecimentoRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Não foi possível encontrar a Área de conhecimento"));
    }

    public void validaNomeEmAreasDeConhecimentoExistentes(AreaConhecimentoRequestDTO request, UUID id) {
        boolean existeAreaComMesmoNome = areaConhecimentoRepository.existsByNomeIgnoreCaseAndAtivoTrue(request.nome());

        if (id != null) {
            existeAreaComMesmoNome = areaConhecimentoRepository.existsByNomeIgnoreCaseAndAtivoTrueAndIdNot(request.nome(), id);
        }

        if(existeAreaComMesmoNome) {
            throw new NomeAreaConhecimentoExistenteException("Já existe uma área de conhecimento com este mesmo nome, utilize outro.");
        }
    }
}
