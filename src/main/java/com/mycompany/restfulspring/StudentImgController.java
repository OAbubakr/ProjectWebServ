/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.restfulspring;


import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import second.DaoInstance;
import second.StudentImgDao;

/**
 *
 * @author Sandra
 */

@RestController
public class StudentImgController {
    
    @RequestMapping(value = "/getStudentImg", method = RequestMethod.GET, headers = "Accept=application/json")
    @Produces("image/jpg")
    public Response getStudentImg(){
        StudentImgDao studentImgDao = DaoInstance.getInstance().getStudentImgDao();
        return studentImgDao.getStudentImg();
       
        
    }
    
}
