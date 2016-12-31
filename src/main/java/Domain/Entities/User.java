package Domain.Entities;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.util.UUID;

/**
 * Created by raxis on 27.12.2016.
 * Пользователь
 */
public class User implements Serializable {
    private static final Logger logger = LoggerFactory.getLogger(User.class);

    private UUID id;        //первичный ключ
    private UUID roleID;    //код роли
    private String name;    //имя
    private String email;   //email
    private String password;//пароль
    private String salt;    //соль для хэша
    private String lastName;//фамилия
    private String surName; //отчество
    private String address; //адрес
    private String phone;   //телефон

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getRoleID() {
        return roleID;
    }

    public void setRoleID(UUID roleID) {
        this.roleID = roleID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getSurName() {
        return surName;
    }

    public void setSurName(String surName) {
        this.surName = surName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
