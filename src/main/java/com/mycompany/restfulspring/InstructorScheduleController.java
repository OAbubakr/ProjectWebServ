/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.restfulspring;

import bean.InstructorSession;
import dto.Response;
import java.util.ArrayList;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import second.DaoInstance;
import second.InstructorScheduleDao;

/**
 *
 * @author Sandra
 */
@RestController
public class InstructorScheduleController {
    @RequestMapping(value= "/getInstructorSchedule", method = RequestMethod.GET, headers = "Accept=application/json")
    public Response getStudentScheduleAuthorized(@RequestParam("instructorId") int instructorId){
        Response response = new Response();
        InstructorScheduleDao instructorScheduleDao = DaoInstance.getInstance().getInstructorScheduleDao();
        ArrayList<InstructorSession> instructorSessions = instructorScheduleDao.getInstructorSchedule(instructorId);
        return response.createResponse(instructorSessions);
        
    }
    
}
