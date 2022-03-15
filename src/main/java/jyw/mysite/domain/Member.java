package jyw.mysite.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Member {

    private Long id;
    private String loginId;
    private String password;

    public Member(String loginId, String password) {
        this.loginId = loginId;
        this.password = password;
    }
}
