package com.personal_admin.controller.model.vo;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

@Data
public class ResultMessage<T> implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private boolean success = Boolean.TRUE;
    private String message;
    private String code;
    private long timestamp = System.currentTimeMillis();
    private T result;
}
