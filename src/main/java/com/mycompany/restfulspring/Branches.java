/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.restfulspring;

import bean.Branch;
import bean.Course;
import dto.Response;
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
    public Response getAnswers() {

        Response response = new Response();
        ProgramDAO d = DaoInstance.getInstance().getProgramDAO();
        ArrayList<Branch> branchs = d.getBranches();
        
        return response.createResponse(branchs);
    }

    @RequestMapping(value = "/getBranchesNames", method = RequestMethod.GET, headers = "Accept=application/json")
    public Response getBranchesNames() {
        Response response = new Response();
        ProgramDAO d = DaoInstance.getInstance().getProgramDAO();
        ArrayList<Branch> branchs = d.getBranchesNames();
        
        return response.createResponse(branchs);
    }

    @RequestMapping(value = "/getCourses", method = RequestMethod.GET, headers = "Accept=application/json")
    public Response getCourses(@RequestParam("trackId") int platformIntakeId) {
        Response response = new Response();
        ProgramDAO d = DaoInstance.getInstance().getProgramDAO();
        ArrayList<Course> courses = d.getCourseByTrackId(platformIntakeId);
        
        return response.createResponse(courses);
    }

    @RequestMapping(value = "/getInstructorCourses", method = RequestMethod.GET, headers = "Accept=application/json")
    public Response getInstructorCoursesAuthorized(@RequestParam("instructorId") int id) {
        Response response = new Response();
        ProgramDAO d = DaoInstance.getInstance().getProgramDAO();
        ArrayList<Course> courses = d.GetAllInstructorsCourseByEmpId(id);
        
        return response.createResponse(courses);
    }

}
