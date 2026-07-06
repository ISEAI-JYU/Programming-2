import java.util.*;

class Student extends Person {
    List<String> currentCourses;

    public Student(String name) {
        super(name);
        this.currentCourses = new ArrayList<>();
    }

    void showStudyProgram() {
        String courses = String.join(", ", currentCourses);
        IO.println(this.getName() + " studies the following courses: " + courses);
    }

    void enrollInCourse(String course) {
        currentCourses.add(course);
    }
}