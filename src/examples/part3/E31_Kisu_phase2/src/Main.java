void main() {
    Student student = new Student("Olli Opiskelija");
    student.enrollInCourse("Programming 2");
    student.showStudyProgram();

    Teacher teacher = new Teacher("Maija Teacher");
    teacher.addCourse("Programming 1");
    teacher.addCourse("Programming 2");
    teacher.showCoursesTaught();
}