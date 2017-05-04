/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package second;

import beans.Users;
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

 

 

    public void insertCustomer(Users cus) {

     template.save(cus);
 

    }

    public Users getCustomerBuyId(int id) {

       Users cus = (Users) template.get(Users.class, new BigDecimal(id));
       return cus;
    }

    
}
