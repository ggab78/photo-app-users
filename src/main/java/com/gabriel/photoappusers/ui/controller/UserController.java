package com.gabriel.photoappusers.ui.controller;

import com.gabriel.photoappusers.service.UserService;
import com.gabriel.photoappusers.shared.UserDto;
import com.gabriel.photoappusers.ui.model.CreateUserRequestModel;
import com.gabriel.photoappusers.ui.model.CreateUserResponseModel;
import com.gabriel.photoappusers.ui.model.UserResponseModel;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping("users")
@RequiredArgsConstructor
public class UserController {


    private final UserService userService;

    @Autowired
    private Environment env;

    @GetMapping("/status/check")
    public String status() {
        return "Working on port " + env.getProperty("local.server.port")
                + " with token " + env.getProperty("token.secret");
    }

    @GetMapping(path = "/{userId}/albums", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserResponseModel> getUser(@PathVariable("userId") String userId) {

        UserDto userDto = userService.getUserByUserId(userId);
        UserResponseModel returnValue = new ModelMapper().map(userDto, UserResponseModel.class);


        log.info("before calling albums");
//        try {
            returnValue.setAlbums(userService.getUserAlbums(userId).getBody());
//        }catch (FeignException e){
//            log.error(e.getMessage());
//        }
        log.info("after calling albums");


        return ResponseEntity.status(HttpStatus.OK).body(returnValue);

    }


    @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
            produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<CreateUserResponseModel> createUser(@Valid @RequestBody CreateUserRequestModel userRequest) {

        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        UserDto userDto = mapper.map(userRequest, UserDto.class);

        userDto = userService.createUser(userDto);

        return new ResponseEntity(mapper.map(userDto, CreateUserResponseModel.class), HttpStatus.CREATED);
    }


}
