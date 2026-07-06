import java.util.*;

class Student {
    String name;
    List<String> currentCourses;

    public Student() {
        this.currentCourses = new ArrayList<>();
    }

    void setName(String name) {
        this.name = name;
    }

    String getName() {
        return this.name;
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