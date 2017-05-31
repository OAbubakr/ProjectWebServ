/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.restfulspring;

import dto.LoginRequest;
import dto.LoginResponse;
import dto.Response;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import second.DaoInstance;
import second.LoginDAO;

/**
 *
 * @author Mahmoud
 */

@RestController
@RequestMapping("/login")
public class LoginController {
    
    @RequestMapping(value = "/onLoginAuth", method = RequestMethod.GET, headers = "Accept=application/json")
    public Response onLoginAuth(@RequestParam("userType") int userType,@RequestParam("userName") String userName,
            @RequestParam("password") String password){
        System.out.println(userType+"   "+userName+"    "+password);
        LoginDAO loginDao = DaoInstance.getInstance().getLoginDao();
        return loginDao.getUserId(userType,userName, password);
        
    }
    
    
     @RequestMapping(value = "/onLoginAuth", method = RequestMethod.POST, headers = "Accept=application/json")
    public Response onLoginAuth(@RequestBody LoginRequest request){
        System.out.println(request.getUserType()+"   "+request.getUserName()+"    "+request.getPassword());
        LoginDAO loginDao = DaoInstance.getInstance().getLoginDao();
        return loginDao.getUserId(request.getUserType(),request.getUserName(),request.getPassword());
        
    }
}
