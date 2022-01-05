package com.example.EnglishCenterBE;

import com.example.EnglishCenterBE.utils.Constants;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.nio.charset.StandardCharsets;
import java.security.SignatureException;

public class Main {
    public static void main(String[] args) {
        byte[] key = Constants.Secret.TOKEN_KEY.getBytes(StandardCharsets.UTF_8);

        String jwt = Jwts.builder().setIssuer("http://EnglishCenter.com/")
                .setSubject("GV100001")
                .signWith(SignatureAlgorithm.HS256,key)
                .compact();

        System.out.println(jwt);

        System.out.println("Get data back");

        try {
            String subject = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(jwt).
                    getBody().getSubject();
            System.out.println(subject);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
