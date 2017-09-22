package com.basaki.example.jpa.spring.service;

import com.basaki.example.jpa.spring.data.model.Book;
import com.basaki.example.jpa.spring.data.model.BookRequest;
import com.basaki.example.jpa.spring.data.model.BookWithNoGenre;
import com.basaki.example.jpa.spring.data.model.Genre;
import com.basaki.example.jpa.spring.data.repository.BookRepository;
import com.basaki.example.jpa.spring.error.DataNotFoundException;
import com.basaki.example.jpa.spring.error.InvalidSearchException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

/**
 * {@code BookService} service provides data access service for {@code Book}.
 * <p/>
 *
 * @author Indra Basak
 * @sice 9/22/17
 */
@Service
@Slf4j
public class BookService {

    @Autowired
    private BookRepository repo;

    private Mapper mapper;

    @Autowired
    public BookService(BookRepository repo, Mapper mapper) {
        this.repo = repo;
        this.mapper = mapper;
    }

    public Book create(BookRequest request) {
        validate(request);

        Book entity = mapper.map(request, Book.class);

        entity = repo.save(entity);
        if (entity == null) {
            throw new InvalidSearchException("Failed to create book!");
        }

        return entity;
    }

    public Book read(UUID id) {
        Book entity = repo.findOne(id);

        if (entity == null) {
            throw new InvalidSearchException("Book not found!");
        }

        return entity;
    }

    public BookWithNoGenre readProjection(UUID id) {
        BookWithNoGenre entity = repo.findProjectedById(id);

        if (entity == null) {
            throw new InvalidSearchException("Book not found!");
        }

        return entity;
    }

    public List<Book> read(String title, String author, Genre genre) {
        List<Book> entities = null;

        if (title != null && author == null && genre == null) {
            entities = repo.findByTitleIgnoreCase(title);
        } else if (title != null && author != null && genre == null) {
            entities = repo.findByTitleIgnoreCaseAndAuthorIgnoreCase(title,
                    author);
        } else if (title != null && author != null && genre != null) {
            entities =
                    repo.findByTitleIgnoreCaseAndAuthorIgnoreCaseAndGenre(title,
                            author, genre);
        } else if (title == null && author != null && genre == null) {
            entities = repo.findByAuthorIgnoreCase(author);
        } else if (title == null && author != null && genre != null) {
            entities = repo.findByAuthorIgnoreCaseAndGenre(author, genre);
        } else if (title != null && author == null && genre != null) {
            entities = repo.findByTitleIgnoreCaseAndGenre(title, genre);
        } else if (title == null && author == null && genre != null) {
            entities = repo.findByGenre(genre);
        } else {
            Iterable<Book> iter = repo.findAll();

            List<Book> ent = new ArrayList<>();
            iter.forEach(e -> ent.add(e));
            entities = ent;
        }

        if (entities == null || entities.isEmpty()) {
            throw new DataNotFoundException(
                    "No books found with the search criteria!");
        }

        return entities;
    }

    public List<BookWithNoGenre> readProjectionAll() {
        List<BookWithNoGenre> entities = repo.findProjectedAll();

        if (entities == null || entities.isEmpty()) {
            throw new DataNotFoundException(
                    "No books found with the search criteria!");
        }

        return entities;
    }

    public List<BookWithNoGenre> readProjectionNativeAll() {
        List<BookWithNoGenre> entities = repo.findProjectedNativeAll();

        if (entities == null || entities.isEmpty()) {
            throw new DataNotFoundException(
                    "No books found with the search criteria!");
        }

        return entities;
    }

    @Transactional
    public Book update(UUID id, BookRequest request) {
        Book entity = repo.findOne(id);

        if (entity == null) {
            throw new DataNotFoundException(
                    "Book with id " + id + " not found!");
        }

        validate(request);
        mapper.map(request, entity);
        entity = repo.save(entity);

        if (entity == null) {
            throw new InvalidSearchException("Failed to create book!");
        }

        return entity;
    }

    public void delete(UUID id) {
        repo.delete(id);
    }

    public void deleteAll() {
        repo.deleteAll();
    }


    private void validate(BookRequest request) {
        Assert.notNull(request.getTitle(), "Title can't be null!");
        Assert.notNull(request.getAuthor(), "Author can't be null!");
        Assert.notNull(request.getGenre(), "Genre can't be null!");
    }
}
