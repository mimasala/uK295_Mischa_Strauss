package ch.noseryoung.sbdemo01;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
@RestController
public class HelloController {
    @GetMapping("/api/v1/")
    public String index(){
        return "this is my API";
    }
}
