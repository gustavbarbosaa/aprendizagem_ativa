package gustavbarbosaa.backend.controllers;

import gustavbarbosaa.backend.dtos.requests.AreaConhecimentoRequestDTO;
import gustavbarbosaa.backend.dtos.responses.AreaConhecimentoResponseDTO;
import gustavbarbosaa.backend.dtos.responses.MinAreaConhecimentoResponseDTO;
import gustavbarbosaa.backend.services.AreaConhecimentoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api/v1/areas")
@RequiredArgsConstructor
public class AreaConhecimentoController {
    private final AreaConhecimentoService areaConhecimentoService;

    @GetMapping
    public ResponseEntity<List<AreaConhecimentoResponseDTO>> listarTodos() {
        return ResponseEntity.ok().body(areaConhecimentoService.listarTodos());
    }

    @GetMapping("/ativos")
    public ResponseEntity<List<AreaConhecimentoResponseDTO>> listarAtivos() {
        return ResponseEntity.ok().body(areaConhecimentoService.listarAtivos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<AreaConhecimentoResponseDTO> buscarPorId(@PathVariable UUID id) {
        return ResponseEntity.ok().body(areaConhecimentoService.buscarPorId(id));
    }

    @PostMapping
    public ResponseEntity<MinAreaConhecimentoResponseDTO> cadastrar(@RequestBody AreaConhecimentoRequestDTO request) {
        return ResponseEntity.ok().body(areaConhecimentoService.criar(request));
    }

    @PatchMapping("/{id}/editar")
    public ResponseEntity<AreaConhecimentoResponseDTO> editar(@PathVariable UUID id, @RequestBody AreaConhecimentoRequestDTO request) {
        return ResponseEntity.ok().body(areaConhecimentoService.editar(id, request));
    }

    @DeleteMapping("/{id}/remover")
    public ResponseEntity<Void> remover(@PathVariable UUID id) {
        areaConhecimentoService.remover(id);
        return ResponseEntity.noContent().build();
    }
}
