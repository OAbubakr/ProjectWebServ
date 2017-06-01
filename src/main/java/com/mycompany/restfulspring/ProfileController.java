/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.restfulspring;

import dto.Response;
import dto.UserData;
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
    public Response getUserData(@RequestParam("userType") int userType,@RequestParam("userId") int userId) {
        ProfileDAO profileDao = DaoInstance.getInstance().getProfileDao();
        return profileDao.getData(userType,userId);
    }

    @RequestMapping(value = "/onSetUserData", method = RequestMethod.GET, headers = "Accept=application/json")
    public Response setUserData(@RequestParam("userType") int userType,@RequestParam("userId") int userId, @RequestParam("userData") UserData userData) {
        ProfileDAO profileDao = DaoInstance.getInstance().getProfileDao();
        return profileDao.setData(userType,userId, userData);
    }
}
