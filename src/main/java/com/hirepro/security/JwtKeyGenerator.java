package com.hirepro.security;

import io.jsonwebtoken.security.Keys;
import java.util.Base64;

public class JwtKeyGenerator {
    public static void main(String[] args) {
        String base64Key = Base64.getEncoder().encodeToString(
                Keys.secretKeyFor(io.jsonwebtoken.SignatureAlgorithm.HS256).getEncoded()
        );
        System.out.println("NEW_SECURE_KEY: " + base64Key);
    }
}
