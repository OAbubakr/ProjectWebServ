/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.restfulspring;

import bean.StudentDataByTrackID;
import bean.Supervisor;
import dto.Response;
import java.util.ArrayList;
import javax.ws.rs.QueryParam;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import second.AllStudentByTrackDao;
import second.DaoInstance;
import second.SupervisorDao;

/**
 *
 * @author admin
 */
@RestController
public class SupervisorController {

    @RequestMapping(value = "/getSupervisorByTrackId", method = RequestMethod.GET, headers = "Accept=application/json")
    public Response getStudents(@QueryParam("id") int id) {
        SupervisorDao dao = DaoInstance.getInstance().getSupervisorDao();
        return new Response().createResponse(dao);
    }

}
