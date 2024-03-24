package hello.itemservice.converter.controller;

import hello.itemservice.converter.type.IpPort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@Controller
public class TypeConvertController {

    @GetMapping("/convert")
    @ResponseBody
    public String convert(@RequestParam("ipPort") IpPort ipPort) {
        System.out.println("ipPort.getIp() = " + ipPort.getIp());
        System.out.println("ipPort.getPort() = " + ipPort.getPort());
        return "ok";
    }

    @GetMapping("/convert/view")
    public String converterView(Model model) {
        model.addAttribute("number", 10000);
        model.addAttribute("ipPort", new IpPort("127.0.0.1", 8080));
        return "convert/converter-view";
    }

    @ResponseBody
    @GetMapping("/convert/format")
    public String format(@RequestParam Integer data) {
        System.out.println("data = "+ data);
        return "ok";
    }
}
