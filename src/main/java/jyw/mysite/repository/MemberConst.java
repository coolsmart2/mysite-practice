package jyw.mysite.repository;

public class MemberConst {

    static public int PASSWORD_MIN = 4;
    static public int PASSWORD_MAX = 10;

    static public String PASSWORD_PATTERN(int min, int max) {
//        return "^.*(?=^.{" + min + "," + max + "}$)(?=.*\\d)(?=.*[a-zA-Z])(?=.*[!@#$%^&+=]).*$";
        return "^.{" + min + "," + max + "}$";
    }
}
