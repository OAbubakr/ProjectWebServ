/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.restfulspring;

import bean.Course;
//import bean.StudentBasicData;
import java.util.ArrayList;
import java.util.List;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import second.CourseDAO;
import second.CourseDaoInstance;
import second.DaoInstance;

/**
 *
 * @author engra
 */

@RestController
public class CourseController {
    
//    @RequestMapping(value = "/getCourses", method = RequestMethod.GET, headers = "Accept=application/json")
//    public ArrayList<Course> getAnswers(@RequestParam("id") int id) {
//        CourseDAO courseDAO = CourseDaoInstance.getInstance().getCourseDao();
//        return courseDAO.getCourseByTrackId(id);
//       
//    }
}
