package hello.itemservice.session;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@Slf4j
@RestController
public class SessionInfoController {

    @GetMapping("/session-info")
    public String sessionInfo(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session==null) {
            return "세션이 없습니다";
        }

        session.getAttributeNames().asIterator()
                .forEachRemaining(name -> log.info("session name={}, value={}", name, session.getAttribute(name)));

        log.info("sessionId={}", session.getId());
        log.info("sessionId 유효시간={}", session.getMaxInactiveInterval());
        log.info("sessionId 생성일시={}", new Date(session.getCreationTime()));
        log.info("sessionId 최근에 서버에 접근한 시간={}", new Date(session.getLastAccessedTime()));
        log.info("sessionId 새로 생성된 세션인지 아닌지={}", session.isNew());

        return "세션 출력";
    }
}
