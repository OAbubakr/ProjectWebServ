/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.restfulspring;

import bean.Instructor;
import dto.Response;
import java.util.List;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import second.DaoInstance;
import second.InstructorsByBranchDAO;
import second.StudentGradeDAO;

/**
 *
 * @author home
 */
@RestController
public class InstructorsByBranch {
 
    @RequestMapping(value = "/getInstructorByBranch",  method = RequestMethod.GET, headers = "Accept=application/json")
    public Response getInstructorByBranch(@RequestParam("id") int branchId, 
            @RequestParam("excludeId") int excludeId){
        
        InstructorsByBranchDAO instructorsByBranchDAO = DaoInstance.getInstance().getInstructorsByBranchDAO();
        return new Response().createResponse(instructorsByBranchDAO.getInstructorsByBranch(branchId, excludeId));
        
    }
    
}
