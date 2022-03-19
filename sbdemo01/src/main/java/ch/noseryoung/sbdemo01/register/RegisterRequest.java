package ch.noseryoung.sbdemo01.register;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@AllArgsConstructor
public class RegisterRequest {
    private final String username;
    private final String lastName;
    private final String email;
    private final String password;
}
