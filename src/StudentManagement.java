import java.util.ArrayList;
import java.util.Collections;

class Student {
    private String name;
    private int age;

    public Student(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    @Override
    public String toString() {
        return "Student{name='" + name + "', age=" + age + '}';
    }
}

public class StudentManagement {
    public static void main(String[] args) {
        ArrayList<Student> studentList = new ArrayList<>();

        //添加Student对象到ArrayList
        studentList.add(new Student("Alice", 20));
        studentList.add(new Student("Bob", 22));
        studentList.add(new Student("Charlie", 21));

        //遍历ArrayList
        System.out.println("遍历学生列表：");
        for (Student student : studentList) {
            System.out.println(student);
        }

        //查询对象
        String searchName = "Bob";
        for (Student student : studentList) {
            if (student.getName().equals(searchName)) {
                System.out.println("查询到学生：" + student);
                break;
            }
        }

        //删除对象
        String deleteName = "Alice";
        Student studentToRemove = null;
        for (Student student : studentList) {
            if (student.getName().equals(deleteName)) {
                studentToRemove = student;
                break;
            }
        }
        if (studentToRemove != null) {
            studentList.remove(studentToRemove);
            System.out.println("已删除学生：" + studentToRemove);
        }

        // 排序ArrayList
        Collections.sort(studentList, (s1, s2) -> s1.getName().compareTo(s2.getName()));

        // 再次遍历已排序的ArrayList
        System.out.println("排序后的学生列表：");
        for (Student student : studentList) {
            System.out.println(student);
        }
    }
}
