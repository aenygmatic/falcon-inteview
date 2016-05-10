package io.falcon.interview.virtualboard.services.domain;


import java.util.Date;

public class Post {

    private String id;
    private String content;
    private Date issued;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getIssued() {
        return issued;
    }

    public void setIssued(Date issued) {
        this.issued = issued;
    }
}
