/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.restfulspring;

import bean.EmployeeHours;
import java.util.ArrayList;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import second.DaoInstance;
//import second.CourseDaoInstance;
import second.EmpHoursDAO;
//import second.EmpHours_DAOInstance;

/**
 *
 * @author engra
 */
@RestController
public class EmpHours_Controller {

    @RequestMapping(value = "/getEmpHours", method = RequestMethod.GET, headers = "Accept=application/json")
    public EmployeeHours getAnswers(@RequestParam("id") int id, @RequestParam("start") String start, 
            @RequestParam("end") String end) {
        
        EmpHoursDAO empHoursDAO = DaoInstance.getInstance().getEmpHours();
        return empHoursDAO.getEmployeeHours(id, start, end);

    }
}
