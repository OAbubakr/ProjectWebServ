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
public class LoginResponse {
  private UserLogin data;
    private String statusLogin;
    private String errorLogin;

    public UserLogin getData() {
        return data;
    }

    public void setData(UserLogin data) {
        this.data = data;
    }


    public String getStatusLogin() {
        return statusLogin;
    }


    public void setStatusLogin(String statusLogin) {
        this.statusLogin = statusLogin;
    }


    public String getErrorLogin() {
        return errorLogin;
    }


    public void setErrorLogin(String errorLogin) {
        this.errorLogin = errorLogin;
    }

    
}
