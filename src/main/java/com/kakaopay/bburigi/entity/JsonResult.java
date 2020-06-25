package com.kakaopay.bburigi.entity;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;

@Data
public class JsonResult {
    private boolean success;
    private String message;
    @JsonSerialize(typing = JsonSerialize.Typing.DYNAMIC)
    private Object data;

    public static JsonResult success(Object data) {
        JsonResult result = new JsonResult();
        result.setSuccess(true);
        result.setData(data);
        return result;
    }

    public static JsonResult success() {
        JsonResult result = new JsonResult();
        result.setSuccess(true);
        return result;
    }

    public static JsonResult failure(String message) {
        JsonResult result = new JsonResult();
        result.setSuccess(false);
        result.setMessage(message);
        return result;
    }
}
