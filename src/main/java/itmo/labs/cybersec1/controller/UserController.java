package itmo.labs.cybersec1.controller;

import itmo.labs.cybersec1.model.dto.AuthDto;
import itmo.labs.cybersec1.model.dto.UserDto;
import itmo.labs.cybersec1.model.entity.User;
import itmo.labs.cybersec1.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("/auth/login")
    public ResponseEntity<Map<String, String>> login(@RequestBody AuthDto authDto){
        return ResponseEntity.ok(userService.login(authDto));
    }

    @GetMapping("/api/data")
    public ResponseEntity<List<UserDto>> getData(){
        return ResponseEntity.ok(userService.getData());
    }

    @GetMapping("/api/me")
    public ResponseEntity<UserDto> me(){
        return ResponseEntity.ok(userService.me());
    }
}
