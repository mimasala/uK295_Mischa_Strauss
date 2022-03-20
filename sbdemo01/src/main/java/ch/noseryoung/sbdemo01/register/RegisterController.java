package ch.noseryoung.sbdemo01.register;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "api/v1/register")
@AllArgsConstructor
public class RegisterController {

    private final RegisterService registerService;

    @PostMapping
    public String register(@RequestBody RegisterRequest registerRequest){
        return registerService.register(registerRequest);
    }

    @GetMapping(path = "confirm")
    public String confirm(@RequestParam("token")String token){
        return registerService.confirmToken(token);
    }
}
