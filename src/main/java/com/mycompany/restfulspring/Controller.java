/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.restfulspring;

import bean.Student;
import beans.Users;
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
    
    @RequestMapping(value = "/students", method = RequestMethod.GET, headers = "Accept=application/json")
    public ArrayList<Student> getCountries() {
        ArrayList<Student> students = new ArrayList<>();
        Student s = new Student();
        Student s1 = new Student();
        s.setId(1);
        s1.setId(2);
        s.setName("wafaa");
        s1.setName("qwee");
        students.add(s);
        students.add(s1);
        return students;
    }
    
    @RequestMapping(value = "/setStudent", method = RequestMethod.POST)
    public ResponseEntity<Users> setStudent(@RequestBody Users stu) {
        DAO dao = DaoInstance.getInstance().getDAO();
        dao.insertCustomer(stu);
        
        return new ResponseEntity<>(stu, HttpStatus.OK);
    }
    
}
