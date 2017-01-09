package grocerystore.domain.concrete;

import grocerystore.domain.entities.User;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.UUID;

import static org.junit.Assert.*;

/**
 * Created by raxis on 03.01.2017.
 */
public class UserSqlTest {

    private EmbeddedDatabase db;
    private UserSql userHandler;
    private DataSource ds;
    private User user;
    private List<User> userList;

    /**
     * Подготавливаем тестовую БД H2 на основе скриптов
     * @throws Exception
     */
    @Before
    public void setUp() throws Exception {
        db = new EmbeddedDatabaseBuilder()
                .setType(EmbeddedDatabaseType.H2)
                .addScript("db/create_db.sql")
                .addScript("db/insert_db.sql")
                .build();

        ds = (DataSource)db;
        userHandler = new UserSql();
        userHandler.setDs(ds);
    }

    private boolean queryAndGroceryEquals(String query, User user){
        boolean flag=false;

        try(Connection connection = ds.getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);) {
            while (resultSet.next()){
                if((user.getId().equals(UUID.fromString(resultSet.getString("ID"))))
                        &&(user.getRoleID().equals(UUID.fromString(resultSet.getString("ROLEID"))))
                        &&(user.getName().equals(resultSet.getString("NAME")))
                        &&(user.getEmail().equals(resultSet.getString("EMAIL")))
                        &&(user.getPassword().equals(resultSet.getString("PASSWORD")))
                        &&(user.getSalt().equals(resultSet.getString("SALT")))
                        &&(user.getLastName().equals(resultSet.getString("LASTNAME")))
                        &&(user.getSurName().equals(resultSet.getString("SURNAME")))
                        &&(user.getAddress().equals(resultSet.getString("ADDRESS")))
                        &&(user.getPhone().equals(resultSet.getString("PHONE")))
                        ){
                    flag=true;
                }
            }
        } catch (SQLException e) {
        }
        return flag;
    }

    /**
     * Тестирование метода getAll на соответствие запросу
     * @throws Exception
     */
    @Test
    public void getAll() throws Exception {
        userList = userHandler.getAll();

        int i=0;
        boolean flag=false;

        try(Connection connection = ds.getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM USERS");) {
            while (resultSet.next()){
                if((userList.get(i).getId().equals(UUID.fromString(resultSet.getString("ID"))))
                        &&(userList.get(i).getRoleID().equals(UUID.fromString(resultSet.getString("ROLEID"))))
                        &&(userList.get(i).getName().equals(resultSet.getString("NAME")))
                        &&(userList.get(i).getEmail().equals(resultSet.getString("EMAIL")))
                        &&(userList.get(i).getPassword().equals(resultSet.getString("PASSWORD")))
                        &&(userList.get(i).getSalt().equals(resultSet.getString("SALT")))
                        &&(userList.get(i).getLastName().equals(resultSet.getString("LASTNAME")))
                        &&(userList.get(i).getSurName().equals(resultSet.getString("SURNAME")))
                        &&(userList.get(i).getAddress().equals(resultSet.getString("ADDRESS")))
                        &&(userList.get(i).getPhone().equals(resultSet.getString("PHONE")))
                        ){
                    flag=true;
                }
                i++;
            }
        } catch (SQLException e) {
        }
        assertTrue(flag);
    }

    /**
     * Тестирование метода getOne на соответствие запросу
     * @throws Exception
     */
    @Test
    public void getOne() throws Exception {
        user = userHandler.getOne(UUID.fromString("839356a3-9a4a-4764-a01e-859ba979ab25"));
        assertTrue(queryAndGroceryEquals("SELECT * FROM USERS WHERE ID='839356a3-9a4a-4764-a01e-859ba979ab25'",user));
    }

    /**
     * Тестирование метода create на соответствие запросу
     * @throws Exception
     */
    @Test
    public void create() throws Exception {
        user = new User();

        user.setId(UUID.fromString("907b09fe-7e54-4b22-8fb9-4a45a449e54e"));
        user.setRoleID(UUID.fromString("5fe1ceeb-119f-4437-8f48-6d03949a5f8b"));
        user.setName("TestNae");
        user.setEmail("test@test.ru");
        user.setPassword("password");
        user.setSalt("salt");
        user.setLastName("LastName");
        user.setSurName("Surname");
        user.setAddress("testAdd");
        user.setPhone("123");

        userHandler.create(user);

        assertTrue(queryAndGroceryEquals("SELECT * FROM USERS WHERE ID='907b09fe-7e54-4b22-8fb9-4a45a449e54e'",user));
    }

    /**
     * Тестирование метода delete на соответствие запросу
     * @throws Exception
     */
    @Test
    public void delete() throws Exception {
        int i=0;

        try(Connection connection = ds.getConnection();
            Statement statement = connection.createStatement();) {
            statement.execute("INSERT INTO USERS VALUES ('452025dd-1f1e-4a4f-9964-78cd53dc3ee3','5fe1ceeb-119f-4437-8f48-6d03949a5f8b','name','email','pass','salt','ln','sn','add','ph')");
        } catch (SQLException e) {
        }

        try(Connection connection = ds.getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM USERS WHERE ID='452025dd-1f1e-4a4f-9964-78cd53dc3ee3'");) {
            if(resultSet.next())i++;
        } catch (SQLException e) {
        }


        userHandler.delete(UUID.fromString("452025dd-1f1e-4a4f-9964-78cd53dc3ee3"));

        try(Connection connection = ds.getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM USERS WHERE ID='452025dd-1f1e-4a4f-9964-78cd53dc3ee3'");) {
            if(resultSet.next())i--;
        } catch (SQLException e) {
        }

        assertTrue(i==1);
    }

    /**
     * Тестирование метода update на соответствие запросу
     * @throws Exception
     */
    @Test
    public void update() throws Exception {
        user = new User();

        user.setId(UUID.fromString("839356a3-9a4a-4764-a01e-859ba979ab25"));
        user.setRoleID(UUID.fromString("5fe1ceeb-119f-4437-8f48-6d03949a5f8b"));
        user.setName("TestNae");
        user.setEmail("test@test.ru");
        user.setPassword("password");
        user.setSalt("salt");
        user.setLastName("LastName");
        user.setSurName("Surname");
        user.setAddress("testAdd");
        user.setPhone("123");

        userHandler.update(user);

        assertTrue(queryAndGroceryEquals("SELECT * FROM USERS WHERE ID='839356a3-9a4a-4764-a01e-859ba979ab25'",user));
    }

    /**
     * Тестирование метода getOne(String email, String passwordHash) на соответствие запросу
     * @throws Exception
     */
    @Test
    public void getOne1() throws Exception {
        user = userHandler.getOne("user@mail.ru","4D0CF81F91D55E4D666D1A6698877BE77E903A3E");
        assertTrue(queryAndGroceryEquals("SELECT * FROM USERS WHERE ID='839356a3-9a4a-4764-a01e-859ba979ab25'",user));
    }

    /**
     * Тестирование метода getOneByEmail(String email) на соответствие запросу
     * @throws Exception
     */
    @Test
    public void getOneByEmail() throws Exception {
        user = userHandler.getOneByEmail("user@mail.ru");
        assertTrue(queryAndGroceryEquals("SELECT * FROM USERS WHERE ID='839356a3-9a4a-4764-a01e-859ba979ab25'",user));
    }

    @After
    public void tearDown() {
        db.shutdown();
    }

}