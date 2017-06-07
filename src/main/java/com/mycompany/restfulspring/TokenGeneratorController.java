/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.restfulspring;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.JWSSigner;
import com.nimbusds.jose.KeyLengthException;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import dto.LoginRequest;
import dto.LoginResponse;
import dto.Response;
import dto.UserLogin;
import java.text.ParseException;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.SecretKey;
import javax.servlet.ServletContext;
import net.minidev.json.JSONObject;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import second.DaoInstance;
import second.LoginDAO;

/**
 *
 * @author home
 */
@RestController
public class TokenGeneratorController {
    
    @RequestMapping(value = "/getToken",
            method = RequestMethod.POST,
            headers = "Accept=application/json")

    public LoginResponse getToken(@RequestBody LoginRequest request) {

        Response response = null;
        LoginResponse loginResponse = new LoginResponse();
        int id;

        System.out.println("request " + request.getUserName() + " " + request.getUserType() + " " + request.getPassword());

        //login logic
        LoginDAO loginDao = DaoInstance.getInstance().getLoginDao();
        response = loginDao.getUserId(request.getUserType(), request.getUserName(), request.getPassword());
        loginResponse.setStatusLogin(response.getStatus());
        if (response.getError() != null) {
            loginResponse.setErrorLogin(response.getError());
        }
        if (response.getStatus().equals("SUCCESS")) {
            id = (int) response.getResponseData();
            System.out.println("id " + id);
            try {
                long accessExpiryDateInMillis = System.currentTimeMillis() + (24 * 60 * 60000);
                long refreshExpiryDateInMillis = System.currentTimeMillis() + (60000);
                String accessToken = getAccessToken(security.SecurityManager.getAccessKey(),
                        accessExpiryDateInMillis,
                        String.valueOf(id),
                        String.valueOf(request.getUserType()));
                String refreshToken = getRefreshToken(security.SecurityManager.getRefreshKey(),
                        refreshExpiryDateInMillis,
                        String.valueOf(id),
                        String.valueOf(request.getUserType()));
                response = new Response();
                response.setStatus(Response.sucess);
                response.setError(null);
                UserLogin login = new UserLogin();
                login.setToken(accessToken);
                login.setExpiryDate(new Long(accessExpiryDateInMillis).toString());
                login.setTokenType("bearer");
                response.setResponseData(login);
                loginResponse.setData(login);

            } catch (KeyLengthException ex) {
                ex.printStackTrace();
            } catch (JOSEException ex) {
                ex.printStackTrace();
            }
        }
        return loginResponse;

    }

    @RequestMapping(value = "/renewAccessToken",
            method = RequestMethod.POST,
            headers = "Accept=application/json")
    public Response renewAccessToken(@RequestParam(value = "refreshToken", required = true) String refreshToken) {

        Response response = new Response();
        response.setError(Response.INVALID_REFRESH_TOKEN);
        response.setStatus(Response.failure);
        response.setResponseData(null);

        try {
            //validate refresh token
            SecretKey secretKey = security.SecurityManager.getRefreshKey();
            JSONObject refreshTokenPayload = security.SecurityManager.validateToken(refreshToken, secretKey);

            String id = (String) refreshTokenPayload.get("id");
            String type = (String) refreshTokenPayload.get("type");
            long accessExpiryDateInMillis = System.currentTimeMillis() + (60000); //valid for one week
            String accessToken = getAccessToken(security.SecurityManager.getAccessKey(),
                    accessExpiryDateInMillis, id, type);

            response.setStatus(Response.sucess);
            response.setError(null);
            JSONObject responseData = new JSONObject();
            responseData.put("access_token", accessToken);
            responseData.put("token_type", "bearer");
            responseData.put("expiry_date", accessExpiryDateInMillis);
            response.setResponseData(responseData);

        } catch (KeyLengthException ex) {
            ex.printStackTrace();

        } catch (JOSEException ex) {
            ex.getMessage();
            if (ex.getMessage().equals("expiredToken")) {
                System.out.println("jose time exception");
                response.setError(Response.EXPIRED_REFRESH_TOKEN);
            }

            ex.printStackTrace();
        } catch (ParseException ex) {
            ex.printStackTrace();

        }

        return response;

    }
    
    public Response renewRefreshToken(@RequestParam(value = "refreshToken", required = true) String refreshToken) {
        
        return null;

    }
    
    private String getAccessToken(SecretKey secretKey, long expiryDateInMillis, String id, String type)
            throws KeyLengthException, JOSEException {

        JWSSigner signer = new MACSigner(secretKey.getEncoded());

        JWTClaimsSet claimsSet = new JWTClaimsSet.Builder()
                .expirationTime(new Date(expiryDateInMillis))
                .claim("id", id)
                .claim("type", type)
                .build();

        SignedJWT signedJWT = new SignedJWT(
                new JWSHeader(JWSAlgorithm.HS256), claimsSet);

        // Apply the HMAC
        signedJWT.sign(signer);
        return signedJWT.serialize();

    }

    private String getRefreshToken(SecretKey refreshKey, long expiryDateInMillis, String id, String type)
            throws KeyLengthException, JOSEException {

        JWSSigner signer = new MACSigner(refreshKey.getEncoded());

        JWTClaimsSet claimsSet = new JWTClaimsSet.Builder()
                .expirationTime(new Date(expiryDateInMillis))
                .claim("id", id)
                .claim("type", type)
                .build();

        SignedJWT signedJWT = new SignedJWT(
                new JWSHeader(JWSAlgorithm.HS256), claimsSet);

        // Apply the HMAC
        signedJWT.sign(signer);
        return signedJWT.serialize();

    }

    /*
    @RequestMapping(value = "/getTokenEncrypted", method = RequestMethod.GET, headers = "Accept=application/json")
    public String getTokenEncrypted(
            @RequestParam(value = "userName", required = true) String userName,
            @RequestParam(value = "password", required = true) String password,
            @RequestParam(value = "type", required = true) int type) {

        //login logic
        int id = 1;
        String s = null;

        try {

            // Generate 256-bit AES key for HMAC as well as encryption
            SecretKey secretKey = SecurityKeyInstance.getEncryptionKey();
            // Create HMAC signer
            JWSSigner signer = new MACSigner(secretKey.getEncoded());

            // Prepare JWT with claims set 7 * 24 * 60 * 60 * 1000
            long expiryDateInMillis = System.currentTimeMillis() + (30000); //valid for one week

            JWTClaimsSet claimsSet = new JWTClaimsSet.Builder()
                    .expirationTime(new Date(expiryDateInMillis))
                    .audience(String.valueOf(type))
                    .claim("id", String.valueOf(id))
                    .build();

            SignedJWT signedJWT = new SignedJWT(
                    new JWSHeader(JWSAlgorithm.HS256), claimsSet);

            // Apply the HMAC
            signedJWT.sign(signer);

            // Create JWE object with signed JWT as payload
            JWEObject jweObject = new JWEObject(
                    new JWEHeader.Builder(JWEAlgorithm.DIR, EncryptionMethod.A256GCM)
                            .contentType("JWT") // required to signal nested JWT
                            .build(),
                    new Payload(signedJWT));

            // Perform encryption
            jweObject.encrypt(new DirectEncrypter(secretKey.getEncoded()));

            // Serialise to JWE compact form
            s = jweObject.serialize();

        } catch (KeyLengthException ex) {
            ex.printStackTrace();
        } catch (JOSEException ex) {
            ex.printStackTrace();
        }
        return s;
    }
     */
 /*
    @RequestMapping(value = "/getToken", 
            method = RequestMethod.POST, 
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public String getToken(
            @RequestParam(value = "userName", required = true) String userName,
            @RequestParam(value = "password", required = true) String password,
            @RequestParam(value = "type", required = true) int type) {
       
        return "token";
    }*/

 /*
    @RequestMapping(value = "/refreshToken", method = RequestMethod.GET, headers = "Accept=application/json")
    public String refreshToken(
            @RequestParam(value = "refreshToken", required = true) String refreshToken) {
        
        //login logic
        int id = 1;
        String s = null;

        try {

            // Generate 256-bit AES key for HMAC as well as encryption
            SecretKey secretKey = SecurityKeyInstance.getEncryptionKey();
            // Create HMAC signer
            JWSSigner signer = new MACSigner(secretKey.getEncoded());

            // Prepare JWT with claims set 7 * 24 * 60 * 60 * 1000
            long expiryDateInMillis = System.currentTimeMillis() + (30000); //valid for one week
            
            JWTClaimsSet claimsSet = new JWTClaimsSet.Builder()
                    .expirationTime(new Date(expiryDateInMillis))
                    .audience(String.valueOf(type))
                    .claim("id", String.valueOf(id))
                    .build();

            SignedJWT signedJWT = new SignedJWT(
                    new JWSHeader(JWSAlgorithm.HS256), claimsSet);

            // Apply the HMAC
            signedJWT.sign(signer);

            // Create JWE object with signed JWT as payload
            JWEObject jweObject = new JWEObject(
                    new JWEHeader.Builder(JWEAlgorithm.DIR, EncryptionMethod.A256GCM)
                            .contentType("JWT") // required to signal nested JWT
                            .build(),
                    new Payload(signedJWT));

            // Perform encryption
            jweObject.encrypt(new DirectEncrypter(secretKey.getEncoded()));

            // Serialise to JWE compact form
            s = jweObject.serialize();

        } catch (KeyLengthException ex) {
            ex.printStackTrace();
        } catch (JOSEException ex) {
            ex.printStackTrace();
        } 
        return s;
    }
     */
}
