package com.krmplov.storage.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "client_changes")
public class ClientChanges {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "client_id")
    private String clientId;

    private String message;
}
