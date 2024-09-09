package com.eduardosdl.financecontrol.exceptions;

import java.util.List;

public class ControllerErrorResponse {
    private List<String> errors;

    public ControllerErrorResponse(List<String> errors) {
        this.errors = errors;
    }

    public List<String> getErrors() {
        return errors;
    }

    public void setErrors(List<String> errors) {
        this.errors = errors;
    }
}
