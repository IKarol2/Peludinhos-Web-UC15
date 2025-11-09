package br.com.peludinhos.web.validation;
import org.springframework.http.*;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.http.converter.HttpMessageNotReadableException;
import jakarta.validation.ConstraintViolationException;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Tratamento global de erros com mensagens em PT-BR.
 *
 * <p>Projeto: PeludinhosMours-Web-UC15</p>
 * <p>Autor: Karol</p>
 * <p>Data: 2025-10-30</p>
 * <p>Versão: 1.0</p>
 */

@RestControllerAdvice
public class GlobalExceptionHandler {

  // Bean Validation em @RequestBody (ex.: @Valid DTO)
  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<Map<String, Object>> handleValidation(MethodArgumentNotValidException ex) {
    Map<String, Object> body = new LinkedHashMap<>();
    body.put("mensagem", "Verifique os dados informados.");

    Map<String, String> erros = new LinkedHashMap<>();
    ex.getBindingResult().getFieldErrors()
      .forEach(fe -> erros.put(fe.getField(), fe.getDefaultMessage()));
    body.put("erros", erros);

    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
  }

  // Bean Validation em @RequestParam / @PathVariable
  @ExceptionHandler(ConstraintViolationException.class)
  public ResponseEntity<Map<String, Object>> handleConstraint(ConstraintViolationException ex) {
    Map<String, Object> body = new LinkedHashMap<>();
    body.put("mensagem", "Dados inválidos. Revise os campos.");
    body.put("detalhe", ex.getMessage());
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
  }

  // Erros de conversão de tipos (ex.: número onde vem texto)
  @ExceptionHandler(MethodArgumentTypeMismatchException.class)
  public ResponseEntity<Map<String, Object>> handleTypeMismatch(MethodArgumentTypeMismatchException ex) {
    Map<String, Object> body = new LinkedHashMap<>();
    body.put("mensagem", "Tipo de dado inválido para o campo: " + ex.getName());
    body.put("detalhe", ex.getMessage());
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
  }

  // JSON malformado / corpo vazio
  @ExceptionHandler(HttpMessageNotReadableException.class)
  public ResponseEntity<Map<String, Object>> handleNotReadable(HttpMessageNotReadableException ex) {
    Map<String, Object> body = new LinkedHashMap<>();
    body.put("mensagem", "Formato de requisição inválido. Envie um JSON válido.");
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
  }

  // Regras manuais (quando você lança IllegalArgumentException)
  @ExceptionHandler(IllegalArgumentException.class)
  public ResponseEntity<Map<String, Object>> handleIllegalArg(IllegalArgumentException ex) {
    Map<String, Object> body = new LinkedHashMap<>();
    body.put("mensagem", ex.getMessage());
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
  }
}
