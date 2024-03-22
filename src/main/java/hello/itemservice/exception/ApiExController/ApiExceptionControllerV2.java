package hello.itemservice.exception.ApiExController;

import hello.itemservice.domain.dto.MemberDto;
import hello.itemservice.exception.ErrorResult;
import hello.itemservice.exception.UserException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
public class ApiExceptionControllerV2 {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(IllegalArgumentException.class)
    public ErrorResult illegalExHandler(IllegalArgumentException e, HttpServletRequest request){
        log.error("[exceptionHandler] ex", e);
        return new ErrorResult("BAD", e.getMessage());
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResult> userExHandler(UserException e) {
        log.error("[exceptionHandler] ex", e);
        ErrorResult errorResult = new ErrorResult("USER_EX", e.getMessage());
        return new ResponseEntity<>(errorResult, HttpStatus.BAD_REQUEST);
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler
    public ErrorResult exHandler(Exception e) {
        log.error("[exceptionHandler] ex", e);
        return new ErrorResult("EX", "내부 오류");
    }

    @GetMapping("/api/v2/members/{id}")
    public MemberDto getMember(@PathVariable("id")String id) {
        if (id.equals("ex")){
            throw new RuntimeException("잘못된 사용자");
        }
        if (id.equals("bad")){
            throw new IllegalArgumentException("잘못 입력된 값");
        }
        if (id.equals("user-ex")){
            throw new UserException("사용자 오류");
        }

        return new MemberDto(id, "hello" + id);
    }

    @PostMapping("/api/v2/members")
    public MemberDto postMember(@RequestParam("id")String id) {
        if (id.equals("ex")){
            throw new RuntimeException("잘못된 사용자");
        }
        if (id.equals("bad")){
            throw new IllegalArgumentException("잘못 입력된 값");
        }
        if (id.equals("user-ex")){
            throw new UserException("사용자 오류");
        }

        return new MemberDto(id, "hello" + id);
    }
}
