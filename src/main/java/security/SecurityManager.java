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
import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.util.Date;
import net.minidev.json.JSONObject;
import org.apache.commons.codec.binary.Base64;

/**
 *
 * @author home
 */
public class SecurityManager {

    private SecurityManager() {

    }

   
    public static JSONObject validateToken(String token, String stringKey) throws JOSEException, ParseException {

       byte[] key = Base64.decodeBase64(stringKey);        
        SignedJWT signedJWT = SignedJWT.parse(token);

        JWSVerifier verifier = new MACVerifier(key);

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


/*

                secureRandom = new SecureRandom();
                    keys = new byte[32];
                    secureRandom.nextBytes(keys);
                    s = Base64.encode(keys);
                    System.out.println("tok1 " + s);

*/
