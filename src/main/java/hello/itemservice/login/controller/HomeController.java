package hello.itemservice.login.controller;

import hello.itemservice.domain.Member;
import hello.itemservice.login.SessionConst;
import hello.itemservice.login.repository.MemberRepository;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequiredArgsConstructor
@Slf4j
public class HomeController {

    private final MemberRepository memberRepository;

  //  @GetMapping("/")
    public String home() {
        log.info("홈화면");
        return "home";
    }

    //@GetMapping("/")
    public String homeLogin(@CookieValue(name="memberId", required = false)Long memberId, Model model) {

        if (memberId == null) {
            return "home";
        }

        Optional<Member> loginMember = memberRepository.findById(memberId);
        if (loginMember.get() == null) {
            return "home";
        }

        model.addAttribute("member", loginMember.get());
        return "loginHome";
    }

    //@GetMapping("/")
    public String homeLoginV2(HttpServletRequest request, Model model) {

        HttpSession session = request.getSession(false);
        if (session == null) {
            return "home";
        }

        Member loginMember = (Member)session.getAttribute(SessionConst.LOGIN_MEMBER);

        //세션 x
        if (loginMember == null) {
            return "home";
        }

        //세션 유지
        model.addAttribute("member", loginMember);
        return "loginHome";
    }

    @GetMapping("/")
    public String homeLoginV3Spring(
            @SessionAttribute(name = SessionConst.LOGIN_MEMBER,required = false) Member loginMember, Model model) {

        //세션 x
        if (loginMember == null) {
            return "home";
        }

        //세션 유지
        model.addAttribute("member", loginMember);
        return "loginHome";
    }

    //@PostMapping("/logout")
    public String logout(HttpServletResponse response, String cookieName) {
        Cookie cookie = new Cookie(cookieName, null);
        cookie.setMaxAge(0);
        response.addCookie(cookie);
        return "redirect:/";
    }

    @PostMapping("/logout")
    public String logoutV2(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        return "redirect:/";
    }

}
