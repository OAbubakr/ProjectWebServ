package com.mycompany.restfulspring;

import bean.FileInfo;
import bean.ReturnMessage;
import com.google.gson.Gson;
import java.io.File;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import second.DaoInstance;
import second.SaveImageDao;

@RestController
public class SaveImageController {

    @Autowired
    ServletContext context;

    /**
     *
     * @param inputFile
     * @param id
     * @return
     */
    @RequestMapping(value = "{id}/fileupload", headers = ("content-type=multipart/*"), method = RequestMethod.POST)
    public String uploadAuthorized(Integer myid, HttpServletRequest request,
            HttpServletResponse response,@RequestParam("file") MultipartFile inputFile, @PathVariable int id) { //,@RequestParam("id") int id
        System.out.println("id is "+ id);
        Gson gson = new Gson();
        ReturnMessage returnMessage = new ReturnMessage();
        FileInfo fileInfo = new FileInfo();
        HttpHeaders headers = new HttpHeaders();
        if (!inputFile.isEmpty()) {
            try {
                String originalFilename = inputFile.getOriginalFilename();
                File destinationFile = new File(request.getServletContext().getRealPath("/WEB-INF/uploaded")
                        + File.separator + originalFilename);
                inputFile.transferTo(destinationFile);
                fileInfo.setFileName(destinationFile.getPath());
                System.out.println(destinationFile.getPath());

                fileInfo.setFileSize(inputFile.getSize());
                headers.add("File Uploaded Successfully - ", id+originalFilename);
                SaveImageDao saveImageDao = DaoInstance.getInstance().getSaveImageDao();
                String s = saveImageDao.insertImage(5700, destinationFile.getPath());

                returnMessage.setMessage(s);
                return gson.toJson(returnMessage);
            } catch (Exception e) {
                return "" + e.getMessage();
            }
        }
        return gson.toJson(returnMessage);
    }
}
