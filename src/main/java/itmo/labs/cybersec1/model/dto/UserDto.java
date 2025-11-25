package itmo.labs.cybersec1.model.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserDto {
    private Long id;
    private String name;
    private String surname;
    private String login;
}
