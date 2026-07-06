import java.util.*;

class Teacher extends Person {
    List<String> coursesTaught;

    public Teacher() {
        this.coursesTaught = new ArrayList<>();
    }

    void showCoursesTaught() {
        String courses = String.join(", ", coursesTaught);
        IO.println(this.getName() + " teaches courses: " + courses);
    }

    void addCourse(String course) {
        coursesTaught.add(course);
    }
}