package com.example.accessingdatajpa.model;

import com.example.accessingdatajpa.model.ids.BookCatId;

import javax.persistence.*;

@Entity
@IdClass(BookCatId.class)
@Table
public class BookCat {

    @Id
    @Column
    private Long isbn;
    @Id
    @Column
    private String categoryName;

}
