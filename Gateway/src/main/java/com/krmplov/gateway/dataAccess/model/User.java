package com.krmplov.gateway.dataAccess.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "users_gateway", schema = "public")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;

    @Column
    private String password;

    @Column(unique = true)
    private String username;

    @Column
    private String role;
}
