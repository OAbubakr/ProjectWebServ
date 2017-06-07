/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.restfulspring;

import com.nimbusds.jose.EncryptionMethod;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWEAlgorithm;
import com.nimbusds.jose.JWEHeader;
import com.nimbusds.jose.JWEObject;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.JWSSigner;
import com.nimbusds.jose.KeyLengthException;
import com.nimbusds.jose.Payload;
import com.nimbusds.jose.crypto.DirectEncrypter;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import dto.LoginRequest;
import dto.LoginResponse;
import dto.Response;
import dto.UserLogin;
import java.util.Date;
import javax.crypto.SecretKey;
import net.minidev.json.JSONObject;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import second.DaoInstance;
import second.LoginDAO;
import security_aspect.SecurityKeyInstance;

/**
 *
 * @author home
 */
@RestController
public class TokenGeneratorController {

    @RequestMapping(value = "/getToken",
            method = RequestMethod.POST,
//            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE,
            headers = "Accept=application/json")

    public LoginResponse getToken(@RequestBody LoginRequest request) {

        Response response = null;
        LoginResponse loginResponse = new LoginResponse();
        int id;

        System.out.println("request " + request.getUserName() + " "+ request.getUserType() + " "+ request.getPassword());
        
        //login logic
        LoginDAO loginDao = DaoInstance.getInstance().getLoginDao();
        response = loginDao.getUserId(request.getUserType(), request.getUserName(), request.getPassword());
        loginResponse.setStatusLogin(response.getStatus());
        if(response.getError()!=null)
            loginResponse.setErrorLogin(response.getError());
        if (response.getStatus().equals("SUCCESS")) {
            id = (int) response.getResponseData();
            System.out.println("id " + id);
            //create access token
            try {

                // Generate 256-bit AES key for HMAC as well as encryption
                SecretKey secretKey = SecurityKeyInstance.getEncryptionKey();
                // Create HMAC signer
                JWSSigner signer = new MACSigner(secretKey.getEncoded());

                // Prepare JWT with claims set 7 * 24 * 60 * 60 * 1000
                long expiryDateInMillis = System.currentTimeMillis() + (7*24*60*60000); //valid for one week

                JWTClaimsSet claimsSet = new JWTClaimsSet.Builder()
                        .expirationTime(new Date(expiryDateInMillis))
                        .claim("id", String.valueOf(id))
                        .claim("type", String.valueOf(request.getUserType()))
                        .build();

                SignedJWT signedJWT = new SignedJWT(
                        new JWSHeader(JWSAlgorithm.HS256), claimsSet);

                // Apply the HMAC
                signedJWT.sign(signer);
                String accessToken = signedJWT.serialize();

                response = new Response();
                response.setStatus("success");
                response.setError(null);
//                JSONObject responseData = new JSONObject();
                UserLogin login = new UserLogin();
                login.setToken(accessToken);
                login.setExpiryDate(new Long(expiryDateInMillis).toString());
                login.setTokenType("bearer");
//                responseData.put("access_token", accessToken);
//                responseData.put("token_type", "bearer");
//                responseData.put("expiry_date", expiryDateInMillis);
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
