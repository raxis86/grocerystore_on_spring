package Domain.Entities;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.util.UUID;

/**
 * Created by raxis on 27.12.2016.
 * Роль пользователя
 */
public class Role implements Serializable {
    private static final Logger logger = LoggerFactory.getLogger(Role.class);

    private UUID id;        //первичный ключ
    private String name;    //наименование

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
