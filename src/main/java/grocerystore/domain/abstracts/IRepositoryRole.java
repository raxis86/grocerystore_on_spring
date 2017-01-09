package grocerystore.domain.abstracts;

import grocerystore.domain.entities.Role;
import grocerystore.domain.exceptions.RoleException;

import java.util.UUID;

/**
 * Created by raxis on 27.12.2016.
 */
public interface IRepositoryRole extends IRepository<Role,UUID> {
    public Role roleByRoleName(String roleName) throws RoleException;
}
