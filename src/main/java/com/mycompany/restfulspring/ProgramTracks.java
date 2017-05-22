/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.restfulspring;

import bean.StudentBasicData;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import second.DaoInstance;
import second.ProgramDAO;

/**
 *
 * @author salma
 */

@RestController
public class ProgramTracks {
    
    @RequestMapping(value = "/getTracks", method = RequestMethod.GET, headers = "Accept=application/json")
    public HashMap<String,ArrayList<String>> getAnswers() {

        HashMap<String,ArrayList<String>> trackCourses = new HashMap<>();
        ProgramDAO d = DaoInstance.getInstance().getProgramDAO();
        List<Object[]> lists =d.getTracks();
        
        for (Object[] list : lists) {
            String trackName = (String)list[0];
            String courseName = (String)list[1];
            if (trackCourses.get(trackName)==null) {
                ArrayList<String> courseList=new ArrayList<>();
                courseList.add(courseName);
                trackCourses.put(trackName,courseList);
            }else{
                
                ArrayList<String> courseList=trackCourses.get(trackName);
                courseList.add(courseName);
                trackCourses.put(trackName, courseList);
            }
        }

        

        return trackCourses;
    }
}
