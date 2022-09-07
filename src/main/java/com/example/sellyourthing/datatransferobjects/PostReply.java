package com.example.sellyourthing.datatransferobjects;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PostReply {
    private String message;
    private String replyEmail;
    private Long postId;
}
