package com.basaki.example.jpa.spring.data.model;

import lombok.Data;

/**
 * {@code BookRequest} represents a request to create or update a book model.
 * <p/>
 *
 * @author Indra Basak
 * @since 9/22/17
 */
@Data
public class BookRequest {
    private String title;
    private String author;
    private Genre genre;
}
