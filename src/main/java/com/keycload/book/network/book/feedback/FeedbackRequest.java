package com.keycload.book.network.book.feedback;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record FeedbackRequest(
        @Positive(message = "Note must be positive")
        @Min(value = 0, message = "201")
        @Min(value = 5, message = "205")
        Double note,
        @NotNull(message = "Comment cannot be empty")
        String comment,
        @NotNull(message = "BookId cannot be empty")
        Integer bookId
) {
}
