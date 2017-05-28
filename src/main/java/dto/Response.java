/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dto;

/**
 *
 * @author Mahmoud
 */
public class Response {
    private Object reponseData;
    private String status;
    private String error;

    public Object getResponseData() {
        return reponseData;
    }

    public void setResponseData(Object reponseData) {
        this.reponseData = reponseData;
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

    
}
