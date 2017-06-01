/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package second;


import bean.EventId;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.HibernateTemplate;

/**
 *
 * @author Rana Gamal
 */
public class EventDAO {
    
    private HibernateTemplate template;

    public EventDAO() {
    }

    public HibernateTemplate getTemplate() {
        return template;
    }

    public void setTemplate(HibernateTemplate template) {
        this.template = template;
    }

    
    public ArrayList<EventId> getEvents() {
        return template.execute(new HibernateCallback<ArrayList>() {
            @Override
            public ArrayList doInHibernate(Session sn) throws HibernateException, SQLException {
            
                Query query = sn.createSQLQuery(" { CALL GetEvents() }");
                List<Object[]> list = query.list();
                
                ArrayList<EventId> events = new ArrayList<>();

                for (Object[] row : list) {
                    EventId ev = new EventId();
                    ev.setEventId((int) row[0]);
                    ev.setTitle((String) row[1]);
                    ev.setDescription((String) row[2]);
                    ev.setEventStart((Date) row[3]);
                    ev.setEventEnd((Date) row[4]);

                    events.add(ev);
                }
                return events;
            }
        });
    }  
}
