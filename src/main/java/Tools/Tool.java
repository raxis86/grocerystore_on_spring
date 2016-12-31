package Tools;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jws.soap.SOAPBinding;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.math.BigDecimal;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.*;
import java.util.List;
import java.util.Properties;
import java.util.Random;
import java.util.UUID;

/**
 * Created by raxis on 23.12.2016.
 */
public class Tool {
    private static final Logger logger = LoggerFactory.getLogger(Tool.class);

    private static volatile Connection connection;


    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            logger.error("Can't load JDBC driver!",e);
            e.printStackTrace();
        }
    }

    public static synchronized Connection getConnection(){
        if(connection==null){
            Properties properties = new Properties();
            properties.setProperty("characterEncoding","utf-8");
            properties.setProperty("user","root");
            properties.setProperty("password","123");
            properties.setProperty("verifyServerCertificate","false");
            properties.setProperty("useSSL","false");
            properties.setProperty("useJDBCCompliantTimezoneShift","true");
            properties.setProperty("useLegacyDatetimeCode","false");
            properties.setProperty("serverTimezone","UTC");
            try {
                connection=DriverManager.getConnection("jdbc:mysql://localhost:3306/groceriesstore",properties);
            } catch (SQLException e) {
                logger.error("Cant get connection",e);
                e.printStackTrace();
            }
        }
        return connection;
    }

    public static void closeConnection(){
        try {
            connection.close();
        } catch (SQLException e) {
            logger.error("Cant close connection",e);
            e.printStackTrace();
        }
    }

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
        /*DatabaseManager databaseManager = new DatabaseManager();

        try {
            Statement statement = databaseManager.getConnection().createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM USERS");
            while (resultSet.next()){
                System.out.println(resultSet.getString(2));
            }
        } catch (NamingException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }*/
        /*String salt = Tool.generateSalt();
        String pwd = Tool.computeHash(Tool.computeHash("1qaz2wsx") + salt);
        System.out.println(salt);
        System.out.println(pwd);*/
        //System.out.println(generateSalt());
       /* Grocery grocery = new Grocery();
        List<Grocery> groceryList = grocery.getOne();

        for(Grocery g:groceryList){
            System.out.println(g.getId()+" " + g.getParentid() + " " + g.isIscategory() + " " + g.getName() + " " + g.getQuantity() + " " + g.getPrice());
        }

        grocery.setId(UUID.randomUUID());
        grocery.setParentid(new UUID(0L,0L));
        grocery.setIscategory(false);
        grocery.setQuantity(100);
        grocery.setName("banana");
        grocery.setPrice(BigDecimal.valueOf(10.25));
        grocery.insert();

        Grocery grocery1 = groceryList.get(0);
        grocery1.setPrice(BigDecimal.valueOf(89.99));
        grocery1.update();

        groceryList.get(2).delete();*/

        /*User user = new User();
        user.setId(UUID.randomUUID());
        user.setRoleID(UUID.fromString("5fe1ceeb-119f-4437-8f48-6d03949a5f8b"));
        user.setName("user2");
        user.setEmail("user2@mail.ru");
        user.setPassword(computeHash("1qaz2wsx"));
        user.insert();*/

        //User user = new User().getOne(UUID.fromString("51f9d052-323e-49c1-a88e-208530f88d5d"));

        //System.out.println(user.getPassword());
        //user.setPassword(computeHash("yamaha"));
        //System.out.println(user.getPassword().toString());
        //user.update();
        //System.out.println(computeHash("yamaha"));
        /*if(computeHash("1qaz2wsx").equals(user.getPassword())){
            System.out.println(user.getName());
        }*/

    }

}
