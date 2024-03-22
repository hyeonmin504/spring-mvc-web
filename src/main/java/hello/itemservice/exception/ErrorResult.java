package hello.itemservice.exception;

import ch.qos.logback.core.status.ErrorStatus;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ErrorResult {
    private String code;
    private String message;
}
