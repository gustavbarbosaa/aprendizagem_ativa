package gustavbarbosaa.backend.exceptions.records;

import org.springframework.http.HttpStatus;

import java.util.List;

public record MensagemErro(HttpStatus status, String mensagem, List<String> erros) { }
