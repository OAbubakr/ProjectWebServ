/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.restfulspring;

import bean.GraduateBasicData;
import bean.StudentDataByTrackID;
import dto.Response;
import java.util.ArrayList;
import javax.ws.rs.QueryParam;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import second.AllGraduateByTrackDao;
import second.AllStudentByTrackDao;
import second.DaoInstance;

/**
 *
 * @author engra
 */
@RestController
public class GraduateDataController {

    @RequestMapping(value = "/gradByIntakeAndTrack", method = RequestMethod.GET, headers = "Accept=application/json")
    public Response getGraduates(@QueryParam("id") int id,@QueryParam("platformId") int platformId) {
        AllGraduateByTrackDao dao = DaoInstance.getInstance().getAllGraduatesByTrackDao();
        ArrayList<GraduateBasicData> data = dao.getAllGraduateDataByTrackId(id,platformId);
        return new Response().createResponse(data);
    }
}
