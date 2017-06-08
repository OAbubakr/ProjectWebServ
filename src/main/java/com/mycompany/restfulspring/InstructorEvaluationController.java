/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.restfulspring;

import bean.InstructorEvaluation;
import dto.Response;
import java.util.ArrayList;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import second.DaoInstance;
import second.InstructorEvaluationDAO;

/**
 *
 * @author Rana Gamal
 */

@RestController
public class InstructorEvaluationController {
    
    @RequestMapping(value= "/getInstructorEvaluation", method = RequestMethod.GET, headers = "Accept=application/json")
    public Response getInstructorEvaluationAuthorized(@RequestParam("instId") int instId){
        Response response = new Response();
        InstructorEvaluationDAO dao = DaoInstance.getInstance().getInstructorEvaluationDAO();
        ArrayList<InstructorEvaluation> instructorEvaluations = dao.getInstructorEvaluation(instId);
        return response.createResponse(instructorEvaluations);
    }
}
