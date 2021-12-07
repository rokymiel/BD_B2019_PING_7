package com.example.accessingdatajpa.model;

import com.example.accessingdatajpa.model.ids.BorrowingId;

import javax.persistence.*;
import java.util.Date;

@Entity
@IdClass(BorrowingId.class)
@Table
public class Borrowing {

    @Id
    @Column
    private Long readerNr;
    @Id
    @Column
    private Long isbn;
    @Id
    @Column
    private int copyNumber;
    @Id
    @Column
    private Date returnDate;

}
