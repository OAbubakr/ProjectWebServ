package security;

import com.nimbusds.jose.JOSEException;
import dto.Response;
import java.text.ParseException;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import net.minidev.json.JSONObject;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestHeader;

@Aspect
@Component
public class AuthorizationAspect {

    @Autowired
    private ServletContext context;

    @Autowired
    private HttpServletRequest request;

    @Pointcut("within(@org.springframework.web.bind.annotation.RestController *)")
    public void controller() {
    }

    @Around("controller() && execution(public * com.mycompany.restfulspring.*.*Authorized(..))")
    public Response authorize(ProceedingJoinPoint joinPoint) {

        String accessKey = context.getInitParameter("accessKey");

        Response response = new Response();
        response.setError(Response.INVALID_ACCESS_TOKEN);
        response.setResponseData(null);
        response.setStatus(Response.failure);

        System.out.println("Authorize at " + System.currentTimeMillis());
        Object[] args = joinPoint.getArgs();

        if (args != null) {
            if (args[0] != null) {

                try {
                    String accessToken = request.getHeader("Authorization");
                    System.out.println("token " + accessToken);

                    JSONObject jSONObject = SecurityManager.validateToken(accessToken, accessKey);

                    //replace the first parameter(token) with user id
                    args[0] = (String) jSONObject.get("id");
                    System.out.println("responce to be called");
                    return (Response) joinPoint.proceed(args);

                } catch (ParseException ex) {

                    System.out.println("parse exception");
                    ex.printStackTrace();
                } catch (JOSEException ex) {
                    System.out.println("jose exception");

                    if (ex.getMessage().equals("expiredToken")) {
                        System.out.println("jose time exception");
                        response.setError(Response.EXPIRED_ACCESS_TOKEN);

                    }

                    ex.printStackTrace();
                } catch (Throwable ex) {

                    System.out.println("throwable exception");
                    ex.printStackTrace();
                }

            }
        }

        System.out.println("returning " + response.toString());

        return response;
    }

    /*
    @Around("controller() && execution(* *.*Authorized(..))")
    public Response authorize(ProceedingJoinPoint joinPoint) {

        Response response = new Response();
        response.setError("1");
        response.setResponseData(null);
        response.setStatus("failed");

        System.out.println("Authorize at " + System.currentTimeMillis());
        Object[] args = joinPoint.getArgs();
        if (args != null) {
            if (args[0] != null) {
                String token = (String) args[0];

                try {
                    System.out.println("decryption");
                    JSONObject jSONObject = decryptToken(token);
                    System.out.println("after decryption");

                    //replace the token with user id
                    args[0] = jSONObject.get("id");
                    System.out.println("responce to be called");
                    return (Response) joinPoint.proceed(args);

                } catch (ParseException ex) {

                    System.out.println("parse exception");

                } catch (JOSEException ex) {
                    System.out.println("jose exception");

                    if (ex.getMessage().equals("expiredToken")) {
                        System.out.println("jose time exception");
                        response.setError("2");

                    }
                } catch (Throwable ex) {

                    System.out.println("throwable exception");

                }

            }
        }

        System.out.println("returning " + response.toString());

        return response;
    }
     */
 /*
    public JSONObject decryptToken(String token)
            throws ParseException,
            JOSEException {

        System.out.println("decrypt token  has been called");
        // Serialise to JWE compact form
        // Parse the JWE string
        SecretKey secretKey = SecurityKeyInstance.getEncryptionKey();
        JWEObject jweObject = JWEObject.parse(token);

        // Decrypt with shared key
        byte[] encodedSecret = secretKey.getEncoded();

        DirectDecrypter directDecrypter
                = new DirectDecrypter(encodedSecret);

        jweObject.decrypt(directDecrypter);

        // Extract payload
        SignedJWT signedJWT = jweObject.getPayload().toSignedJWT();

        JSONObject jSONObject = signedJWT.getPayload().toJSONObject();
        if (signedJWT.getJWTClaimsSet().getExpirationTime().
                compareTo(new Date(System.currentTimeMillis())) <= 0) //expired token
        {
            System.out.println(" date error");
            throw new JOSEException("expiredToken");
        }

        return jSONObject;

    }*/
}
