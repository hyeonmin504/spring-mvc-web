package hello.itemservice.exception.ApiExController;

import hello.itemservice.domain.dto.MemberDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;


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

        return new MemberDto(id, "hello" + id);
    }
}
