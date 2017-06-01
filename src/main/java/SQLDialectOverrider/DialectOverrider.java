/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SQLDialectOverrider;



import java.sql.Types;
import org.hibernate.Hibernate;
import org.hibernate.dialect.SQLServerDialect;

/**
 *
 * @author Sandra
 */
public class DialectOverrider extends SQLServerDialect{

    public DialectOverrider() {
        super();
        registerHibernateType(Types.NVARCHAR, Hibernate.STRING.getName());
        registerHibernateType(Types.NCHAR, Hibernate.STRING.getName());
        registerHibernateType(Types.LONGNVARCHAR, Hibernate.STRING.getName());
    }
    
    
    
}
