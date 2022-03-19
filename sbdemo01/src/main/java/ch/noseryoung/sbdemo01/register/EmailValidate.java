package ch.noseryoung.sbdemo01.register;

import org.springframework.stereotype.Service;

import java.util.function.Predicate;

@Service
public class EmailValidate implements Predicate<String> {
    @Override
    public boolean test(String str){
        return str.matches("^[a-zA-Z0-9_!#$%&’*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$");
    }
}
