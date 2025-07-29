package leagueOfJava.moba.handler;

import leagueOfJava.moba.exception.DadosInvalidosException;
import leagueOfJava.moba.exception.NomeJaExistenteException;
import leagueOfJava.moba.exception.ResultadoNaoEncontradoException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestControllerAdvice
public class CustomExceptionHandler {

    private UUID getTransacaoID(){
        return UUID.randomUUID();
    }

    @ExceptionHandler(ResultadoNaoEncontradoException.class)
    public ResponseEntity<Object> handleResultadoNaoEncontradoException(ResultadoNaoEncontradoException ex) {
        Map<String, Object> body = new HashMap<>();
        body.put("uuid", getTransacaoID());
        body.put("timestamp", LocalDateTime.now());
        body.put("status", HttpStatus.NOT_FOUND.value());
        body.put("mensagem", ex.getMessage());

        return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
    }


    @ExceptionHandler(NomeJaExistenteException.class)
    public ResponseEntity<Object> handleNomeJaExistenteException(NomeJaExistenteException ex) {
        Map<String, Object> body = new HashMap<>();
        body.put("uuid", getTransacaoID());
        body.put("timestamp", LocalDateTime.now());
        body.put("status", HttpStatus.NOT_FOUND.value());
        body.put("mensagem", ex.getMessage());

        return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(DadosInvalidosException.class)
    public ResponseEntity<Object> handleDadpsInvalidosExcewption(DadosInvalidosException ex) {
        Map<String, Object> body = new HashMap<>();
        body.put("uuid", getTransacaoID());
        body.put("timestamp", LocalDateTime.now());
        body.put("status", HttpStatus.NOT_FOUND.value());
        body.put("mensagem", ex.getMessage());

        return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
    }
}
