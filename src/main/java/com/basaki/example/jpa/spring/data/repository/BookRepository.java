package com.basaki.example.jpa.spring.data.repository;

import com.basaki.example.jpa.spring.data.model.Book;
import com.basaki.example.jpa.spring.data.model.BookWithNoGenre;
import com.basaki.example.jpa.spring.data.model.Genre;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

/**
 * {@code BookRepository} exposes all CRUD operations on a data of type
 * {@code Book} and {@code BookWithNoGenre} projection.
 * <p/>
 *
 * @author Indra Basak
 * @since 9/22/17
 */
public interface BookRepository extends CrudRepository<Book, UUID> {

    List<Book> findByTitleIgnoreCase(String title);

    List<Book> findByTitleIgnoreCaseAndAuthorIgnoreCase(String title,
            String author);

    List<Book> findByTitleIgnoreCaseAndAuthorIgnoreCaseAndGenre(
            String title,
            String author,
            Genre genre);

    List<Book> findByTitleIgnoreCaseAndGenre(String title, Genre genre);

    List<Book> findByAuthorIgnoreCase(String author);

    List<Book> findByAuthorIgnoreCaseAndGenre(String author, Genre genre);

    List<Book> findByGenre(Genre genre);

    BookWithNoGenre findProjectedById(UUID id);

    @Query(value = "SELECT b.title as title, b.author as author FROM Book AS b")
    List<BookWithNoGenre> findProjectedAll();

    @Query(value = "SELECT b.title as title, b.author as author FROM Books AS b", nativeQuery = true)
    List<BookWithNoGenre> findProjectedNativeAll();
}
