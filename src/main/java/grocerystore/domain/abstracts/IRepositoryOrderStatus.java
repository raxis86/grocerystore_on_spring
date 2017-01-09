package grocerystore.domain.abstracts;

import grocerystore.domain.entities.OrderStatus;

import java.util.UUID;

/**
 * Created by raxis on 27.12.2016.
 */
public interface IRepositoryOrderStatus extends IRepository<OrderStatus,UUID> {
}
