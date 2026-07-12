package gustavbarbosaa.backend.exceptions.records;

import org.springframework.http.HttpStatus;

public record MensagemErro(HttpStatus status, String mensagem) { }
