package com.example.accessingdatajpa;

import com.example.accessingdatajpa.model.Book;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/books")
public class BookController {
    private BookService bookService = new BookService();

    @GetMapping(value = "{isbn}")
    public @ResponseBody
    Book getBook(@PathVariable Long isbn) {
        return bookService.read(isbn);
    }

    @PostMapping
    public void postBook(@RequestBody Book book) {
        bookService.create(book);
    }

    @PatchMapping
    public @ResponseBody
    boolean patchBook(@RequestBody Book book) {
        return bookService.update(book);
    }

    @DeleteMapping(value = "{isbn}")
    public @ResponseBody
    boolean deleteBook(@PathVariable Long isbn) {
        return bookService.delete(isbn);
    }

}
