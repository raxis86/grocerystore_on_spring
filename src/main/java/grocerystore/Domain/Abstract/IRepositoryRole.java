package grocerystore.Domain.Abstract;

import grocerystore.Domain.Entities.Role;
import grocerystore.Domain.Exceptions.RoleException;

import java.util.UUID;

/**
 * Created by raxis on 27.12.2016.
 */
public interface IRepositoryRole extends IRepository<Role,UUID> {
    public Role roleByRoleName(String roleName) throws RoleException;
}
