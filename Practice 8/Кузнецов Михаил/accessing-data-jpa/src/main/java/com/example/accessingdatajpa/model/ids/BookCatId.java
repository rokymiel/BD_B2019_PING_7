package com.example.accessingdatajpa.model.ids;

import javax.persistence.Id;
import java.io.Serializable;
import java.util.Objects;

public class BookCatId implements Serializable {
    private Long isbn;
    private String categoryName;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BookCatId bookCatId = (BookCatId) o;
        return isbn.equals(bookCatId.isbn) &&
                categoryName.equals(bookCatId.categoryName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(isbn, categoryName);
    }
}
