package com.example.accessingdatajpa.model;

import com.example.accessingdatajpa.model.ids.CopyId;

import javax.persistence.*;

@Entity
@IdClass(CopyId.class)
@Table
public class Copy {
    @Id
    @Column
    private Long isbn;
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column
    private int copyNumber;
    @Column
    private int shelfPosition;

    public Long getIsbn() {
        return isbn;
    }

    public int getCopyNumber() {
        return copyNumber;
    }

    public int getShelfPosition() {
        return shelfPosition;
    }

    public static Copy from(Long isbn, int copyNumber) {
        Copy copy = new Copy();
        copy.isbn = isbn;
        copy.copyNumber = copyNumber;
        return copy;
    }
}
