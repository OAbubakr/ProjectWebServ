/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.restfulspring;

import bean.InstructorSession;
import bean.StudentSession;
import java.util.ArrayList;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import second.DaoInstance;
import second.InstructorScheduleDao;
import second.StudentScheduleDao;

/**
 *
 * @author Sandra
 */
@RestController
public class InstructorScheduleController {
    @RequestMapping(value= "/getInstructorSchedule", method = RequestMethod.GET, headers = "Accept=application/json")
    public ArrayList<InstructorSession> getStudentSchedule(@RequestParam("instructorId") int instructorId){
        InstructorScheduleDao instructorScheduleDao = DaoInstance.getInstance().getInstructorScheduleDao();
        return instructorScheduleDao.getInstructorSchedule(instructorId);
        
    }
    
}
