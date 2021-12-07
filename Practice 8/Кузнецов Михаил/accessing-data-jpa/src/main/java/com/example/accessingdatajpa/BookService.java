package com.example.accessingdatajpa;

import com.example.accessingdatajpa.model.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class BookService {
    @Autowired
    private BookRepository bookRepository;

    public void create(Book book) {
        bookRepository.save(book);
    }

    public Book read(Long isbn) {
        Example<Book> example = Example.of(Book.from(isbn));
        Optional<Book> actual = bookRepository.findOne(example);
        return actual.orElse(null);
    }

    public boolean update(Book book) {
        Example<Book> example = Example.of(Book.from(book.getIsbn()));
        Optional<Book> actual = bookRepository.findOne(example);

        if (actual.isPresent()) {
            bookRepository.save(book);
            return true;
        }
        return false;
    }

    public boolean delete(Long isbn) {
        Example<Book> example = Example.of(Book.from(isbn));
        Optional<Book> actual = bookRepository.findOne(example);

        if (actual.isPresent()) {
            bookRepository.delete(actual.get());
            return true;
        }
        return false;
    }
}
