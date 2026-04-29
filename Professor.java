public class Professor {
    private String professorId;
    private String password;
    private String name;

    public Professor(String professorId, String password, String name) {
        this.professorId = professorId;
        this.password = password;
        this.name = name;
    }

    public String getProfessorId() { return professorId; }
    public String getPassword() { return password; }
    public String getName() { return name; }
}