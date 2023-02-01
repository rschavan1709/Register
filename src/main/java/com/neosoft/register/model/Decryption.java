package com.neosoft.register.model;

import lombok.extern.slf4j.Slf4j;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.ByteBuffer;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Base64;

@Slf4j
public class Decryption {
    public static String aesDecrypt(final String encryptedText, final String password){
        try{
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
//strip off the salt and iv
            ByteBuffer buffer = ByteBuffer.wrap(Base64.getMimeDecoder().decode(encryptedText));
            byte[] saltBytes = new byte[20];
            buffer.get(saltBytes, 0, saltBytes.length);
            byte[] ivBytes1 = new byte[cipher.getBlockSize()];
            buffer.get(ivBytes1, 0, ivBytes1.length);
            byte[] encryptedTextBytes = new byte[buffer.capacity() - saltBytes.length -
                    ivBytes1.length];
            buffer.get(encryptedTextBytes);
            SecretKeyFactory factory =
                    SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
            PBEKeySpec spec = new PBEKeySpec(password.toCharArray(), saltBytes, 50,
                    128);
            SecretKey secretKey = factory.generateSecret(spec);
            SecretKeySpec secret = new SecretKeySpec(secretKey.getEncoded(), "AES");
            cipher.init(Cipher.DECRYPT_MODE, secret, new IvParameterSpec(ivBytes1));
            byte[] decryptedTextBytes = null;
            try {
                decryptedTextBytes = cipher.doFinal(encryptedTextBytes);
            } catch (Exception e) {
                log.error("Exception in aesDecrypt method: {}", e.getMessage());
            }
            return new String(decryptedTextBytes);
        }catch(NoSuchAlgorithmException | InvalidKeySpecException | NoSuchPaddingException |
               InvalidKeyException | InvalidAlgorithmParameterException ex2){
            final Exception ex = null;
            final Exception e = ex;
            return "ER001" + e.toString();
        }
    }
}
