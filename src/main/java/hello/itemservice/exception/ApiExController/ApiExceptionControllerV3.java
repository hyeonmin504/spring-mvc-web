package hello.itemservice.exception.ApiExController;

import hello.itemservice.domain.dto.MemberDto;
import hello.itemservice.exception.UserException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
public class ApiExceptionControllerV3 {

    @GetMapping("/api/v3/members/{id}")
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

    @PostMapping("/api/v3/members")
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
