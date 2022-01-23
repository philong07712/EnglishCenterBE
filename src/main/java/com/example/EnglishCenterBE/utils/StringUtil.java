package com.example.EnglishCenterBE.utils;

import com.example.EnglishCenterBE.models.Account;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.nio.charset.StandardCharsets;

public class StringUtil {
    public static byte[] key = Constants.Secret.TOKEN_KEY.getBytes(StandardCharsets.UTF_8);
    private static final String ALPHA_NUMERIC_STRING = "0123456789";
    private static final String ALPHABET_CHARACTER = "abcdefghijkmnopqrstuvwxyz0123456789";

    public static String getRandom(int num) {
        int count = num;
        StringBuilder builder = new StringBuilder();
        while (count-- != 0) {
            int character = (int) (Math.random() * ALPHABET_CHARACTER.length());
            builder.append(ALPHABET_CHARACTER.charAt(character));
        }
        return builder.toString();
    }

    public static String generateToken(String username) {
        return Jwts.builder().setIssuer("http://EnglishCenter.com/")
                .setSubject(username)
                .signWith(SignatureAlgorithm.HS256,key)
                .compact();
    }

    public static Account verifyUser(String token) {
        if (token == null || token.isEmpty()) return null;
        Account account = new Account();
        String subject = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).
                getBody().getSubject();
        account.setRole(RoleUtils.getRoleFromUsername(subject));
        System.out.println(RoleUtils.getRoleFromUsername(subject));
        account.setUsername(subject);
        return account;
    }
}
