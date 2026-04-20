package com.project.api.v1.service;

import com.project.api.v1.model.dto.TokenPair;
import io.quarkus.logging.Log;
import io.smallrye.jwt.build.Jwt;
import io.smallrye.jwt.auth.principal.JWTParser;
import jakarta.enterprise.context.ApplicationScoped;
import org.eclipse.microprofile.config.ConfigProvider;
import org.eclipse.microprofile.jwt.JsonWebToken;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Duration;

@ApplicationScoped
public class JwtService {

    @Inject
    JWTParser jwtParser;

    private static final long ACCESS_EXPIRY = 900;
    private static final long REFRESH_EXPIRY = 604800;

    public TokenPair generateAccessToken(String userId, String refreshToken) {
        String storedRefreshToken = "x";  //fetch from DB
        TokenPair tokenPair = null;
        if(storedRefreshToken.equals(refreshToken)) {
            tokenPair = generateTokenPair(userId);
        }
        return tokenPair;
    }

    public TokenPair generateTokenPair(String userId) {
        try {
            //Generate Access token
            String accessToken = Jwt.claims()
                    .issuer("boolean-broker")
                    .subject(userId)
                    .expiresIn(Duration.ofSeconds(ACCESS_EXPIRY))
                    .sign();

            //Generate Refresh token
            String refreshToken = Jwt.claims()
                    .issuer("boolean-broker")
                    .subject(userId)
                    .claim("type", "refresh")
                    .expiresIn(Duration.ofSeconds(REFRESH_EXPIRY))
                    .sign();

            return new TokenPair(
                    accessToken,
                    refreshToken,
                    ACCESS_EXPIRY,
                    REFRESH_EXPIRY);
        }
        catch (Exception e) {
            Log.error("JWT generation failed",e);
            throw new RuntimeException(e);
        }
    }

    public String extractUserId(String token) {
        try {
            JsonWebToken jwt = jwtParser.parse(token);
            return jwt.getSubject(); // ✅ retrieves userId
        } catch (Exception e) {
            Log.error("Failed to parse JWT", e);
            throw new RuntimeException("Invalid token");
        }
    }

}

