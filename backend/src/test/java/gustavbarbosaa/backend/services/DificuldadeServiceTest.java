package gustavbarbosaa.backend.services;

import gustavbarbosaa.backend.domains.AreaConhecimento;
import gustavbarbosaa.backend.domains.Dificuldade;
import gustavbarbosaa.backend.dtos.requests.DificuldadeRequestDTO;
import gustavbarbosaa.backend.dtos.responses.DificuldadeResponseDTO;
import gustavbarbosaa.backend.enums.PrioridadeEnum;
import gustavbarbosaa.backend.enums.StatusEnum;
import gustavbarbosaa.backend.exceptions.RecursoNaoEncontradoException;
import gustavbarbosaa.backend.mappers.DificuldadeMapper;
import gustavbarbosaa.backend.repositories.DificuldadeRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DificuldadeServiceTest {
    private static final String TEMA = "Tema dificuldade";
    private static final String DESCRICAO = "Descrição dificuldade";
    private static final String CONTEXTO = "Contexto dificuldade";
    private static final String OBJETIVO = "Objetivo dificuldade";
    private static final PrioridadeEnum PRIORIDADE = PrioridadeEnum.BAIXA;
    private static final StatusEnum STATUS = StatusEnum.PENDENTE;
    private static final String NOME_AREA_CONHECIMENTO = "Nome área";
    private static final String DESCRICAO_AREA_CONHECIMENTO = "Descrição área";

    @Mock
    private DificuldadeRepository dificuldadeRepository;

    @Mock
    private DificuldadeMapper dificuldadeMapper;

    @InjectMocks
    private DificuldadeService dificuldadeService;

    private UUID geraIdValido() {
        return UUID.randomUUID();
    }

    private AreaConhecimento geraAreaConhecimentoValido() {
        return new AreaConhecimento(geraIdValido(), NOME_AREA_CONHECIMENTO, DESCRICAO_AREA_CONHECIMENTO, true, geraDataValida(), null);
    }

    private LocalDateTime geraDataValida() {
        return LocalDateTime.now();
    }

    private Dificuldade geraDificuldadeValido() {
        return new Dificuldade(geraIdValido(), geraAreaConhecimentoValido(), TEMA, DESCRICAO, CONTEXTO, OBJETIVO, PRIORIDADE, STATUS, geraDataValida(), null);
    }

    private DificuldadeRequestDTO geraRequestValido() {
        return new DificuldadeRequestDTO(geraIdValido(), TEMA, DESCRICAO, CONTEXTO, OBJETIVO, PRIORIDADE, STATUS);
    }

    private DificuldadeResponseDTO geraResponseValido(Dificuldade dificuldade) {
        return new DificuldadeResponseDTO(
                dificuldade.getId(),
                geraAreaConhecimentoValido().getId(),
                dificuldade.getTema(),
                dificuldade.getDescricao(),
                dificuldade.getContexto(),
                dificuldade.getObjetivo(),
                dificuldade.getPrioridade(),
                dificuldade.getStatus()
        );
    }


    @Test
    @DisplayName("Deve retornar uma dificuldade caso exista")
    void deveBuscarDificuldadePorId() {
        Dificuldade dificuldade = geraDificuldadeValido();
        DificuldadeResponseDTO respostaEsperada = geraResponseValido(dificuldade);

        when(dificuldadeRepository.findById(dificuldade.getId())).thenReturn(Optional.of(dificuldade));
        when(dificuldadeMapper.toDTO(dificuldade)).thenReturn(respostaEsperada);

        DificuldadeResponseDTO resposta = dificuldadeService.buscarPorId(dificuldade.getId());

        assertNotNull(resposta);
        assertEquals(resposta, respostaEsperada);

        verify(dificuldadeRepository).findById(dificuldade.getId());
        verify(dificuldadeMapper).toDTO(dificuldade);
        verifyNoMoreInteractions(dificuldadeRepository, dificuldadeMapper);
    }

    @Test
    @DisplayName("Deve lançar exceção caso não exista dificuldade com o id informado")
    void deveLancarExcecaoDeRecursoNaoEncontradoCasoRecursoNaoExista() {
        UUID id = geraIdValido();

        when(dificuldadeRepository.findById(id)).thenReturn(Optional.empty());

        RecursoNaoEncontradoException excecao = assertThrows(
                RecursoNaoEncontradoException.class,
                () -> dificuldadeService.buscarPorId(id)
        );

        assertEquals("Não foi possível encontrar Dificuldade", excecao.getMessage());

        verify(dificuldadeRepository).findById(id);
        verifyNoInteractions(dificuldadeMapper);
        verifyNoMoreInteractions(dificuldadeRepository);
    }

}