package com.gabriel.photoappusers.service;

import com.gabriel.photoappusers.data.UserEntity;
import com.gabriel.photoappusers.repository.UserRepository;
import com.gabriel.photoappusers.shared.UserDto;
import com.gabriel.photoappusers.ui.model.AlbumResponseModel;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    //private final RestTemplate restTemplate;
    private final AlbumsServiceFeignClient albumsServiceFeignClient;
    private final Environment env;

    @Override
    public UserDto createUser(UserDto userDto) {
        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        userDto.setUserId(UUID.randomUUID().toString());
        userDto.setEncryptedPassword(passwordEncoder.encode(userDto.getPassword()));
        UserEntity userEntity = mapper.map(userDto, UserEntity.class);

        return mapper.map(userRepository.save(userEntity), UserDto.class);
    }

    @Override
    public UserDto getUserByEmail(String email) throws UsernameNotFoundException{
        UserEntity userEntity = Optional.ofNullable(userRepository.findByEmail(email))
                .orElseThrow(()->new UsernameNotFoundException(email));

        return new ModelMapper().map(userEntity, UserDto.class);
    }

    @Override
    public UserDto getUserByUserId(String userId) {
        return new ModelMapper().map(userRepository.findByUserId(userId),UserDto.class);
    }

    @Override
    public ResponseEntity<List<AlbumResponseModel>> getUserAlbums(String userId) throws FeignException {

//        String albumsUrl = String.format(env.getProperty("albums.url"),userId);
//
//        ResponseEntity<List<AlbumResponseModel>> exchange = restTemplate.exchange(albumsUrl, HttpMethod.GET, null,
//                new ParameterizedTypeReference<List<AlbumResponseModel>>() {
//                });
//        return exchange;

        try {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(albumsServiceFeignClient.getAlbums(userId));
        }catch(FeignException e){
            throw e;
        }
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        UserEntity userEntity = Optional.ofNullable(userRepository.findByEmail(s))
                .orElseThrow(()-> new UsernameNotFoundException(s));
        return new User(userEntity.getEmail(), userEntity.getEncryptedPassword(), true,true,true,true, new ArrayList<>());
    }
}
