/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package second;

import bean.Branch;

import java.math.BigDecimal;
import java.util.List;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import org.springframework.orm.hibernate3.HibernateTemplate;

/**
 *
 * @author omari
 */
public class StudentDAO {

    private HibernateTemplate template;

    public StudentDAO() {
    }

    public HibernateTemplate getTemplate() {
        return template;
    }

    public void setTemplate(HibernateTemplate template) {
        this.template = template;
    }

//    public void insertAnswer(Answer cus) {
//
//     template.save(cus);
// 
//
//    }
//    public StudentBasicData getStudentById(int id) {
//
//        List<Object[]> d = template.findByCriteria(DetachedCriteria.forClass(StudentBasicData.class, "stu").createAlias("platfromIntake", "pfi").createAlias("pfi.subTrack", "subt")
//                .setProjection(Projections.projectionList().add(Projections.property("stu.studentId")).add(Projections.property("stu.englishname")).add(Projections.property("subt.subtrackId"))));
//
//        StudentBasicData cus = new StudentBasicData();
//        cus.setEnglishname((String) d.get(0)[1]);
//        cus.setIdno((int) d.get(0)[0]);
//        Branch b = new Branch();
//        b.setBranchId((int) d.get(0)[0]);
//
//        PlatfromIntake p = new PlatfromIntake();
//        p.setBranch(b);
//        cus.setPlatfromIntake(p);
//
//        return cus;
//    }

}
