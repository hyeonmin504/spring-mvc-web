package hello.itemservice.exception.ApiExController;

import hello.itemservice.domain.dto.MemberDto;
import hello.itemservice.exception.BadRequestException;
import hello.itemservice.exception.ErrorResult;
import hello.itemservice.exception.UserException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;


@Slf4j
@RestController
public class ApiExceptionController {


    @GetMapping("/api/members/{id}")
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

    @GetMapping("/api/response-status-ex1")
    public String responseStatusEx1() {
        throw new BadRequestException();
    }

    @GetMapping("/api/response-status-ex2")
    public String responseStatusEx2() {
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "잘못된 요청 오류", new IllegalArgumentException());
    }

    @GetMapping("/api/default-handler-ex")
    public String defaultException(@RequestParam Integer data) {
        return "ok";
    }
}
