package com.project.api.v1.common;


@ApplicationScoped
public class HashingTechniques {

    @ConfigProperty(name = "security.bcrypt.cost", defaultValue = "10")
    int cost;

    private static byte[] sha256(String userInput) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            return digest.digest(userInput.getBytes(StandardCharsets.UTF_8));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static String encodeBase64(byte[] hash) {
        return Base64.getUrlEncoder()
                .withoutPadding()
                .encodeToString(hash);
    }

    public static String hashPassword(String plainPassword) {
        if (plainPassword == null || plainPassword.isEmpty()) {
            throw new IllegalArgumentException("Password cannot be null or empty");
        }

        return BCrypt.hashpw(plainPassword, BCrypt.gensalt(DEFAULT_COST));
    }

    public static boolean verifyPassword(String plainPassword, String hashedPassword) {
        if (plainPassword == null || hashedPassword == null) {
            return false;
        }

        return BCrypt.checkpw(plainPassword, hashedPassword);
    }

}