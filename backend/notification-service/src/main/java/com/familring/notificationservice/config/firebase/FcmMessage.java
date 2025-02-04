package com.familring.notificationservice.config.firebase;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@AllArgsConstructor
@Getter
public class FcmMessage {

    private boolean validateOnly;
    private Message message;

    @Builder
    @AllArgsConstructor
    @Getter
    public static class Message {
        private String token;
        private FcmDto data; //FcmDto
    }

    @Builder
    @AllArgsConstructor
    @Getter
    public static class FcmDto { //FcmDto
        private String title;
        private String body;
        private String type;
    }
}