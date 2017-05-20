/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package second;


import beans.Answer;
import java.math.BigDecimal;

import org.springframework.orm.hibernate3.HibernateTemplate;

/**
 *
 * @author omari
 */
public class DAO {
   private HibernateTemplate template;

    public DAO() {
    }

    public HibernateTemplate getTemplate() {
        return template;
    }

    public void setTemplate(HibernateTemplate template) {
        this.template = template;
    }

 

 

    public void insertAnswer(Answer cus) {

     template.save(cus);
 

    }

    public Answer getCustomerBuyId(int id) {

       Answer cus = (Answer) template.get(Answer.class, id);
       return cus;
    }

    
}
