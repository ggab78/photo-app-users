package com.gabriel.photoappusers.shared;

import lombok.*;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Setter
@Getter
public class UserDto implements Serializable {

    private final static long serialVersionUID = 1213529479698461544L;

    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String userId;
    private String encryptedPassword;
}
