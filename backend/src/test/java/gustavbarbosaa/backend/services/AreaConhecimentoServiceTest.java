package gustavbarbosaa.backend.services;

import gustavbarbosaa.backend.domains.AreaConhecimento;
import gustavbarbosaa.backend.dtos.requests.AreaConhecimentoRequestDTO;
import gustavbarbosaa.backend.dtos.responses.AreaConhecimentoResponseDTO;
import gustavbarbosaa.backend.dtos.responses.MinAreaConhecimentoResponseDTO;
import gustavbarbosaa.backend.exceptions.NomeAreaConhecimentoExistenteException;
import gustavbarbosaa.backend.exceptions.RecursoNaoEncontradoException;
import gustavbarbosaa.backend.mappers.AreaConhecimentoMapper;
import gustavbarbosaa.backend.repositories.AreaConhecimentoRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AreaConhecimentoServiceTest {

    private static final String NOME = "Git";
    private static final String DESCRICAO = "Estudando git";
    private static final String NOVO_NOME = "Java";
    private static final String NOVA_DESCRICAO = "Estudando java";

    @Mock
    private AreaConhecimentoRepository areaConhecimentoRepository;

    @Mock
    private AreaConhecimentoMapper areaConhecimentoMapper;

    @InjectMocks
    private AreaConhecimentoService areaConhecimentoService;

    private UUID geraIdValido() {
        return UUID.randomUUID();
    }

    private LocalDateTime geraDataValida() {
        return LocalDateTime.of(2026, Month.JULY, 20, 10, 0);
    }

    private AreaConhecimentoRequestDTO geraRequestValido() {
        return new AreaConhecimentoRequestDTO(NOME, DESCRICAO);
    }

    private AreaConhecimentoRequestDTO geraRequestEdicaoValido() {
        return new AreaConhecimentoRequestDTO(NOVO_NOME, NOVA_DESCRICAO);
    }

    private AreaConhecimento geraAreaConhecimentoValido() {
        return new AreaConhecimento(geraIdValido(), NOME, DESCRICAO, true, geraDataValida(), null);
    }

    private AreaConhecimento geraAreaConhecimentoValido(UUID id, LocalDateTime criadoEm, LocalDateTime atualizadoEm) {
        return new AreaConhecimento(id, NOME, DESCRICAO, true, criadoEm, atualizadoEm);
    }

    private AreaConhecimentoResponseDTO gerarAreaConhecimentoResponse(AreaConhecimento areaConhecimento) {
        return new AreaConhecimentoResponseDTO(
                areaConhecimento.getId(),
                areaConhecimento.getNome(),
                areaConhecimento.getDescricao(),
                areaConhecimento.isAtivo(),
                areaConhecimento.getCriadoEm(),
                areaConhecimento.getAtualizadoEm()
        );
    }

    private MinAreaConhecimentoResponseDTO gerarAreaConhecimentoMinResponse(AreaConhecimento areaConhecimento) {
        return new MinAreaConhecimentoResponseDTO(
                areaConhecimento.getId(),
                areaConhecimento.getNome(),
                areaConhecimento.getDescricao(),
                areaConhecimento.isAtivo()
        );
    }

    @Test
    @DisplayName("Deve retornar área de conhecimento caso id exista")
    void deveBuscarAreaConhecimentoPorId() {
        AreaConhecimento areaConhecimento = geraAreaConhecimentoValido();
        AreaConhecimentoResponseDTO respostaEsperada = gerarAreaConhecimentoResponse(areaConhecimento);

        when(areaConhecimentoRepository.findById(areaConhecimento.getId())).thenReturn(Optional.of(areaConhecimento));
        when(areaConhecimentoMapper.toDTO(areaConhecimento)).thenReturn(respostaEsperada);

        AreaConhecimentoResponseDTO resposta = areaConhecimentoService.buscarPorId(areaConhecimento.getId());

        assertNotNull(resposta);
        assertEquals(respostaEsperada, resposta);

        verify(areaConhecimentoRepository).findById(areaConhecimento.getId());
        verify(areaConhecimentoMapper).toDTO(areaConhecimento);
        verifyNoMoreInteractions(areaConhecimentoRepository, areaConhecimentoMapper);
    }

    @Test
    @DisplayName("Deve lançar exceção caso não exista área de conhecimento com o id informado")
    void deveLancarExcecaoDeRecursoNaoEncontradoCasoRecursoNaoExista() {
        UUID id = geraIdValido();

        when(areaConhecimentoRepository.findById(id)).thenReturn(Optional.empty());

        RecursoNaoEncontradoException excecao = assertThrows(
                RecursoNaoEncontradoException.class,
                () -> areaConhecimentoService.buscarPorId(id)
        );

        assertEquals("Não foi possível encontrar a Área de conhecimento", excecao.getMessage());

        verify(areaConhecimentoRepository).findById(id);
        verifyNoInteractions(areaConhecimentoMapper);
        verifyNoMoreInteractions(areaConhecimentoRepository);
    }

    @Test
    @DisplayName("Deve cadastrar área de conhecimento corretamente caso os dados sejam válidos")
    void deveCadastrarAreaConhecimentoCasoDadosSejamValidos() {
        AreaConhecimentoRequestDTO requisicao = geraRequestValido();
        AreaConhecimento areaConhecimento = geraAreaConhecimentoValido();
        MinAreaConhecimentoResponseDTO respostaEsperada = gerarAreaConhecimentoMinResponse(areaConhecimento);

        when(areaConhecimentoRepository.existsByNomeIgnoreCaseAndAtivoTrue(NOME)).thenReturn(false);
        when(areaConhecimentoMapper.toEntity(requisicao)).thenReturn(areaConhecimento);
        when(areaConhecimentoRepository.save(areaConhecimento)).thenReturn(areaConhecimento);
        when(areaConhecimentoMapper.toMinDTO(areaConhecimento)).thenReturn(respostaEsperada);

        MinAreaConhecimentoResponseDTO resposta = areaConhecimentoService.criar(requisicao);

        assertNotNull(resposta);
        assertEquals(respostaEsperada, resposta);

        verify(areaConhecimentoRepository).existsByNomeIgnoreCaseAndAtivoTrue(NOME);
        verify(areaConhecimentoMapper).toEntity(requisicao);
        verify(areaConhecimentoRepository).save(areaConhecimento);
        verify(areaConhecimentoMapper).toMinDTO(areaConhecimento);
        verifyNoMoreInteractions(areaConhecimentoRepository, areaConhecimentoMapper);
    }

    @Test
    @DisplayName("Deve falhar caso já exista área de conhecimento ativa com o mesmo nome ao criar")
    void deveFalharCasoJaExistaAreaConhecimentoAtivaComMesmoNomeAoCriar() {
        AreaConhecimentoRequestDTO requisicao = geraRequestValido();

        when(areaConhecimentoRepository.existsByNomeIgnoreCaseAndAtivoTrue(NOME)).thenReturn(true);

        NomeAreaConhecimentoExistenteException excecao = assertThrows(
                NomeAreaConhecimentoExistenteException.class,
                () -> areaConhecimentoService.criar(requisicao)
        );

        assertEquals("Já existe uma área de conhecimento com este mesmo nome, utilize outro.", excecao.getMessage());

        verify(areaConhecimentoRepository).existsByNomeIgnoreCaseAndAtivoTrue(NOME);
        verifyNoInteractions(areaConhecimentoMapper);
        verifyNoMoreInteractions(areaConhecimentoRepository);
    }

    @Test
    @DisplayName("Deve retornar uma lista com áreas de conhecimento")
    void deveRetornarUmaListaComUmValor() {
        AreaConhecimento areaConhecimento = geraAreaConhecimentoValido();
        AreaConhecimentoResponseDTO itemEsperado = gerarAreaConhecimentoResponse(areaConhecimento);

        when(areaConhecimentoRepository.findAll()).thenReturn(List.of(areaConhecimento));
        when(areaConhecimentoMapper.toDTO(areaConhecimento)).thenReturn(itemEsperado);

        List<AreaConhecimentoResponseDTO> resposta = areaConhecimentoService.listarTodos();

        assertNotNull(resposta);
        assertEquals(List.of(itemEsperado), resposta);

        verify(areaConhecimentoRepository).findAll();
        verify(areaConhecimentoMapper).toDTO(areaConhecimento);
        verifyNoMoreInteractions(areaConhecimentoRepository, areaConhecimentoMapper);
    }

    @Test
    @DisplayName("Deve retornar uma lista vazia ao listar todas as áreas")
    void deveRetornarUmaListaVazia() {
        when(areaConhecimentoRepository.findAll()).thenReturn(List.of());

        List<AreaConhecimentoResponseDTO> resposta = areaConhecimentoService.listarTodos();

        assertNotNull(resposta);
        assertTrue(resposta.isEmpty());

        verify(areaConhecimentoRepository).findAll();
        verifyNoInteractions(areaConhecimentoMapper);
        verifyNoMoreInteractions(areaConhecimentoRepository);
    }

    @Test
    @DisplayName("Deve retornar uma lista com apenas as áreas de conhecimento ativas")
    void deveRetornarUmaListaComApenasAreasConhecimentoAtivas() {
        AreaConhecimento areaConhecimento = geraAreaConhecimentoValido();
        AreaConhecimentoResponseDTO resultadoDTOEsperado = gerarAreaConhecimentoResponse(areaConhecimento);

        when(areaConhecimentoRepository.findAllByAtivoTrue()).thenReturn(List.of(areaConhecimento));
        when(areaConhecimentoMapper.toDTO(areaConhecimento)).thenReturn(resultadoDTOEsperado);

        List<AreaConhecimentoResponseDTO> resposta = areaConhecimentoService.listarAtivos();

        assertNotNull(resposta);
        assertEquals(List.of(resultadoDTOEsperado), resposta);

        verify(areaConhecimentoRepository).findAllByAtivoTrue();
        verify(areaConhecimentoMapper).toDTO(areaConhecimento);
        verifyNoMoreInteractions(areaConhecimentoRepository, areaConhecimentoMapper);
    }

    @Test
    @DisplayName("Deve retornar uma lista vazia caso não existam áreas ativas")
    void deveRetornarUmaListaVaziaSeNaoExistiremAreasAtivas() {
        when(areaConhecimentoRepository.findAllByAtivoTrue()).thenReturn(List.of());

        List<AreaConhecimentoResponseDTO> resposta = areaConhecimentoService.listarAtivos();

        assertNotNull(resposta);
        assertTrue(resposta.isEmpty());

        verify(areaConhecimentoRepository).findAllByAtivoTrue();
        verifyNoInteractions(areaConhecimentoMapper);
        verifyNoMoreInteractions(areaConhecimentoRepository);
    }

    @Test
    @DisplayName("Deve inativar área de conhecimento corretamente caso exista")
    void deveInativarAreaConhecimentoCasoExista() {
        AreaConhecimento areaConhecimento = geraAreaConhecimentoValido();

        when(areaConhecimentoRepository.findById(areaConhecimento.getId())).thenReturn(Optional.of(areaConhecimento));
        when(areaConhecimentoRepository.save(areaConhecimento)).thenReturn(areaConhecimento);

        areaConhecimentoService.remover(areaConhecimento.getId());

        assertFalse(areaConhecimento.isAtivo());

        verify(areaConhecimentoRepository).findById(areaConhecimento.getId());
        verify(areaConhecimentoRepository).save(areaConhecimento);
        verifyNoMoreInteractions(areaConhecimentoRepository, areaConhecimentoMapper);
    }

    @Test
    @DisplayName("Deve lançar exceção ao tentar remover área de conhecimento inexistente")
    void deveLancarExcecaoAoTentarRemoverAreaConhecimentoInexistente() {
        UUID id = geraIdValido();

        when(areaConhecimentoRepository.findById(id)).thenReturn(Optional.empty());

        RecursoNaoEncontradoException excecao = assertThrows(
                RecursoNaoEncontradoException.class,
                () -> areaConhecimentoService.remover(id)
        );

        assertEquals("Não foi possível encontrar a Área de conhecimento", excecao.getMessage());

        verify(areaConhecimentoRepository).findById(id);
        verifyNoInteractions(areaConhecimentoMapper);
        verifyNoMoreInteractions(areaConhecimentoRepository);
    }

    @Test
    @DisplayName("Deve editar área de conhecimento corretamente caso os dados sejam válidos")
    void deveEditarAreaConhecimentoCasoDadosSejamValidos() {
        UUID id = geraIdValido();
        LocalDateTime criadoEm = geraDataValida();
        LocalDateTime atualizadoEm = criadoEm.plusDays(1);

        AreaConhecimento areaConhecimento = geraAreaConhecimentoValido(id, criadoEm, null);
        AreaConhecimentoRequestDTO requisicao = geraRequestEdicaoValido();

        areaConhecimento.setNome(NOVO_NOME);
        areaConhecimento.setDescricao(NOVA_DESCRICAO);
        areaConhecimento.setAtualizadoEm(atualizadoEm);

        AreaConhecimentoResponseDTO respostaEsperada = new AreaConhecimentoResponseDTO(
                id,
                NOVO_NOME,
                NOVA_DESCRICAO,
                true,
                criadoEm,
                atualizadoEm
        );

        when(areaConhecimentoRepository.findById(id)).thenReturn(Optional.of(areaConhecimento));
        when(areaConhecimentoRepository.existsByNomeIgnoreCaseAndAtivoTrueAndIdNot(NOVO_NOME, id)).thenReturn(false);
        when(areaConhecimentoRepository.save(areaConhecimento)).thenReturn(areaConhecimento);
        when(areaConhecimentoMapper.toDTO(areaConhecimento)).thenReturn(respostaEsperada);

        AreaConhecimentoResponseDTO resposta = areaConhecimentoService.editar(id, requisicao);

        assertNotNull(resposta);
        assertEquals(respostaEsperada, resposta);

        verify(areaConhecimentoRepository).findById(id);
        verify(areaConhecimentoRepository).existsByNomeIgnoreCaseAndAtivoTrue(NOVO_NOME);
        verify(areaConhecimentoRepository).existsByNomeIgnoreCaseAndAtivoTrueAndIdNot(NOVO_NOME, id);
        verify(areaConhecimentoRepository).save(areaConhecimento);
        verify(areaConhecimentoMapper).toDTO(areaConhecimento);
        verifyNoMoreInteractions(areaConhecimentoRepository, areaConhecimentoMapper);
    }

    @Test
    @DisplayName("Deve lançar exceção ao tentar editar área de conhecimento inexistente")
    void deveLancarExcecaoAoTentarEditarAreaConhecimentoInexistente() {
        UUID id = geraIdValido();
        AreaConhecimentoRequestDTO requisicao = geraRequestEdicaoValido();

        when(areaConhecimentoRepository.findById(id)).thenReturn(Optional.empty());

        RecursoNaoEncontradoException excecao = assertThrows(
                RecursoNaoEncontradoException.class,
                () -> areaConhecimentoService.editar(id, requisicao)
        );

        assertEquals("Não foi possível encontrar a Área de conhecimento", excecao.getMessage());

        verify(areaConhecimentoRepository).findById(id);
        verifyNoInteractions(areaConhecimentoMapper);
        verifyNoMoreInteractions(areaConhecimentoRepository);
    }

    @Test
    @DisplayName("Deve falhar ao editar quando já existir outra área ativa com o mesmo nome")
    void deveFalharAoEditarQuandoJaExistirOutraAreaAtivaComMesmoNome() {
        UUID id = geraIdValido();
        AreaConhecimento areaConhecimento = geraAreaConhecimentoValido(id, geraDataValida(), null);
        AreaConhecimentoRequestDTO requisicao = geraRequestEdicaoValido();

        when(areaConhecimentoRepository.findById(id)).thenReturn(Optional.of(areaConhecimento));
        when(areaConhecimentoRepository.existsByNomeIgnoreCaseAndAtivoTrue(NOVO_NOME)).thenReturn(false);
        when(areaConhecimentoRepository.existsByNomeIgnoreCaseAndAtivoTrueAndIdNot(NOVO_NOME, id)).thenReturn(true);

        NomeAreaConhecimentoExistenteException excecao = assertThrows(
                NomeAreaConhecimentoExistenteException.class,
                () -> areaConhecimentoService.editar(id, requisicao)
        );

        assertEquals("Já existe uma área de conhecimento com este mesmo nome, utilize outro.", excecao.getMessage());

        verify(areaConhecimentoRepository).findById(id);
        verify(areaConhecimentoRepository).existsByNomeIgnoreCaseAndAtivoTrue(NOVO_NOME);
        verify(areaConhecimentoRepository).existsByNomeIgnoreCaseAndAtivoTrueAndIdNot(NOVO_NOME, id);
        verifyNoInteractions(areaConhecimentoMapper);
        verifyNoMoreInteractions(areaConhecimentoRepository);
    }

    @Test
    @DisplayName("Deve validar nome ao editar ignorando o próprio id")
    void deveValidarNomeAoEditarIgnorandoOProprioId() {
        AreaConhecimentoRequestDTO requisicao = geraRequestEdicaoValido();
        UUID id = geraIdValido();

        when(areaConhecimentoRepository.existsByNomeIgnoreCaseAndAtivoTrue(NOVO_NOME)).thenReturn(false);
        when(areaConhecimentoRepository.existsByNomeIgnoreCaseAndAtivoTrueAndIdNot(NOVO_NOME, id)).thenReturn(false);

        areaConhecimentoService.validaNomeEmAreasDeConhecimentoExistentes(requisicao, id);

        verify(areaConhecimentoRepository).existsByNomeIgnoreCaseAndAtivoTrue(NOVO_NOME);
        verify(areaConhecimentoRepository).existsByNomeIgnoreCaseAndAtivoTrueAndIdNot(NOVO_NOME, id);
        verifyNoMoreInteractions(areaConhecimentoRepository, areaConhecimentoMapper);
    }
}
