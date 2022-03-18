package jyw.mysite.domain;

import lombok.Getter;

@Getter
public class PostHome {
    private Long id;
    private String title;
    private String dateTime;

    public PostHome(Long id, String title, String dateTime) {
        this.id = id;
        this.title = title;
        this.dateTime = dateTime;
    }

}
