package com.dwr.task.config;

import com.dwr.task.domain.Task;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;


public class TaskValidator implements Validator {


    @Override
    public boolean supports(Class<?> clazz) {
        return Task.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "description", "field.required");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "isActive", "field.required");
    }
}