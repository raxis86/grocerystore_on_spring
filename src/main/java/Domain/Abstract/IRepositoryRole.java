package Domain.Abstract;

import Domain.Entities.Role;
import Domain.Exceptions.RoleException;

import java.util.UUID;

/**
 * Created by raxis on 27.12.2016.
 */
public interface IRepositoryRole extends IRepository<Role,UUID> {
    public Role roleByRoleName(String roleName) throws RoleException;
}
