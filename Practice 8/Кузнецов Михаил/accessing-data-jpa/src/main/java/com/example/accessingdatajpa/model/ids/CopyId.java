package com.example.accessingdatajpa.model.ids;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Objects;

public class CopyId implements Serializable {
    private Long isbn;
    private int copyNumber;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CopyId copyId = (CopyId) o;
        return copyNumber == copyId.copyNumber &&
                isbn.equals(copyId.isbn);
    }

    @Override
    public int hashCode() {
        return Objects.hash(isbn, copyNumber);
    }
}
