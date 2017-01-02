package grocerystore.Domain.Abstract;

import grocerystore.Domain.Entities.Order;
import grocerystore.Domain.Exceptions.OrderException;

import java.util.List;
import java.util.UUID;

/**
 * Created by raxis on 27.12.2016.
 */
/*public interface IRepositoryOrder<T,K> extends IRepository<T,K> {
    public List<T> getByUserId(K userid);
}*/

public interface IRepositoryOrder extends IRepository<Order,UUID> {
    public List<Order> getByUserId(UUID userid) throws OrderException;
}
