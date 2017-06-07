/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dto;

import java.io.Serializable;

/**
 *
 * @author Mahmoud
 */
public class Response implements Serializable {

    public static final String sucess = "SUCCESS";
    public static final String failure = "FAILURE";

    //authintication error codes
    public static final String INVALID_ACCESS_TOKEN = "INVALID_ACCESS_TOKEN";
    public static final String EXPIRED_ACCESS_TOKEN = "EXPIRED_ACCESS_TOKEN";
    public static final String INVALID_REFRESH_TOKEN = "INVALID_REFRESH_TOKEN";
    public static final String EXPIRED_REFRESH_TOKEN = "EXPIRED_REFRESH_TOKEN";

//    @Serializable
    private Object responseData;
     private String status;
    private String error;

    
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

     public Object getResponseData() {
        return responseData;
    }

    public void setResponseData(Object reponseData) {
        this.responseData = reponseData;
    }
   
    public Response createResponse(Object data) {
        Response response = new Response();
        if (data != null) {
            response.setStatus(Response.sucess);
            response.setResponseData(data);
        } else {
            response.setStatus(Response.failure);
        }
        return response;
    }

}
