package ca.ulaval.glo4003.starter.environnement;

import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import java.security.Key;
import java.util.HexFormat;

public class EnvironmentVariableHelper {
    public String getEmailSenderName() {
        return System.getenv("EMAIL_SENDER");
    }

    public String getEmailSenderPassword() {
        return System.getenv("EMAIL_PASSWORD");
    }

    public Key getJWTKey() {
        String jwtKey = System.getenv("JWT_KEY");
        if (jwtKey != null) {
            return Keys.hmacShaKeyFor(HexFormat.of().parseHex(System.getenv("JWT_KEY")));
        }
        return Keys.secretKeyFor(SignatureAlgorithm.HS256);
    }
}
