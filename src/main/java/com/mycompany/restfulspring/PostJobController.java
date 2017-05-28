/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.restfulspring;

import java.util.Date;
import javax.ws.rs.QueryParam;
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
    
    @RequestMapping(value= "/postJob", method = RequestMethod.GET, headers = "Accept=application/json")
    public int InsertNewJop(
            @RequestParam int companyId, @RequestParam String jopCode, 
            @RequestParam String jopTitle, @RequestParam String jopDesc, @RequestParam String experience,
            @RequestParam String closingDate, @RequestParam String sendTo, @RequestParam int jopNoNeed,
            @RequestParam int subTrackId , @RequestParam String jopDate ){
        
        PostJobDAO dao = DaoInstance.getInstance().getPostJopDAO();
        return dao.InsertNewJop(companyId, jopCode, jopTitle, jopDesc, experience, closingDate, sendTo, jopNoNeed, subTrackId, jopDate);
    }
}
