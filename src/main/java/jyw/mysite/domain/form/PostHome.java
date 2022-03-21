package jyw.mysite.domain.form;

import lombok.Getter;

@Getter
public class PostHome {
    private Long id;
    private String loginId;
    private String title;
    private String dateTime;

    public PostHome(Long id, String loginId, String title, String dateTime) {
        this.id = id;
        this.loginId = loginId;
        this.title = title;
        this.dateTime = dateTime;
    }

}
