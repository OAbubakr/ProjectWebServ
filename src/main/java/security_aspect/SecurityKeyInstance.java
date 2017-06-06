/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package security_aspect;

/**
 *
 * @author home
 */
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

/**
 *
 * @author home
 */
public class SecurityKeyInstance {

 //   private static byte[] secret = null;
    private static SecretKey secretKey = null;

    private SecurityKeyInstance() {

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
    public static SecretKey getEncryptionKey() {

        if (secretKey == null) {
            synchronized (SecurityKeyInstance.class) {
                if (secretKey == null) {
        System.out.println("null secret");

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
                System.out.println("done secret");

        return secretKey;
    }

}
