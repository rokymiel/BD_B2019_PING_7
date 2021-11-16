package com.example.accessingdatajpa;

import com.example.accessingdatajpa.model.Book;
import com.example.accessingdatajpa.model.Copy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CopyService {
    @Autowired
    private CopyRepository copyRepository;

    public void create(Copy book) {
        copyRepository.save(book);
    }

    public Copy read(Long isbn, int number) {
        Example<Copy> example = Example.of(Copy.from(isbn, number));
        Optional<Copy> actual = copyRepository.findOne(example);
        return actual.orElse(null);
    }

    public boolean update(Copy copy) {
        Example<Copy> example = Example.of(Copy.from(copy.getIsbn(), copy.getCopyNumber()));
        Optional<Copy> actual = copyRepository.findOne(example);

        if (actual.isPresent()) {
            copyRepository.save(copy);
            return true;
        }
        return false;
    }

    public boolean delete(Long isbn, int number) {
        Example<Copy> example = Example.of(Copy.from(isbn, number));
        Optional<Copy> actual = copyRepository.findOne(example);

        if (actual.isPresent()) {
            copyRepository.delete(actual.get());
            return true;
        }
        return false;
    }
}

