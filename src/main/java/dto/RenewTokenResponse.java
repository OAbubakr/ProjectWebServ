package dto;

import dto.Response;
import java.io.Serializable;

/**
 * Created by home on 6/10/2017.
 */

public class RenewTokenResponse extends Response implements Serializable {
    
    private RenewAccessTokenObject data;
    
    public RenewAccessTokenObject getData() {
        return data;
    }

    public void setData(RenewAccessTokenObject data) {
        this.data = data;
    }

    /*private String status;
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

    */


}
