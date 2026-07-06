void main() {
    Student student = new Student();
    student.setName("Olli Student");
    student.enrollInCourse("Programming 2");

    Teacher teacher = new Teacher();
    teacher.setName("Maija Teacher");
    teacher.addCourse("Programming 1");
    teacher.addCourse("Programming 2");
    teacher.showCoursesTaught();
}