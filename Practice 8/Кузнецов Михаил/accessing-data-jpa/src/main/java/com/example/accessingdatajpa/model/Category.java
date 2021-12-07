package com.example.accessingdatajpa.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table
public class Category {
    @Id
    @Column
    private String name;
    @Column
    private String parentCat;
}
