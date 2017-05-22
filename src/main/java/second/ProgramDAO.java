/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package second;

import bean.StudentBasicData;
import bean.TrackManual;
import java.util.List;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projections;
import org.springframework.orm.hibernate3.HibernateTemplate;

/**
 *
 * @author salma
 */
public class ProgramDAO {

     private HibernateTemplate template;
    public ProgramDAO() {
    }
    
    public HibernateTemplate getTemplate() {
        return template;
    }

    public void setTemplate(HibernateTemplate template) {
        this.template = template;
    }
    
    
    public  List<Object[]> getTracks (){
    
    
     List<Object[]> d = template.findByCriteria(DetachedCriteria.forClass(TrackManual.class, "tm").createAlias("platfromIntake", "pfi")
             .createAlias("pfi.subTrack", "subt").createAlias("courseManual", "cm").createAlias("cm.course", "c")
             .setProjection(Projections.projectionList().add(Projections.property("subt.subtrackName")).add(Projections.property("c.courseName"))));

    System.out.print(d);
    return d;
    }
    
    
}
