package com.rts.interceptor;

import com.auth0.jwt.exceptions.AlgorithmMismatchException;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rts.common.ResultCode;
import com.rts.common.ResultJson;
import com.rts.util.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author: RTS
 * @CreateDateTime: 2024/7/15 19:03
 **/
@Slf4j
@Component
public class AuthInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = null;
        Map<String ,Object> map = new HashMap<>();
        token = request.getHeader("token");
        try{
            JwtUtil.verify(token);
            JwtUtil.updateToken(token);
            map.put("token",token);
            response.setHeader("token",token);
            String username = JwtUtil.getUsernameFromToken(token);
            request.setAttribute("username", username);
            return true;
        } catch (SignatureVerificationException e) {
            e.printStackTrace();
            map.put("messgae","非法请求！无效签名");
        } catch (TokenExpiredException e) {
            e.printStackTrace();
            map.put("messgae","非法请求！token过期");
        } catch (AlgorithmMismatchException e) {
            e.printStackTrace();
            map.put("messgae","非法请求！算法不一致");
        } catch (Exception e) {
            e.printStackTrace();
            map.put("messgae","登录已失效，请重新登录 token失效");
        }
        String message = (String) map.get("messgae");

        String json = new ObjectMapper().writeValueAsString(ResultJson.custom(ResultCode.RC401.getCode(), message,null));
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().print(json);
        log.info(json);
        return false;
    }
}
