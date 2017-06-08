/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.restfulspring;


import bean.StudentSession;
import dto.Response;
import java.util.ArrayList;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import second.DaoInstance;
import second.StudentScheduleDao;
import second.TrackScheduleDao;

/**
 *
 * @author waffaa
 */
@RestController
public class Controller {

//    @RequestMapping(value = "/getStudents", method = RequestMethod.GET, headers = "Accept=application/json")
//    public ArrayList<StudentBasicData> getAnswers() {
//        ArrayList<StudentBasicData> answers = new ArrayList<>();
//         StudentDAO dao = DaoInstance.getInstance().getStudentDAO();
//         StudentBasicData s = dao.getStudentById(5699);
//        answers.add(s);
//       
//        
//
//        return answers;
//    }
    
    @RequestMapping(value= "/getStudentSchedule", method = RequestMethod.GET, headers = "Accept=application/json")
    public Response getStudentScheduleAuthorized(@RequestParam("studentId") int studentId){
        Response response = new Response();
        StudentScheduleDao studentScheduleDao = DaoInstance.getInstance().getStudentScheduleDao();
        ArrayList<StudentSession> studentSessions =studentScheduleDao.getStudentSchedule(studentId);
        return response.createResponse(studentSessions);
        
    }
    
    
    @RequestMapping(value= "/getTrackSchedule", method = RequestMethod.GET, headers = "Accept=application/json")
    public Response getTrackScheduleAuthorized( Integer myId, @RequestParam("trackId") int trackId){
        
        Response response = new Response();
       TrackScheduleDao trackScheduleDao=DaoInstance.getInstance().getTrackScheduleDao();
       ArrayList<StudentSession> studentSessions = trackScheduleDao.getTrackSchedule(trackId);
       return  response.createResponse(studentSessions);
        
    }

//    @RequestMapping(value = "/submitAnswer", method = RequestMethod.POST)
//    public ResponseEntity<Answer> submitAnswer(@RequestBody Answer ans) {
//        DAO dao = DaoInstance.getInstance().getDAO();
//        dao.insertAnswer(ans);
//        
//        return new ResponseEntity<>(ans, HttpStatus.OK);
//    }
}
