package jyw.mysite.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberLoginForm {
    private String loginId;
    private String password;

    @Override
    public String toString() {
        return "MemberLoginForm{" +
                "loginId='" + loginId + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
