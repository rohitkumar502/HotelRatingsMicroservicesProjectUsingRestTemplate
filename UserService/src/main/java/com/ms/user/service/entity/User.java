package com.ms.user.service.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "micro_users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

    @Id
    @Column(name = "ID")
    private String userId;

    @Column(name = "NAME", length = 20)
    private String name;

    @Column(name = "EMAIL")
    private String email;

    @Column(name = "ABOUT")
    private String about;


    @Transient // To ignore this field by JPA to avoid storing in DB. This field will not store db table
    private List<Rating> ratings = new ArrayList<>();



}
