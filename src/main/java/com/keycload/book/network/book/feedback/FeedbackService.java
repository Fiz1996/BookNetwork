package com.keycload.book.network.book.feedback;


import com.keycload.book.network.book.Book;
import com.keycload.book.network.book.BookRepository;
import com.keycload.book.network.common.PageResponse;
import com.keycload.book.network.feedback.Feedback;
import com.keycload.book.network.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class FeedbackService {

    private final BookRepository bookRepository;
    private final FeedbackMapper feedbackMapper;
    private final FeedbackRepository feedbackRepository;


    public Integer saveFeedback(
             FeedbackRequest request , Authentication connectedUser) {
        Book book = bookRepository.findById(request.bookId()).orElseThrow();
        if(book.isArchived() || !book.isShareable()) {
            throw new IllegalStateException("The requested book cannot be rated");
        }
        User user = ((User) connectedUser.getPrincipal());
        if(Objects.equals(book.getOwner().getId(),user.getId())) {
            throw new IllegalStateException("You cannot rate your own book");
        }

        Feedback feedbackEntity = feedbackMapper.toFeedback(request);
        return feedbackRepository.save(feedbackEntity).getId();

    }

    public PageResponse<FeedbackResponse> findAllFeedbacksByBook(Integer bookId, int page, int size, Authentication connectedUser) {
        Pageable pageable = createPageRequest(page, size);
        User user = ((User) connectedUser.getPrincipal());
        Page<Feedback> feedbacks = feedbackRepository.findAllByBookId(bookId, pageable);
        List<FeedbackResponse> feedbackResponses = mapToFeedbackResponses(feedbacks, user.getId());
        return createPageResponse(feedbackResponses, feedbacks);
    }

    private Pageable createPageRequest(int page, int size) {
        return PageRequest.of(page, size);
    }

    private List<FeedbackResponse> mapToFeedbackResponses(Page<Feedback> feedbacks, Integer userId) {
        return feedbacks.stream()
                .map(f -> feedbackMapper.toFeedbackResponse(f, userId))
                .toList();
    }

    private <T> PageResponse<T> createPageResponse(List<T> content, Page<?> page) {
        return new PageResponse<>(
                content,
                page.getNumber(),
                page.getSize(),
                page.getTotalElements(),
                page.getTotalPages(),
                page.isFirst(),
                page.isLast()
        );
    }
}
