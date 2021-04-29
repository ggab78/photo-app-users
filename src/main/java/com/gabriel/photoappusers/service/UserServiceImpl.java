package com.gabriel.photoappusers.service;

import com.gabriel.photoappusers.data.UserEntity;
import com.gabriel.photoappusers.repository.UserRepository;
import com.gabriel.photoappusers.shared.UserDto;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

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
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        UserEntity userEntity = Optional.ofNullable(userRepository.findByEmail(s))
                .orElseThrow(()-> new UsernameNotFoundException(s));
        return new User(userEntity.getEmail(), userEntity.getEncryptedPassword(), true,true,true,true, new ArrayList<>());
    }
}
