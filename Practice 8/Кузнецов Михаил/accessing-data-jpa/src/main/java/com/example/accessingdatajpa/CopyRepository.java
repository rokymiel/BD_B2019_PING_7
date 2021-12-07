package com.example.accessingdatajpa;

import com.example.accessingdatajpa.model.Book;
import com.example.accessingdatajpa.model.Copy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CopyRepository extends JpaRepository<Copy, Integer> {

}