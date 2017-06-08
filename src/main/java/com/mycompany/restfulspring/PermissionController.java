/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.restfulspring;

import bean.Permission;
import dto.LoginRequest;
import dto.Response;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import second.DaoInstance;
import second.PermissionDAO;

/**
 *
 * @author Rana Gamal
 */
@RestController
public class PermissionController {

    @RequestMapping(value = "/addPermission", method = RequestMethod.POST, headers = "Accept=application/json")
    public Response addPermissionAuthorized(Integer myId, @RequestBody Permission permission) {

        PermissionDAO dao = DaoInstance.getInstance().getPermissionDAO();
        System.out.println("--------------" + permission.getComment());
        dao.addPermission(permission);
        return new Response().createResponse(permission);
    }
}
