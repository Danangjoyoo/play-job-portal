package util.authentication;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

public class PasswordHashing {
    public static String hash(String password){
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] encodedHash = digest.digest(password.getBytes(StandardCharsets.UTF_8));

            StringBuilder hexString = new StringBuilder();
            for (byte b : encodedHash){
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1){
                    hexString.append("O");
                }
                hexString.append(hex);
            }

            return hexString.toString();
        } catch (Exception e) {
            return null;
        }
    }

    public static Boolean check(String password, String hashedPassword){
        String hashedInputPassword = PasswordHashing.hash(password);
        return hashedInputPassword != null && hashedInputPassword.equals(hashedPassword);
    }
}
