package com.example.accessingdatajpa.model;

import javax.persistence.*;

@Entity
@Table
public class Publisher {
    @Id
    @Column
    private String name;
    @Column
    private String address;
}
