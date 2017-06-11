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
import java.text.ParseException;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletContext;
import net.minidev.json.JSONObject;
import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import second.DaoInstance;
import second.LoginDAO;
import dto.UserLogin;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author home
 */
@RestController
public class TokenGeneratorController {

    @Autowired
    ServletContext context;

    private static final long accessTokenExpiresInMillis = 1000 * 60 * 60; //hour
    private static final long refreshTokenExpiresInMillis = 60 * 60 * 1000 * 24 * 14; //2weeks

    @RequestMapping(value = "/getToken",
            method = RequestMethod.POST,
            headers = "Accept=application/json")

    public LoginResponse getToken(@RequestBody LoginRequest request) {

        Response response = null;
        LoginResponse loginResponse = new LoginResponse();
        int id;

        LoginDAO loginDao = DaoInstance.getInstance().getLoginDao();
        response = loginDao.getUserId(request.getUserType(), request.getUserName(), request.getPassword());
        loginResponse.setStatus(response.getStatus());
        if (response.getError() != null) {
            loginResponse.setError(response.getError());
        }
        if (response.getStatus().equals("SUCCESS")) {
            id = (int) response.getResponseData();

            String accessKey = context.getInitParameter("accessKey");
            String refreshKey = context.getInitParameter("refreshKey");
            //create access token
            try {
                // Generate 256-bit AES key for HMAC as well as encryption
                long accessExpiryDateInMillis = System.currentTimeMillis() + accessTokenExpiresInMillis; //valid for one week
                long refreshExpiryDateInMillis = System.currentTimeMillis() + refreshTokenExpiresInMillis; //valid for one week

                String accessToken = getAccessToken(accessKey,
                        accessExpiryDateInMillis,
                        String.valueOf(id),
                        String.valueOf(request.getUserType()));
                String refreshToken = getRefreshToken(refreshKey,
                        refreshExpiryDateInMillis,
                        String.valueOf(id),
                        String.valueOf(request.getUserType()));
                
                response = new Response();
                response.setStatus(Response.sucess);
                response.setError(null);
                UserLogin login = new UserLogin();
                login.setToken(accessToken);
                login.setRefreshToken(refreshToken);
                login.setRefreshTokenExpiryDate(refreshExpiryDateInMillis);
                login.setExpiryDate(accessExpiryDateInMillis);
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

    
    //        headers = "Accept=application/json"
    //@RequestParam(value = "refreshToken", required = true)
    @RequestMapping(value = "/renewAccessToken",
            method = RequestMethod.POST)
    public Response renewAccessToken(@RequestBody String refreshToken) {
        refreshToken = refreshToken.substring(1, refreshToken.length()-1);
        String refreshKey = context.getInitParameter("refreshKey");
        String accessKey = context.getInitParameter("accessKey");
        Response response = new Response();
        response.setError(Response.INVALID_REFRESH_TOKEN);
        response.setStatus(Response.failure);
        response.setResponseData(null);

        try {
            //validate refresh token
            JSONObject refreshTokenPayload = security.SecurityManager.
                    validateToken(refreshToken, refreshKey);

            String id = (String) refreshTokenPayload.get("id");
            String type = (String) refreshTokenPayload.get("type");
            long accessExpiryDateInMillis = System.currentTimeMillis() + accessTokenExpiresInMillis; //valid for one week
            String accessToken = getAccessToken(accessKey,
                    accessExpiryDateInMillis, id, type);

            response.setStatus(Response.sucess);
            response.setError(null);
            
            JSONObject responseData = new JSONObject();
            responseData.put("access_token", accessToken);
            responseData.put("token_type", "bearer");
            responseData.put("expiry_date", accessExpiryDateInMillis);
           
//            RenewAccessTokenObject renewAccessTokenObject = new RenewAccessTokenObject();
//            renewAccessTokenObject.setAccessToken(accessToken);
//            renewAccessTokenObject.setToken_type("bearer");
//            renewAccessTokenObject.setExpiry_date(accessExpiryDateInMillis);
            
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
            Logger.getLogger(TokenGeneratorController.class.getName()).log(Level.SEVERE, null, ex);
        }

        return response;

    }

    /*
    @RequestMapping(value = "/renewRefreshToken",
            method = RequestMethod.POST,
            headers = "Accept=application/json")
    public Response renewRefreshToken(@RequestParam(value = "refreshToken", required = true) String refreshToken) {

        return null;

    }
     */
    private String getAccessToken(String accessKey, long expiryDateInMillis, String id, String type)
            throws KeyLengthException, JOSEException {

        byte[] key = Base64.decodeBase64(accessKey);
        JWSSigner signer = new MACSigner(key);

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

    private String getRefreshToken(String refreshKey, long expiryDateInMillis, String id, String type)
            throws KeyLengthException, JOSEException {

        byte[] key = Base64.decodeBase64(refreshKey);
        JWSSigner signer = new MACSigner(key);

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


/*

SecureRandom secureRandom = new SecureRandom();
                    byte[] keys = new byte[32];
                    secureRandom.nextBytes(keys);
                    String ss = Base64.encodeBase64String(keys);
                    System.out.println("tok1 " + ss);

        
                    secureRandom = new SecureRandom();
                    keys = new byte[32];
                    secureRandom.nextBytes(keys);
                    ss = Base64.encodeBase64String(keys);
                    System.out.println("tok2 " + ss);

        
 */
