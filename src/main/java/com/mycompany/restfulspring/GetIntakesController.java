/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.restfulspring;

import bean.ProgramIntake;
import dto.Response;
import java.util.ArrayList;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import second.DaoInstance;
import second.IntakesDao;

/**
 *
 * @author engra
 */
@RestController
public class GetIntakesController {
    
  @RequestMapping(value = "/getIntakes", method = RequestMethod.GET, headers = "Accept=application/json")
    public Response getAnswers() {

        Response response = new Response();
        IntakesDao d = DaoInstance.getInstance().getIntakes();
        ArrayList<ProgramIntake> programIntakes = d.getAllProgramIntake();
        
        return response.createResponse(programIntakes);
    }
}
