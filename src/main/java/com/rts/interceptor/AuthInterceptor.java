package com.rts.interceptor;

import com.auth0.jwt.exceptions.AlgorithmMismatchException;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rts.common.ResultCode;
import com.rts.common.ResultJson;
import com.rts.config.JWTProperties;
import com.rts.constants.PathConstants;
import com.rts.util.JwtUtil;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * @Author: RTS
 * @CreateDateTime: 2024/7/15 19:03
 **/
@Slf4j
@Component
public class AuthInterceptor implements HandlerInterceptor {


    private static final Pattern GET_POSTS_PATTERN = Pattern.compile(PathConstants.GET_POSTS);
    private static final Pattern GET_POST_BY_ID_PATTERN = Pattern.compile(PathConstants.GET_POST_BY_ID.replace("*", "\\d+"));

    @Resource
    private JwtUtil jwtUtil;

    @Resource
    private JWTProperties jwtProperties;

    @Value("${token.name}")
    private String tokenName;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = null;
        String path = null;
        String method = null;
        Map<String ,Object> map = new HashMap<>();
        path = request.getRequestURI();
        method = request.getMethod();
        // 排除特定路径
        if ((GET_POSTS_PATTERN.matcher(path).matches() && "GET".equals(method)) ||
                (GET_POST_BY_ID_PATTERN.matcher(path).matches() && "GET".equals(method))) {
            return true;
        }

        token = request.getHeader(tokenName);
        try{
            jwtUtil.verify(token);
            jwtUtil.updateToken(token);
            map.put(tokenName,token);
            response.setHeader(tokenName,token);
            String username = jwtUtil.getUsernameFromToken(token);
            request.setAttribute(jwtProperties.getClaimKey(), username);
            log.info("结束");
            return true;
        } catch (SignatureVerificationException e) {
            e.printStackTrace();
            map.put("message","非法请求！无效签名");
        } catch (TokenExpiredException e) {
            e.printStackTrace();
            map.put("message","非法请求！令牌过期");
        } catch (AlgorithmMismatchException e) {
            e.printStackTrace();
            map.put("message","非法请求！算法不一致");
        } catch (JWTDecodeException e) {
            e.printStackTrace();
            map.put("message","非法请求！伪造令牌");
        } catch (NullPointerException e) {
            e.printStackTrace();
            map.put("message","非法请求！令牌不存在");
        } catch (Exception e) {
            e.printStackTrace();
            map.put("message","登录已失效，请重新登录 令牌失效");
        }
        String message = (String) map.get("message");

        String json = new ObjectMapper().writeValueAsString(ResultJson.custom(ResultCode.RC401.getCode(), message,null));
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().print(json);
        log.info(json);
        return false;
    }
}
