public class Course {
    private String courseId;
    private String studentId;
    private String professorId;
    private String courseName;
    private int score;
    private String grade;

    public Course(String courseId, String studentId, String professorId, String courseName) {
        this.courseId = courseId;
        this.studentId = studentId;
        this.professorId = professorId;
        this.courseName = courseName;
        this.score = 0;
        this.grade = ""; // 학점 초기화
    }

    public String getCourseId() { return courseId; }
    public String getStudentId() { return studentId; }
    public String getProfessorId() { return professorId; }
    public String getCourseName() { return courseName; }
    public int getScore() { return score; }
    public String getGrade() { return grade; }

    public void setScore(int score) { this.score = score; }
    public void setGrade(String grade) { this.grade = grade; }
}