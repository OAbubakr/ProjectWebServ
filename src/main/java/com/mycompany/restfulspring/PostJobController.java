/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.restfulspring;

import bean.JobOpportunity;
import dto.Response;
import javax.ws.rs.QueryParam;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import second.DaoInstance;
import second.PostJobDAO;

/**
 *
 * @author Rana Gamal
 */

@RestController
public class PostJobController {
    
//    @RequestMapping(value= "/postJob", method = RequestMethod.GET, headers = "Accept=application/json")
//    public String postJob(
//            @QueryParam("companyId") int companyId, @QueryParam("jobCode") String jobCode, 
//            @QueryParam("jobTitle") String jobTitle, @QueryParam("jobDesc") String jobDesc,
//            @QueryParam("experience") String experience, @QueryParam("closingDate") String closingDate,
//            @QueryParam("sendTo") String sendTo, @QueryParam("jobNoNeed") int jobNoNeed,
//            @QueryParam("subTrackId") int subTrackId , @QueryParam("jobDate") String jobDate ){
//        
//        PostJobDAO dao = DaoInstance.getInstance().getPostJobDAO();
//        return dao.postJob(companyId, jobCode, jobTitle, jobDesc, experience, closingDate, sendTo, jobNoNeed, subTrackId, jobDate);
//    }
    
    @RequestMapping(value= "/postJob", method = RequestMethod.POST, headers = "Accept=application/json")
    public Response postJobAuthorized(Integer myId, @RequestBody JobOpportunity jobOpportunity){
        
        PostJobDAO dao = DaoInstance.getInstance().getPostJobDAO();
        return new Response().createResponse(dao.postJob(jobOpportunity)) ;
    }
}
