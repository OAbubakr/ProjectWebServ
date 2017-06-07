/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package security;

/**
 *
 * @author home
 */
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSVerifier;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.SignedJWT;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.text.ParseException;
import java.util.Date;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import net.minidev.json.JSONObject;

/**
 *
 * @author home
 */
public class SecurityManager {

 //   private static byte[] secret = null;
    private static SecretKey secretKey = null;
    private static SecretKey refreshKey = null;

    private SecurityManager() {

    }
/*
    public static byte[] getKey() {

        if (secret == null) {
            System.out.println("null secret");
            synchronized (SecurityKeyInstance.class) {
                if (secret == null) {
                    SecureRandom secureRandom = new SecureRandom();
                    secret = new byte[32];
                    secureRandom.nextBytes(secret);
                }
            }
        }

        return secret;
    }
*/
    public static SecretKey getAccessKey() {

        if (secretKey == null) {
            synchronized (SecurityManager.class) {
                if (secretKey == null) {
                    try {
                        KeyGenerator keyGen = KeyGenerator.getInstance("AES");
                        keyGen.init(256);
                        secretKey = keyGen.generateKey();
                    } catch (NoSuchAlgorithmException ex) {
                        ex.printStackTrace();
                    }

                }
            }
        }

        return secretKey;
    }

    public static SecretKey getRefreshKey() {

        if (refreshKey == null) {
            synchronized (SecurityManager.class) {
                if (refreshKey == null) {

                    try {
                        KeyGenerator keyGen = KeyGenerator.getInstance("AES");
                        keyGen.init(256);
                        refreshKey = keyGen.generateKey();
                    } catch (NoSuchAlgorithmException ex) {
                        ex.printStackTrace();
                    }

                }
            }
        }
        return refreshKey;
    }

    public static JSONObject validateToken(String token, SecretKey secretKey) throws JOSEException, ParseException {
        
        SignedJWT signedJWT = SignedJWT.parse(token);

        JWSVerifier verifier = new MACVerifier(secretKey);

        if (!signedJWT.verify(verifier)) {
            throw new ParseException("invalidToken", 0);
        }

        if (signedJWT.getJWTClaimsSet().getExpirationTime().
                compareTo(new Date(System.currentTimeMillis())) <= 0) { //expired token

            throw new JOSEException("expiredToken");
        }

        return signedJWT.getPayload().toJSONObject();

    }

    
}
