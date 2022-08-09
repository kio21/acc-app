package com.example.controller;

import com.example.dto.UserDto;
import com.example.request.UserDataRequest;
import com.example.usecase.UserUseCase;
import com.example.validation.DeleteValidation;
import com.example.validation.PostValidation;
import com.example.validation.PutValidation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Pattern;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import static com.example.utils.StringUtils.*;

@Validated
@RestController
@RequestMapping("/users")
public class UserController extends PrivateController {
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    private final UserUseCase userUseCase;

    public UserController(UserUseCase userUseCase) {
        this.userUseCase = userUseCase;
    }

    @GetMapping(produces = {"application/json"})
    public ResponseEntity<List<UserDto>> getUsers(
        @RequestParam Map<String, String> params,

        @RequestParam(name = "date_of_birth", required = false)
        @DateTimeFormat(pattern = DATE_FORMAT)
        @Valid LocalDate dateOfBirth,

        @RequestParam(name = "phone", required = false)
        @Pattern(regexp = PHONE_REGEX)
        @Valid String phone,

        @RequestParam(name = "email", required = false)
        @Pattern(regexp = EMAIL_REGEX)
        @Valid String email,

        @RequestParam(name = "name", required = false)
        String name
    ) {
        return auth(user -> userUseCase.getUsers(params));
    }

    @PostMapping(consumes = {"application/json"}, produces = {"application/json"})
    public ResponseEntity<UserDto> addUserData(
        @RequestBody @Validated(PostValidation.class) UserDataRequest userDataRequest) {
        return auth(user -> userUseCase.addUserData(user, userDataRequest.data()));
    }

    @PutMapping(consumes = {"application/json"}, produces = {"application/json"})
    public ResponseEntity<UserDto> updateUserData(
        @RequestBody @Validated(PutValidation.class) UserDataRequest userDataRequest) {
        return auth(user -> userUseCase.updateUserData(user, userDataRequest.data()));
    }

    @DeleteMapping(consumes = {"application/json"}, produces = {"application/json"})
    public ResponseEntity<UserDto> deleteUserData(
        @RequestBody @Validated(DeleteValidation.class) UserDataRequest userDataRequest) {
        return auth(user -> userUseCase.deleteUserData(user, userDataRequest.data()));
    }
}
