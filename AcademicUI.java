import java.util.ArrayList;
import java.util.List;

public class AcademicUI {
    private List<Student> students = new ArrayList<>();
    private List<Professor> professors = new ArrayList<>();
    private List<Course> courses = new ArrayList<>();

    // 별도로 지정되지 않은 변수는 String으로 처리
    private String loggedInId = "";
    private String loggedInType = ""; // "STUDENT", "PROFESSOR", "NONE"

    // ================= [학생 관리 기능] =================
    
    public boolean registerStudent(String studentId, String password, String name) {
        for (Student s : students) {
            if (s.getStudentId().equals(studentId)) return false;
        }
        students.add(new Student(studentId, password, name));
        return true;
    }

    public boolean viewStudent(String studentId) {
        for (Student s : students) {
            if (s.getStudentId().equals(studentId)) {
                System.out.println("[학생조회] 학번: " + s.getStudentId() + " / 성명: " + s.getName());
                return true;
            }
        }
        return false;
    }

    public boolean authStudent(String studentId, String password) {
        for (Student s : students) {
            if (s.getStudentId().equals(studentId) && s.getPassword().equals(password)) {
                loggedInId = studentId;
                loggedInType = "STUDENT";
                return true;
            }
        }
        return false;
    }

    // ================= [교수 관리 기능] =================

    public boolean registerProfessor(String professorId, String password, String name) {
        for (Professor p : professors) {
            if (p.getProfessorId().equals(professorId)) return false;
        }
        professors.add(new Professor(professorId, password, name));
        return true;
    }

    public boolean viewProfessor(String professorId) {
        for (Professor p : professors) {
            if (p.getProfessorId().equals(professorId)) {
                System.out.println("[교수조회] 교수ID: " + p.getProfessorId() + " / 성명: " + p.getName());
                return true;
            }
        }
        return false;
    }

    public boolean authProfessor(String professorId, String password) {
        for (Professor p : professors) {
            if (p.getProfessorId().equals(professorId) && p.getPassword().equals(password)) {
                loggedInId = professorId;
                loggedInType = "PROFESSOR";
                return true;
            }
        }
        return false;
    }

    // ================= [과목 및 학사 기능] =================

    public boolean registerCourse(String courseId, String courseName) {
        if (!loggedInType.equals("PROFESSOR")) return false; // 교수 인증 확인

        int count = 0;
        for (Course c : courses) {
            if (c.getProfessorId().equals(loggedInId) && c.getStudentId().equals("")) {
                count++;
            }
            if (c.getCourseId().equals(courseId) && c.getStudentId().equals("")) {
                return false; // 이미 개설된 과목
            }
        }

        if (count >= 3) return false; // 교수는 1~3과목 등록 제한

        // 개설 과목은 학생ID를 비워두어 원본 상태로 저장
        courses.add(new Course(courseId, "", loggedInId, courseName));
        return true;
    }

    public boolean viewCourse(String courseId) {
        for (Course c : courses) {
            if (c.getCourseId().equals(courseId) && c.getStudentId().equals("")) {
                System.out.println("[과목조회] 과목ID: " + c.getCourseId() + " / 과목명: " + c.getCourseName() + " (담당: " + c.getProfessorId() + ")");
                return true;
            }
        }
        return false;
    }

    public boolean applyCourse(String courseId) {
        if (!loggedInType.equals("STUDENT")) return false; // 학생 인증 확인

        Course targetCourse = null;
        int studentCourseCount = 0;
        int currentEnrolledCount = 0;

        for (Course c : courses) {
            if (c.getCourseId().equals(courseId)) {
                if (c.getStudentId().equals("")) targetCourse = c;
                else currentEnrolledCount++;
            }
            if (c.getStudentId().equals(loggedInId)) {
                studentCourseCount++;
                if (c.getCourseId().equals(courseId)) return false; // 중복 수강 방지
            }
        }

        if (targetCourse == null) return false; // 등록되지 않은 과목
        if (studentCourseCount >= 5) return false; // 학생은 최대 5개 과목 제한
        if (currentEnrolledCount >= 35) return false; // 과목 정원 최대 35명 제한

        // 학생ID가 포함된 수강 내역(복제) 객체 생성 및 리스트 추가
        courses.add(new Course(courseId, loggedInId, targetCourse.getProfessorId(), targetCourse.getCourseName()));
        return true;
    }

    // 조건: 점수 입력 시 과목조회, 학생조회를 먼저 거친 뒤 학점을 반환
    public String inputCourseScore(String courseId, String studentId, int score) {
        if (!loggedInType.equals("PROFESSOR")) return "ERROR";

        // 입력 전 과목조회와 학생조회 확인 (Boundary UI 흐름)
        if (!viewCourse(courseId)) return "COURSE_NOT_FOUND";
        if (!viewStudent(studentId)) return "STUDENT_NOT_FOUND";

        for (Course c : courses) {
            if (c.getCourseId().equals(courseId) && c.getStudentId().equals(studentId) && c.getProfessorId().equals(loggedInId)) {
                c.setScore(score); // 점수 입력
                
                // 학점 자동 계산 및 저장
                String grade = "";
                if (score >= 90) grade = "A";
                else if (score >= 80) grade = "B";
                else if (score >= 70) grade = "C";
                else grade = "D";

                c.setGrade(grade);
                return grade; // 입력 완료 시 학점 반환
            }
        }
        return "ERROR_NOT_ENROLLED";
    }

    // 조건: 학점이 String이므로 String 타입 반환
    public String viewGrade(String courseId) {
        if (!loggedInType.equals("STUDENT")) return "ERROR";

        for (Course c : courses) {
            if (c.getCourseId().equals(courseId) && c.getStudentId().equals(loggedInId)) {
                if (c.getGrade().equals("")) return "아직 성적이 입력되지 않았습니다.";
                return c.getGrade(); // 학점 반환
            }
        }
        return "수강 내역을 찾을 수 없습니다.";
    }
}