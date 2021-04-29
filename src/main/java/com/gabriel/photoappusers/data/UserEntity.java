package com.gabriel.photoappusers.data;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Getter
@Setter
@Entity
@Table(name = "Users")
public class UserEntity implements Serializable {

    private final static long serialVersionUID= -6347775377986127837L;

    @Id
    @GeneratedValue
    private long id;

    @Column(nullable = false, length = 50)
    private String firstName;

    @Column(nullable = false, length = 50)
    private String lastName;

    @Column(nullable = false, length = 128, unique = true)
    private String email;

    @Column(nullable = false, unique = true)
    private String userId;

    @Column(nullable = false, unique = true)
    private String encryptedPassword;
}
