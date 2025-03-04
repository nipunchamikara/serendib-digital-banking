package lk.ac.cmb.ucsc.customer.validator;

public interface Validator<T> {

    boolean validate(T input);
}
