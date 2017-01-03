package grocerystore.Domain.Concrete;

import grocerystore.Domain.Entities.Role;
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
public class RoleSqlTest {

    private EmbeddedDatabase db;
    private RoleSql roleHandler;
    private DataSource ds;
    private Role role;
    private List<Role> roleList;

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
        roleHandler = new RoleSql();
        roleHandler.setDs(ds);
    }

    private boolean queryAndGroceryEquals(String query, Role role){
        boolean flag=false;

        try(Connection connection = ds.getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);) {
            while (resultSet.next()){
                if((role.getId().equals(UUID.fromString(resultSet.getString("ID"))))
                 &&(role.getName().equals(resultSet.getString("NAME")))
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
        roleList = roleHandler.getAll();

        int i=0;
        boolean flag=false;

        try(Connection connection = ds.getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM ROLES");) {
            while (resultSet.next()){
                if((roleList.get(i).getId().equals(UUID.fromString(resultSet.getString("ID"))))
                        &&(roleList.get(i).getName().equals(resultSet.getString("NAME")))
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
        role = roleHandler.getOne(UUID.fromString("81446dc5-bd04-4d41-bd72-7405effb4716"));
        assertTrue(queryAndGroceryEquals("SELECT * FROM ROLES WHERE ID='81446dc5-bd04-4d41-bd72-7405effb4716'",role));
    }

    /**
     * Тестирование метода create на соответствие запросу
     * @throws Exception
     */
    @Test
    public void create() throws Exception {
        role = new Role();

        role.setId(UUID.fromString("fe8eb585-d1f0-43b3-a9b8-5747f6b933ee"));
        role.setName("Test");

        roleHandler.create(role);

        assertTrue(queryAndGroceryEquals("SELECT * FROM ROLES WHERE ID='fe8eb585-d1f0-43b3-a9b8-5747f6b933ee'",role));
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
            statement.execute("INSERT INTO ROLES VALUES ('8d098d65-50a8-4ec2-8e35-22219b6c0b00','appTest')");
        } catch (SQLException e) {
        }

        try(Connection connection = ds.getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM ROLES WHERE ID='8d098d65-50a8-4ec2-8e35-22219b6c0b00'");) {
            if(resultSet.next())i++;
        } catch (SQLException e) {
        }


        roleHandler.delete(UUID.fromString("8d098d65-50a8-4ec2-8e35-22219b6c0b00"));

        try(Connection connection = ds.getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM ROLES WHERE ID='8d098d65-50a8-4ec2-8e35-22219b6c0b00'");) {
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
        role = new Role();

        role.setId(UUID.fromString("81446dc5-bd04-4d41-bd72-7405effb4716"));
        role.setName("Test+");

        roleHandler.update(role);

        assertTrue(queryAndGroceryEquals("SELECT * FROM ROLES WHERE ID='81446dc5-bd04-4d41-bd72-7405effb4716'",role));
    }

    @Test
    public void roleByRoleName() throws Exception {
        role = roleHandler.roleByRoleName("customer");
        assertTrue(queryAndGroceryEquals("SELECT * FROM ROLES WHERE NAME='customer'",role));
    }

    @After
    public void tearDown() {
        db.shutdown();
    }

}