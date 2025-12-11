/*
package ru.rsreu.lab.controller.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.rsreu.lab.entity.User;
import ru.rsreu.lab.service.rest.UserService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    public UserController(ModelMapper modelMapper, UserService userService) {
        super(modelMapper);
        this.userService = userService;
    }

    @GetMapping("/getById/{id}")
    public ResponseEntity<ApiResponse<AppUserResponseDTO>> getUser(@PathVariable long id) {
        AppUser user = userService.getById(id);
        AppUserResponseDTO appUserResponseDTO = serialize(user, APP_USER_DTO_CLASS);
        return ResponseEntity.ok(new ApiResponse<>(appUserResponseDTO));
    }


}
*/
