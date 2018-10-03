package tw.ntu.lib.model;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class Validate {
    public static List<String> validate(Reader reader, int i) {
        List<String> errors = new ArrayList();

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();

        Set<ConstraintViolation<Reader>> cvs = validator.validate(reader);

        if (!cvs.isEmpty()) {

            for (ConstraintViolation<Reader> cv : cvs) {

                String err = "Row:" + i + "--" + String.valueOf(cv.getPropertyPath()) + ":" + cv.getMessage();
                errors.add(err);
            }
        }

        return errors;
    }
}
