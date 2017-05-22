/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.restfulspring;

import bean.StudentBasicData;
import bean.StudentSession;
import java.util.ArrayList;
import javax.ws.rs.QueryParam;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import second.DaoInstance;
import second.StudentDAO;
import second.StudentScheduleDao;

/**
 *
 * @author waffaa
 */
@RestController
public class Controller {
 
    @RequestMapping(value = "/getStudents", method = RequestMethod.GET)
    public ArrayList<StudentBasicData> getAnswers( @QueryParam("id") int id) {
        ArrayList<StudentBasicData> answers = new ArrayList<>();
         StudentDAO dao = DaoInstance.getInstance().getStudentDAO();
         StudentBasicData s = dao.getStudentById(id);
        answers.add(s);
       
        return answers;
    }
    
    @RequestMapping(value= "/getStudentSchedule", method = RequestMethod.GET, headers = "Accept=application/json")
    public ArrayList<StudentSession> getStudentSchedule(){
        StudentScheduleDao studentScheduleDao = DaoInstance.getInstance().getStudentScheduleDao();
        return studentScheduleDao.getStudentSchedule();
        
    }

//    @RequestMapping(value = "/submitAnswer", method = RequestMethod.POST)
//    public ResponseEntity<Answer> submitAnswer(@RequestBody Answer ans) {
//        DAO dao = DaoInstance.getInstance().getDAO();
//        dao.insertAnswer(ans);
//        
//        return new ResponseEntity<>(ans, HttpStatus.OK);
//    }
    
}
