/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.restfulspring;

import bean.Branch;
import bean.Course;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
        ArrayList<Branch> branchs =d.getBranches();
        
//        for (Object[] list : lists) {
//            String trackName = (String)list[0];
//            String courseName = (String)list[1];
//            if (trackCourses.get(trackName)==null) {
//                ArrayList<String> courseList=new ArrayList<>();
//                courseList.add(courseName);
//                trackCourses.put(trackName,courseList);
//            }else{
//                
//                ArrayList<String> courseList=trackCourses.get(trackName);
//                courseList.add(courseName);
//                trackCourses.put(trackName, courseList);
//            }
//        }

        

        return branchs;
    }
    
    @RequestMapping(value = "/getBranchesNames", method = RequestMethod.GET, headers = "Accept=application/json")
    public ArrayList<Branch> getBranchesNames() {
        ProgramDAO d = DaoInstance.getInstance().getProgramDAO();
        ArrayList<Branch> branchs = d.getBranchesNames();
        return branchs;
    }
    
    
    @RequestMapping(value = "/getCourses", method = RequestMethod.GET, headers = "Accept=application/json")
    public ArrayList<Course> getCourses(@RequestParam("trackId") int platformIntakeId) {

        
        ProgramDAO d = DaoInstance.getInstance().getProgramDAO();
        ArrayList<Course> courses =d.getCourseByTrackId(platformIntakeId);
        return courses;
    }
    
    @RequestMapping(value = "/getInstructorCourses", method = RequestMethod.GET, headers = "Accept=application/json")
    public ArrayList<Course> getInstructorCourses(@RequestParam("instructorId") int id) {

        
        ProgramDAO d = DaoInstance.getInstance().getProgramDAO();
        ArrayList<Course> courses =d.GetAllInstructorsCourseByEmpId(id);
        return courses;
    }
    
    
}
