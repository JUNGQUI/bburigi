package com.kakaopay.bburigi.entity;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Getter;

@Getter
public class JsonResult {
    private boolean success;
    private String message;
    @JsonSerialize(typing = JsonSerialize.Typing.DYNAMIC)
    private Object data;

    public static JsonResult success(Object data) {
        return new JsonResult(true, data, null);
    }

    public static JsonResult success() {
        return new JsonResult(true, null, null);
    }

    public static JsonResult failure(String message) {
        return new JsonResult(false, null, message);
    }

    public JsonResult () {}
    public JsonResult (boolean success, Object data, String message) {
        this.success = success;
        this.data = data;
        this.message = message;
    }
}
