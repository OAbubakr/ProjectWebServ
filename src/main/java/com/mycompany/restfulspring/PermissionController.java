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
import org.springframework.web.bind.annotation.RequestParam;
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
        boolean ret = dao.addPermission(permission);
        Response response = new Response();
        if (ret) {
        response.setStatus(Response.sucess);
        response.setResponseData(null);
        return response; 
        }
        else return response.createResponse(null);
    }
    @RequestMapping(value = "/getStudentPermissions", method = RequestMethod.GET, headers = "Accept=application/json")
    public Response getStudentPermissionsAuthorized(@RequestParam("studentId")  Integer myId) {

        PermissionDAO dao = DaoInstance.getInstance().getPermissionDAO();
//        System.out.println("--------------" + permission.getComment());
//        List<P ret = dao.getStudentPermissions(myId);
     return new Response().createResponse(dao.getStudentPermissions(myId));
      
    }
    
     @RequestMapping(value = "/getSupervisorPermissions", method = RequestMethod.GET, headers = "Accept=application/json")
    public Response getSuperisorPermissionsAuthorized(@RequestParam("supervisorId")  Integer myId) {

        PermissionDAO dao = DaoInstance.getInstance().getPermissionDAO();

     return new Response().createResponse(dao.getSupervisorPermissions(myId));
      
    }
    
    
     @RequestMapping(value = "/setPermissionResponse", method = RequestMethod.GET, headers = "Accept=application/json")
    public Response setPermissionResponseAuthorized(Integer myid, @RequestParam("permissionId")  int permissionId , @RequestParam("status")  boolean status) {

        PermissionDAO dao = DaoInstance.getInstance().getPermissionDAO();
        dao.setPermssionResponse(permissionId, status);
         Response response = new Response();
        response.setStatus(Response.sucess);
        response.setResponseData(null);
        return response; 
       

      
    }
//    @RequestMapping(value = "/addPermission", method = RequestMethod.POST, headers = "Accept=application/json")
//    public Response getSupervisorPermissionsAuthorized(Integer myId) {
//
//        PermissionDAO dao = DaoInstance.getInstance().getPermissionDAO();
//        System.out.println("--------------" + permission.getComment());
//        boolean ret = dao.addPermission(permission);
//        Response response = new Response();
//        if (ret) {
//        response.setStatus(Response.sucess);
//        response.setResponseData(null);
//        return response; 
//        }
//        else return response.createResponse(null);
//    }
}
