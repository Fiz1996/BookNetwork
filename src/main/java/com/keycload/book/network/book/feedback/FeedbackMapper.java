package com.keycload.book.network.book.feedback;

import com.keycload.book.network.book.Book;
import com.keycload.book.network.feedback.Feedback;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class FeedbackMapper {

    public Feedback toFeedback(FeedbackRequest request){
        return Feedback.builder()
                .note(request.note())
                .comment(request.comment())
                .book(
                        Book.builder()
                                    .id(request.bookId())
                                    .archived(false)
                                    .shareable(false)
                                    .build()
                )
                .build();
    }

    public FeedbackResponse toFeedbackResponse(Feedback feedback, Integer id) {
        return FeedbackResponse.builder()
                .note(feedback.getNote())
                .comments(feedback.getComment())
                .ownFeedback(Objects.equals(feedback.getCreatedBy(),id))
                .build() ;
    }
}
