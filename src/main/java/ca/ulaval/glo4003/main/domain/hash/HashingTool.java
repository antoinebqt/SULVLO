package ca.ulaval.glo4003.main.domain.hash;

import java.math.BigInteger;
import java.security.MessageDigest;

public class HashingTool {

    public static String hashString(String toHash) {

        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] messageDigest = md.digest(toHash.getBytes());
            BigInteger bigInt = new BigInteger(1, messageDigest);
            return bigInt.toString(16);
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }

}
