package Persistence;

import practice.sv.bai1.ReadFile;
import practice.sv.bai1.Student;
import sv.practice.mysql.Insert;

import java.io.File;
import java.util.ArrayList;

public class insertFile {
    public static void main(String filename) {
        ArrayList<Student> students = new ArrayList<>();
        students = (ArrayList<Student>) ReadFile.listStudent(filename);
        Insert.insertInfo(students);
    }
}
