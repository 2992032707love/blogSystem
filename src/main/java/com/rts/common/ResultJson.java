package com.rts.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@Getter
@AllArgsConstructor
public class ResultJson<T> {
    private Integer code;
    private String message;
    private T data ;
    private long timestamp;

    private ResultJson() {
        this.timestamp = System.currentTimeMillis();
    }

    public static <T> ResultJson<T> success(T data) {
        return custom(ResultCode.RC200.getCode(), ResultCode.RC200.getMessage(), data);
    }

    public static <T> ResultJson<T> fail(Integer code, String massage) {
        return custom(code, massage, null);
    }

    public static <T> ResultJson<T> fail() {
        return custom(ResultCode.RC999.getCode(), ResultCode.RC999.getMessage(), null);
    }

    public static <T> ResultJson<T> fail(String massage) {
        return custom(9999, massage, null);
    }


    public static <T> ResultJson<T> custom(Integer code, String massage, T data) {
        ResultJson<T> result = new ResultJson<>();
        result.setCode(code);
        result.setMessage(massage);
        result.setData(data);
        return result;
    }
}
