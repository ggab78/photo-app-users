package com.gabriel.photoappusers.service;

import com.gabriel.photoappusers.shared.UserDto;
import com.gabriel.photoappusers.ui.model.AlbumResponseModel;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService extends UserDetailsService {

    UserDto createUser(UserDto userDto);
    UserDto getUserByEmail(String email);

    UserDto getUserByUserId(String userId);
    ResponseEntity<List<AlbumResponseModel>> getUserAlbums(String userId);
}
