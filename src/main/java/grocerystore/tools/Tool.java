package grocerystore.tools;

import javafx.util.converter.BigDecimalStringConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.*;
import java.util.Properties;
import java.util.Random;

/**
 * Created by raxis on 23.12.2016.
 */
public class Tool {
    private static final Logger logger = LoggerFactory.getLogger(Tool.class);

    public static String computeHash(String password){
        MessageDigest messageDigest=null;
        byte[] b=null;
        String str="";
        try {
            messageDigest = MessageDigest.getInstance("SHA-1");
            messageDigest.reset();
            messageDigest.update(password.getBytes());

            b = messageDigest.digest();
            StringBuffer sb = new StringBuffer(b.length * 2);
            for (int i = 0; i < b.length; i++){
                int v = b[i] & 0xff;
                if (v < 16) {
                    sb.append('0');
                }
                sb.append(Integer.toHexString(v));
            }

            str=sb.toString().toUpperCase();

        } catch (NoSuchAlgorithmException e) {
            logger.error("Cant hash generate!",e);
            e.printStackTrace();
        }

        return str;
    }

    public static String generateSalt(){
        String salt="";
        Random random = new Random();
        int length = random.nextInt(17)+10;
        for(int i=0; i<length; i++){
            salt+=(char)(random.nextInt(93)+33);
        }
        return salt;
    }

    public static void main(String[] args) {
    }

}
