package com.cly.zmyy.exception;

import lombok.Data;

@Data
public class BusinessException  extends RuntimeException {
    private static final long serialVersionUID = 2332608236621015980L;

    public BusinessException(String message) {
        super(message);
    }

    public BusinessException(String message, Throwable cause) {
        super(message, cause);
    }
}
