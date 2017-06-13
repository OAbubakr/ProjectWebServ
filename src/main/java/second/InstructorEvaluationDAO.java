/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package second;

import bean.InstructorEvaluation;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.HibernateTemplate;

/**
 *
 * @author Rana Gamal
 */
public class InstructorEvaluationDAO {

    private HibernateTemplate template;

    public InstructorEvaluationDAO() {
    }

    public HibernateTemplate getTemplate() {
        return template;
    }

    public void setTemplate(HibernateTemplate template) {
        this.template = template;
    }

    public ArrayList<InstructorEvaluation> getInstructorEvaluation(final int instId) {
        return template.execute(new HibernateCallback<ArrayList>() {
            @Override
            public ArrayList doInHibernate(Session sn) throws HibernateException, SQLException {

                Query query = sn.createSQLQuery(" { CALL getInstEval(:instId) }")
                        .setParameter("instId", String.valueOf(instId));

                List<Object[]> list = query.list();
                ArrayList<InstructorEvaluation> instructorEvaluation = new ArrayList<>();

                for (Object[] row : list) {
                    InstructorEvaluation iv = new InstructorEvaluation();
                    iv.setEval(String.valueOf((double) row[0]));
                    iv.setCourseId((int) row[1]);
                    iv.setCourseName((String) row[2]);
                    instructorEvaluation.add(iv);
                }
                //---------------------------------------------------------------------
//                InstructorEvaluation tempObject = new InstructorEvaluation();
//                tempObject.setCourseId(-1);
//
//                ArrayList<ArrayList<InstructorEvaluation>> all = new ArrayList<>();
//                ArrayList<InstructorEvaluation> part;
//                ArrayList<InstructorEvaluation> temp;
//
//                while (instructorEvaluation.size() > 0) {
//                    temp = new ArrayList();
//                    part = new ArrayList<>();
//                    int tempId = instructorEvaluation.get(0).getCourseId();
//
//                    for (int i = 0; i < instructorEvaluation.size(); i++) {
//                        if (instructorEvaluation.get(i).getCourseId() == tempId) {
//                            part.add(instructorEvaluation.get(i));
//                            instructorEvaluation.set(i, tempObject);
//                        }
//                    }
//                    all.add(part);
//                    for (int i = 0; i < instructorEvaluation.size(); i++) {
//                        if (instructorEvaluation.get(i).getCourseId() != -1) {
//                            temp.add(instructorEvaluation.get(i));
//                        }
//                    }
//                    instructorEvaluation = temp;
//                }
//
////                for (int j = 0; j < all.size(); j++) {
////                    for (int k = 0; k < all.get(j).size(); k++) {
////                        System.out.println(all.get(j).get(k).getCourseId());
////                    }
////                    System.out.println("------------------------------------------------------------------");
////                }
//                //----------------------------------------------------------------
//                
//                ArrayList<InstructorEvaluation> averageEval = new ArrayList<>();
//                for(int j=0; j< all.size(); j++){
//                    
//                    int avgEval = 0;
//                    int courseId = all.get(j).get(0).getCourseId();
//                    String courseName = all.get(j).get(0).getCourseName();
//                    int size = all.get(j).size();
//                    
//                    for (int k=0; k< all.get(j).size(); k++){
//                        avgEval = avgEval + Integer.valueOf(all.get(j).get(k).getEval());
//                    }
//                    InstructorEvaluation obj = new InstructorEvaluation();
//                    obj.setCourseId(courseId);
//                    obj.setCourseName(courseName);
//                    obj.setEval(String.valueOf(avgEval/size));
//                    averageEval.add(obj);
//                }
//
//                return averageEval;
                return instructorEvaluation;
            }
        });
    }

}
