package mthree.com.fullstackschool.service;

import mthree.com.fullstackschool.dao.StudentDao;
import mthree.com.fullstackschool.model.Course;
import mthree.com.fullstackschool.model.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class StudentServiceImpl implements StudentServiceInterface {

    //YOUR CODE STARTS HERE

    @Autowired
    private StudentDao studentDao;

    @Autowired
    CourseServiceImpl courseServiceImpl;

    @Autowired
    public StudentServiceImpl(StudentDao studentDao){
        this.studentDao=studentDao;
    }


    //YOUR CODE ENDS HERE

    public List<Student> getAllStudents() {
        //YOUR CODE STARTS HERE

        return studentDao.getAllStudents();

        //YOUR CODE ENDS HERE
    }

    public Student getStudentById(int id) {
        //YOUR CODE STARTS HERE

        try {
            return studentDao.findStudentById(id);
        } catch (DataAccessException e) {
            Student student = new Student();
            student.setStudentFirstName("Student Not Found");
            student.setStudentLastName("Student Not Found");
            return student;
        }

        //YOUR CODE ENDS HERE
    }

    public Student addNewStudent(Student student) {
        //YOUR CODE STARTS HERE

        if (student.getStudentFirstName() == null || student.getStudentFirstName().isBlank()) {
            student.setStudentFirstName("First Name blank, student NOT added");
        }
        if (student.getStudentLastName() == null || student.getStudentLastName().isBlank()) {
            student.setStudentLastName("Last Name blank, student NOT added");
        }
        return studentDao.createNewStudent(student);

        //YOUR CODE ENDS HERE
    }

    public Student updateStudentData(int id, Student student) {
        //YOUR CODE STARTS HERE

        if (id != student.getStudentId()) {
            student.setStudentFirstName("IDs do not match, student not updated");
            student.setStudentLastName("IDs do not match, student not updated");
            return student;
        }
        studentDao.updateStudent(student);
        return studentDao.findStudentById(id);

        //YOUR CODE ENDS HERE
    }

    public void deleteStudentById(int id) {
        //YOUR CODE STARTS HERE

        studentDao.deleteStudent(id);

        //YOUR CODE ENDS HERE
    }

    public void deleteStudentFromCourse(int studentId, int courseId) {
        //YOUR CODE STARTS HERE

        Student student = studentDao.findStudentById(studentId);
        Course course = courseServiceImpl.getCourseById(courseId);

        if ("Student Not Found".equals(student.getStudentFirstName())) {
            System.out.println("Student not found");
        } else if ("Course Not Found".equals(course.getCourseId())) {
            System.out.println("Course not found");
        } else {
            studentDao.deleteStudentFromCourse(studentId, courseId);
            System.out.println("Student: " + studentId + " deleted from course: " + courseId);
        }

        //YOUR CODE ENDS HERE
    }

    public void addStudentToCourse(int studentId, int courseId) {
        //YOUR CODE STARTS HERE

        Student student = studentDao.findStudentById(studentId);
        Course course = courseServiceImpl.getCourseById(courseId);

        if ("Student Not Found".equals(student.getStudentFirstName())) {
            System.out.println("Student not found");
        } else if ("Course Not Found".equals(course.getCourseId())) {
            System.out.println("Course not found");
        } else {
            try {
                studentDao.addStudentToCourse(studentId, courseId);
                System.out.println("Student: " + studentId + " added to course: " + courseId);
            } catch (Exception e) {
                System.out.println("Student: " + studentId + " already enrolled in course: " + courseId);
            }
        }
        //YOUR CODE ENDS HERE
    }
}
