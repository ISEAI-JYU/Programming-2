import java.util.*;

class Student extends Person {
    List<String> currentCourses;

    public Student() {
        this.currentCourses = new ArrayList<>();
    }

    void showStudyProgram() {
        String courses = String.join(", ", currentCourses);
        IO.println(name + " studies the following courses: " + courses);
    }

    void enrollInCourse(String course) {
        IO.println(this.name + " enrolled in course: " + course);
        currentCourses.add(course);
    }
}