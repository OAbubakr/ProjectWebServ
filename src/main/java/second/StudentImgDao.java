/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package second;

import com.mycompany.restfulspring.StudentImgController;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.ws.rs.core.Response;
import org.springframework.orm.hibernate3.HibernateTemplate;

/**
 *
 * @author Sandra
 */
public class StudentImgDao {

    private HibernateTemplate template;

    public HibernateTemplate getTemplate() {
        return template;
    }

    public void setTemplate(HibernateTemplate template) {
        this.template = template;
    }

    public StudentImgDao() {
    }

    public Response getStudentImg() {
        //absolute path of the file must be given as the application runs from the Apache folder
        File file = new File("E:\\ITI\\Graduation Project\\ProjectWebServices\\ProjectWerServ\\src\\main\\java\\second\\img.jpg");
        byte[] imgData;
        
        try {
            BufferedImage buffImg = ImageIO.read(file);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(buffImg, "jpg", baos);
            imgData = baos.toByteArray();
            return Response.ok(imgData).build();

        } catch (IOException ex) {
            Logger.getLogger(StudentImgController.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

}
