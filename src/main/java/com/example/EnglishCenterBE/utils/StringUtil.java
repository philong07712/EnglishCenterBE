package com.example.EnglishCenterBE.utils;

import com.example.EnglishCenterBE.models.Account;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.nio.charset.StandardCharsets;

public class StringUtil {
    public static byte[] key = Constants.Secret.TOKEN_KEY.getBytes(StandardCharsets.UTF_8);

    public static String generateToken(String username) {
        return Jwts.builder().setIssuer("http://EnglishCenter.com/")
                .setSubject(username)
                .signWith(SignatureAlgorithm.HS256,key)
                .compact();
    }

    public static Account verifyUser(String token) {
        Account account = new Account();
        String subject = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).
                getBody().getSubject();
        account.setRole(RoleUtils.getRoleFromUsername(subject));
        account.setUsername(subject);
        return account;
    }
}
