package jyw.mysite.domain;

import lombok.Getter;

@Getter
public class PostHome {
    private String title;
    private String dateTime;

    public PostHome(String title, String dateTime) {
        this.title = title;
        this.dateTime = dateTime;
    }
}
