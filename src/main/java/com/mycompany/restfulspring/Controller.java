/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.restfulspring;

import beans.Answer;
import java.util.ArrayList;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import second.DAO;
import second.DaoInstance;

/**
 *
 * @author waffaa
 */
@RestController
public class Controller {
//    
    @RequestMapping(value = "/getAnswer", method = RequestMethod.GET, headers = "Accept=application/json")
    public ArrayList<Answer> getCountries() {
        ArrayList<Answer> answers = new ArrayList<>();
         DAO dao = DaoInstance.getInstance().getDAO();
        answers.add(dao.getCustomerBuyId(1));
       
        return answers;
    }
//    
    @RequestMapping(value = "/setStudent", method = RequestMethod.POST)
    public ResponseEntity<Answer> setStudent(@RequestBody Answer stu) {
        DAO dao = DaoInstance.getInstance().getDAO();
        dao.insertAnswer(stu);
        
        return new ResponseEntity<>(stu, HttpStatus.OK);
    }
    
}
