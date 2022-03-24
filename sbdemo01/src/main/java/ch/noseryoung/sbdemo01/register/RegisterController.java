package ch.noseryoung.sbdemo01.register;

import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "api/v1/register")
@AllArgsConstructor
public class RegisterController {

    private final RegisterService registerService;

    @PostMapping
    @Operation(summary = "registers new account")
    public String register(@RequestBody RegisterRequest registerRequest){
        return registerService.register(registerRequest);
    }

    @GetMapping(path = "confirm")
    @Operation(summary = "enables the authority of user by the provided token")
    public String confirm(@RequestParam("token")String token){
        return registerService.confirmToken(token);
    }
}
