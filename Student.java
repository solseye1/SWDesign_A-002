public class Student {
    private String studentId;
    private String password;
    private String name;

    public Student(String studentId, String password, String name) {
        this.studentId = studentId;
        this.password = password;
        this.name = name;
    }

    public String getStudentId() { return studentId; }
    public String getPassword() { return password; }
    public String getName() { return name; }
}