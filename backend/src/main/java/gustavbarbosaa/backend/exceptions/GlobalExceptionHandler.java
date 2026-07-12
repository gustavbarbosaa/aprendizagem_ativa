package gustavbarbosaa.backend.exceptions;

import gustavbarbosaa.backend.exceptions.records.MensagemErro;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.List;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(RecursoNaoEncontradoException.class)
    public ResponseEntity<MensagemErro> hadleRecursoNaoEncontradoException(RecursoNaoEncontradoException e) {
        MensagemErro response = new MensagemErro(
                HttpStatus.NOT_FOUND,
                e.getMessage(),
                List.of(e.getMessage())
        );

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    @ExceptionHandler(NomeAreaConhecimentoExistenteException.class)
    public ResponseEntity<MensagemErro> hadleNomeAreaConhecimentoExistenteException(NomeAreaConhecimentoExistenteException e) {
        MensagemErro response = new MensagemErro(
                HttpStatus.UNPROCESSABLE_CONTENT,
                e.getMessage(),
                List.of(e.getMessage())
        );

        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_CONTENT).body(response);
    }

    @ExceptionHandler(RegraNegocioException.class)
    public ResponseEntity<MensagemErro> hadleRegraNegocioException(RegraNegocioException e) {
        MensagemErro response = new MensagemErro(
                HttpStatus.UNPROCESSABLE_CONTENT,
                e.getMessage(),
                List.of(e.getMessage())
        );

        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_CONTENT).body(response);
    }
}
