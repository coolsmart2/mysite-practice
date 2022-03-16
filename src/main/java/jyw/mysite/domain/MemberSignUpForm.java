package jyw.mysite.domain;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class MemberSignUpForm {
    @NotBlank
    private String loginId;
    @NotBlank
    private String password;
    private String checkPassword;
}
