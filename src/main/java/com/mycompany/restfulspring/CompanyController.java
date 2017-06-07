/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.restfulspring;


import bean.CompanyProfile;
import bean.JobVacancy;
import dto.Response;
import java.util.ArrayList;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import second.CompanyDAO;
import second.DaoInstance;

/**
 *
 * @author salma
 */

@RestController
public class CompanyController {
    
    
    @RequestMapping(value = "/getCompanyProfile", method = RequestMethod.GET, headers = "Accept=application/json")
    public Response getCompanyProfile(@RequestParam("companyID") int id) {
        Response response = new Response();
        CompanyDAO companyDAO = DaoInstance.getInstance().getCompanyDAO();
        CompanyProfile companyProfile = companyDAO.getCompanyProfile(id);
        return response.createResponse(companyProfile);
    }
    
    
    @RequestMapping(value = "/getAllVacancies", method = RequestMethod.GET, headers = "Accept=application/json")
    public Response getAllVacancies() {
    Response response = new Response();
        CompanyDAO companyDAO = DaoInstance.getInstance().getCompanyDAO();
        ArrayList<JobVacancy> jobVacancys = companyDAO.getAllJobs();
        return response.createResponse(jobVacancys);
    }
    
    @RequestMapping(value = "/getAllCompanies", method = RequestMethod.GET, headers = "Accept=application/json")
    public Response getAllCompanies() {
        Response response = new Response();
        CompanyDAO companyDAO = DaoInstance.getInstance().getCompanyDAO();
        ArrayList<JobVacancy> jobVacancys = companyDAO.getAllCompanies();
        return response.createResponse(jobVacancys);
    }
}
