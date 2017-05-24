/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.restfulspring;

import bean.StudentGrade;
import java.util.ArrayList;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import second.CourseDAO;
import second.CourseDaoInstance;
import second.StudentGradeDAO;
import second.StuGrade_Instance;

/**
 *
 * @author engra
 */
@RestController
public class StudentGradeController {
    @RequestMapping(value = "/getStudentGrades", method = RequestMethod.GET, headers = "Accept=application/json")
    public ArrayList<StudentGrade> getAnswers(@RequestParam("id") int id) {
        StudentGradeDAO studentGradeDAO = StuGrade_Instance.getInstance().getCourseDao();
        return studentGradeDAO.getAllStudentGrade(id);
       
    }
}
