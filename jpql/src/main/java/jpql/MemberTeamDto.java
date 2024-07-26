package jpql;

public class MemberTeamDto {

    private Long id;
    private String username;
    private String teamName;

    public MemberTeamDto(Long id, String username, String teamName) {
        this.id = id;
        this.username = username;
        this.teamName = teamName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    @Override
    public String toString() {
        return "MemberTeamDto{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", teamName='" + teamName + '\'' +
                '}';
    }
}
