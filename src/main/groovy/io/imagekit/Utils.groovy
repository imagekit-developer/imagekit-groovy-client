package io.imagekit

import javax.crypto.Mac
import javax.crypto.spec.SecretKeySpec
import java.security.InvalidKeyException
import java.security.NoSuchAlgorithmException

class Utils {

    private final static String ALGORITHM = "HmacSHA1"

    static String sign(String content, String privateApiKey) {
        String encoded
        try {
            SecretKeySpec signingKey = new SecretKeySpec(privateApiKey.getBytes(), ALGORITHM)
            Mac mac = Mac.getInstance(ALGORITHM)
            mac.init(signingKey)
            encoded = toHexString(mac.doFinal(content.getBytes()))
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Cannot create signature.", e)
        } catch (InvalidKeyException e) {
            throw new RuntimeException("Cannot create signature.", e)
        }
        encoded
    }

    private static String toHexString(byte[] bytes) {
        Formatter formatter = new Formatter()
        for (byte b : bytes) {
            formatter.format("%02x", b)
        }
        formatter.toString()
    }

    static String timestamp() {
        Long.toString(System.currentTimeMillis() / 1000L as long)
    }

}
