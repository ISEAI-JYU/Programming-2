import java.util.*;

class Teacher {
    String name;
    List<String> coursesTaught;

    public Teacher() {
        this.coursesTaught = new ArrayList<>();
    }

    void setName(String name) {
        this.name = name;
    }

    String getName() {
        return this.name;
    }

    void showCoursesTaught() {
        String courses = String.join(", ", coursesTaught);
        IO.println(this.getName() + " teaches courses: " + courses);
    }

    void addCourse(String course) {
        coursesTaught.add(course);
    }
}