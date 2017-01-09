package grocerystore.Domain.Concrete;

import grocerystore.Tools.DatabaseManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

/**
 * Created by raxis on 03.01.2017.
 */
public class SQLImplementation {
    private static DataSource ds = DatabaseManager.getDataSource();

    public DataSource getDs() {
        return ds;
    }

    public void setDs(DataSource ds) {
        SQLImplementation.ds = ds;
    }
}
