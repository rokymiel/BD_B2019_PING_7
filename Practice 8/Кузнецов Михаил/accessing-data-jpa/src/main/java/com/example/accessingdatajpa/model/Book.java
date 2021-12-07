package com.example.accessingdatajpa.model;

import javax.persistence.*;

@Entity
@Table
public class Book {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column
    private Long isbn;
    @Column
    private String title;
    @Column
    private String author;
    @Column
    private int pagesNum;
    @Column
    private int pubYear;
    @Column
    private String pubName;

    protected  Book() {}

    public Book(String title, String author, int pagesNum, int pubYear, String pubName) {
        this.title = title;
        this.author = author;
        this.pagesNum = pagesNum;
        this.pubYear = pubYear;
        this.pubName = pubName;
    }

    public Long getIsbn() {
        return isbn;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public int getPagesNum() {
        return pagesNum;
    }

    public int getPubYear() {
        return pubYear;
    }

    public String getPubName() {
        return pubName;
    }

    public static Book from(Long isbn) {
        Book book = new Book();
        book.isbn = isbn;
        return book;
    }
}
