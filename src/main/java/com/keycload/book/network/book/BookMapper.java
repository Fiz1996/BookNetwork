package com.keycload.book.network.book;

import com.keycload.book.network.history.BookTransactionHistory;
import org.springframework.stereotype.Service;

@Service
public class BookMapper {

    public Book toBook(BookRequest bookRequest) {
        return Book.builder()
                .id(bookRequest.id())
                .shareable(bookRequest.shareable())
                .isbn(bookRequest.isbn())
                .author(bookRequest.authorName())
                .title(bookRequest.title())
                .synopsis(bookRequest.synopsis())
                .build();
    }

    public BookResponse toBookResponse(Book book) {
        return BookResponse.builder()
                .id(book.getId())
                .title(book.getTitle())
                .authorName(book.getIsbn())
                .synopsis(book.getSynopsis())
                .rate(book.getRate())
                .owner(book.getOwner().fullName())
                .build();
    }

    public BorrowedBookResponse toBorrowedBookResponse(BookTransactionHistory dto) {
        return BorrowedBookResponse.builder()
                .id(dto.getId())
                .title(dto.getBook().getTitle())
                .authorName(dto.getBook().getAuthor())
                .isbn(dto.getBook().getIsbn())
                .rate(dto.getBook().getRate())
                .returned(dto.isReturned())
                .returnApproved(dto.isReturnApproved())
                .build();
    }
}
