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

    public static final String sucess = "success";
    public static final String failure = "failure";
    private Object responseData;
    private String status;
    private String error;

    public Object getResponseData() {
        return responseData;
    }

    public void setResponseData(Object responseData) {
        this.responseData = responseData;
    }

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
