package itmo.labs.cybersec1.service;

import itmo.labs.cybersec1.model.dto.AuthDto;
import itmo.labs.cybersec1.model.dto.UserDto;
import itmo.labs.cybersec1.model.entity.User;
import itmo.labs.cybersec1.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final SanitizationService sanitizationService;

    public Map<String, String> login(AuthDto authDto) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authDto.getLogin(), authDto.getPassword())
        );
        UserDetails principal = (UserDetails) authentication.getPrincipal();
        String token = jwtService.generateToken(principal);
        Map<String, String> resp = new HashMap<>();
        resp.put("token", token);
        return resp;
    }

    public UserDto me() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !(auth.getPrincipal() instanceof UserDetails userDetails)) {
            return null;
        }
        User user = userRepository.findByLogin(userDetails.getUsername()).orElse(null);
        return UserDto.builder()
                .id(user.getId())
                .name(sanitizationService.sanitize(user.getName()))
                .surname(sanitizationService.sanitize(user.getSurname()))
                .login(sanitizationService.sanitize(user.getLogin()))
                .build();
    }

    public List<UserDto> getData() {
        return userRepository.findAll().stream().map(user -> UserDto.builder()
                        .id(user.getId())
                        .name(sanitizationService.sanitize(user.getName()))
                        .surname(sanitizationService.sanitize(user.getSurname()))
                        .login(sanitizationService.sanitize(user.getLogin()))
                        .build())
                .toList();
    }
}
