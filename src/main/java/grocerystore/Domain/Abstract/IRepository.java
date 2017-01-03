package grocerystore.Domain.Abstract;

import grocerystore.Domain.Exceptions.DAOException;

import javax.sql.DataSource;
import java.util.List;

/**
 * Created by raxis on 27.12.2016.
 * DAO
 */
public interface IRepository<T,K> {
    public List<T> getAll() throws DAOException;
    public T getOne(K id) throws DAOException;
    public boolean create(T entity) throws DAOException;
    public boolean delete(K id) throws DAOException;
    public boolean update(T entity) throws DAOException;
}
