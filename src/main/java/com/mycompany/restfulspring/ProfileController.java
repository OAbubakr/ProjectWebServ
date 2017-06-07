/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.restfulspring;

import dto.Response;
import dto.UserData;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import second.DaoInstance;
import second.ProfileDAO;

/**
 *
 * @author Mahmoud
 */
@RestController
@RequestMapping("/profile")
public class ProfileController {

    @RequestMapping(value = "/onGetUserData", method = RequestMethod.GET, headers = "Accept=application/json")
    public Response getUserDataAuthorized(@RequestParam("token") String token,@RequestParam("userType") int userType) {
        ProfileDAO profileDao = DaoInstance.getInstance().getProfileDao();
        return profileDao.getData(token,userType);
    }

    @RequestMapping(value = "/onSetUserData", method = RequestMethod.POST, headers = "Accept=application/json")
    public Response setUserData(@RequestParam("userType") int userType,@RequestParam("userId") int userId, @RequestBody UserData userData) {
        ProfileDAO profileDao = DaoInstance.getInstance().getProfileDao();
        profileDao.setData(userType,userId, userData);
     
        return new Response().createResponse(userData); // dummy
        
    }
}
