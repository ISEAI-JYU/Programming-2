import java.util.ArrayList;
import java.util.List;

class Teacher extends Person {
    private List<String> coursesTaught;

    public Teacher(String name) {
        super(name);
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