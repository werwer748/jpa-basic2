package jpql;

public class MemberDto {

    private String username;
    private int age;

    //* 생성자를 통해서 select 절에서 호출 됨.
    public MemberDto(String username, int age) {
        this.username = username;
        this.age = age;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
