package cn.newbeedaly.im.model;

import lombok.Data;

@Data
public class Message {
    private String username;
    private String content;
    private String room;
}