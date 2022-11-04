package br.com.carlos.multidatabase.domain;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    @GetMapping("/read/{id}")
    public ResponseEntity<UserEntity> getUserRead(@PathVariable Long id){
        return new ResponseEntity<>(service.getUser(id), OK);
    }

    @GetMapping("/write/{id}")
    public ResponseEntity<UserEntity> getUserWrite(@PathVariable Long id){
        return new ResponseEntity<>(service.getUserWrite(id), OK);
    }

    @PostMapping
    public ResponseEntity<UserEntity> insertUser(@RequestBody UserEntity user){
        UserEntity newUser = service.insertUser(user);
        return new ResponseEntity<>(newUser, CREATED);
    }
}
