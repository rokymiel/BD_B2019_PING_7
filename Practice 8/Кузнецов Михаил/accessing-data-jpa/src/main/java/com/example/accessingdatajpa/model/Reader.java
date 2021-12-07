package com.example.accessingdatajpa.model;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table
public class Reader {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column
    private Long id;
    @Column
    private String lastName;
    @Column
    private String firstName;
    @Column
    private String address;
    @Column
    private Date birthDate;
}
