package jyw.mysite.domain.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Getter
@Setter
@Entity
public class Member {

    @Id @GeneratedValue
    private Long id;
    private String loginId;
    private String password;

    public Member() {
    }

    public Member(String loginId, String password) {
        this.loginId = loginId;
        this.password = password;
    }

}

