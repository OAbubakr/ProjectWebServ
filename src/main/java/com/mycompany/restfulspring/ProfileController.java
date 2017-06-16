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
    public Response getUserDataAuthorized(@RequestParam("token") int token,@RequestParam("userType") int userType) {
        ProfileDAO profileDao = DaoInstance.getInstance().getProfileDao();
        return profileDao.getData(token,userType);
    }

    @RequestMapping(value = "/onSetUserData", method = RequestMethod.POST, headers = "Accept=application/json")
    public Response setUserDataAuthorized(@RequestParam("userId") int userId,@RequestParam("userType") int userType
            , @RequestBody UserData userData) {
        ProfileDAO profileDao = DaoInstance.getInstance().getProfileDao();
       
     
        return  profileDao.setData(userType,userId, userData); // dummy 
    }
    
      @RequestMapping(value = "/onGetUserDataOther", method = RequestMethod.GET, headers = "Accept=application/json")
    public Response getUserDataOtherAuthorized(Integer id ,@RequestParam("userId") int userId,@RequestParam("userType") int userType) {
        ProfileDAO profileDao = DaoInstance.getInstance().getProfileDao();
        return profileDao.getData(userId,userType);
    }
    
}
