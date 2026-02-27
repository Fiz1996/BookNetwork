package com.keycload.book.network.book.feedback;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FeedbackResponse {

    private Double note;
    private String comments;
    private boolean ownFeedback;

}
