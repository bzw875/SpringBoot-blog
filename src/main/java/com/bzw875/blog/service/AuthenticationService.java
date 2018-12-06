package com.bzw875.blog.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;

@Service
public class AuthenticationService {
    public String getToken(String username, String paddword) {
        String token = "";
        try {
            token = JWT.create()
                    .withAudience(username) // 将 username 保存到 token 里面
                    .sign(Algorithm.HMAC256(paddword));   // 以 password 作为 token 的密钥
        } catch (UnsupportedEncodingException ignore) {
        }
        return token;
    }
}