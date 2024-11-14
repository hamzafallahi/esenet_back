package com.example.demo.entity;

import lombok.*;
import jakarta.persistence.*;

import jakarta.persistence.*;

@Entity
@Table(name = "members") // Ensure the table name matches exactly
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Members {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String firstname;
    private String lastname;
    private String email;
    private int number;
}
