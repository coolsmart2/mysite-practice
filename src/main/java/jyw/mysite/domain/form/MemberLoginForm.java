package jyw.mysite.domain.form;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class MemberLoginForm {
    @NotBlank
    private String loginId;
    @NotBlank
    private String password;
    @Override
    public String toString() {
        return "MemberLoginForm{" +
                "loginId='" + loginId + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
