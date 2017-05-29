/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.restfulspring;

import bean.Branch;
import bean.Course;
import java.util.ArrayList;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import second.DaoInstance;
import second.ProgramDAO;

/**
 *
 * @author salma
 */
@RestController
public class Branches {

    @RequestMapping(value = "/getBranches", method = RequestMethod.GET, headers = "Accept=application/json")
    public ArrayList<Branch> getAnswers() {

        ProgramDAO d = DaoInstance.getInstance().getProgramDAO();
        ArrayList<Branch> branchs = d.getBranches();
        return branchs;
    }

    @RequestMapping(value = "/getCourses", method = RequestMethod.GET, headers = "Accept=application/json")
    public ArrayList<Course> getCourses(@RequestParam("trackId") int platformIntakeId) {

        ProgramDAO d = DaoInstance.getInstance().getProgramDAO();
        ArrayList<Course> courses = d.getCourseByTrackId(platformIntakeId);
        return courses;
    }

    @RequestMapping(value = "/getInstructorCourses", method = RequestMethod.GET, headers = "Accept=application/json")
    public ArrayList<Course> getInstructorCourses(@RequestParam("instructorId") int id) {

        ProgramDAO d = DaoInstance.getInstance().getProgramDAO();
        ArrayList<Course> courses = d.GetAllInstructorsCourseByEmpId(id);
        return courses;
    }

}
