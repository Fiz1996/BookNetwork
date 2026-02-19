package com.keycload.book.network.book;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record BookRequest (
        Integer id,
        @NotNull(message = "Title cannot be empty")
        @NotEmpty(message = "Not empty")
        String title,

        String authorName,
        String isbn,
        String synopsis,
        boolean shareable
) {


}
