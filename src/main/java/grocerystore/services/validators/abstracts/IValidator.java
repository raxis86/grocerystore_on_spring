package grocerystore.services.validators.abstracts;

import grocerystore.services.exceptions.BusinessLogicException;

/**
 * Created by raxis on 06.01.2017.
 */
public interface IValidator {
    public boolean validate(String field) throws BusinessLogicException;
}
