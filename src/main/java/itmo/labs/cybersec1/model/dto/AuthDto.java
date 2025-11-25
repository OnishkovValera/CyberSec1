package itmo.labs.cybersec1.model.dto;

import lombok.Data;

@Data
public class AuthDto {
    private String login;
    private String password;
}
