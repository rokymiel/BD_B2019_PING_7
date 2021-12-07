package com.example.accessingdatajpa.model.ids;

import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

public class BorrowingId implements Serializable {

    private Long readerNr;
    private Long isbn;
    private int copyNumber;
    private Date returnDate;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BorrowingId that = (BorrowingId) o;
        return copyNumber == that.copyNumber &&
                readerNr.equals(that.readerNr) &&
                isbn.equals(that.isbn) &&
                returnDate.equals(that.returnDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(readerNr, isbn, copyNumber, returnDate);
    }
}
