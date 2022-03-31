package jyw.mysite.domain.form;

import lombok.Getter;

@Getter
public class PostHome {
    private Long id;
    private String loginId;
    private String title;
    private int commentCount;
    private String dateTime;

    public PostHome(Long id, String loginId, String title, int commentCount, String dateTime) {
        this.id = id;
        this.loginId = loginId;
        this.title = title;
        this.commentCount = commentCount;
        this.dateTime = dateTime;
    }

}
