package com.neosoft.register.model;

import org.springframework.stereotype.Component;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.AlgorithmParameters;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.InvalidParameterSpecException;
import java.util.Base64;

@Component
public class Encryption {
    public static String aesEncrypt(final String word, final String password){
        try{
            byte[] ivBytes;
            SecureRandom random = new SecureRandom();
            byte[] bytes = new byte[20];
            random.nextBytes(bytes);
            byte[] saltBytes = bytes;
            SecretKeyFactory factory =
                    SecretKeyFactory.getInstance("PBKDF2withHmacSHA1");
            PBEKeySpec spec = new PBEKeySpec(password.toCharArray(), saltBytes, 50,
                    128);
            SecretKey secretKey = factory.generateSecret(spec);
            SecretKeySpec secret = new SecretKeySpec(secretKey.getEncoded(), "AES");
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, secret);
            AlgorithmParameters params = cipher.getParameters();
            ivBytes = params.getParameterSpec(IvParameterSpec.class).getIV();
            byte[] encryptedTextBytes = cipher.doFinal(word.getBytes("UTF-8"));
//prepend salt and vi
            byte[] buffer = new byte[saltBytes.length + ivBytes.length +
                    encryptedTextBytes.length];
            System.arraycopy(saltBytes, 0, buffer, 0, saltBytes.length);
            System.arraycopy(ivBytes, 0, buffer, saltBytes.length, ivBytes.length);System.arraycopy(encryptedTextBytes, 0, buffer, saltBytes.length +
                    ivBytes.length, encryptedTextBytes.length);
            return Base64.getMimeEncoder().encodeToString(buffer);
//return Base64.encodeBase64String(buffer);
        }catch(BadPaddingException | InvalidParameterSpecException |
               NoSuchAlgorithmException |
               InvalidKeySpecException | NoSuchPaddingException |
               InvalidKeyException |
               IllegalBlockSizeException | UnsupportedEncodingException ex2){
            final Exception ex = null;
            final Exception e = ex;
            return "ER001" + e.toString();
        }
    }
}
