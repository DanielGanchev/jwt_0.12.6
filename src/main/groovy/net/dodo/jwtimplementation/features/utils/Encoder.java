package net.dodo.jwtimplementation.features.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import org.springframework.stereotype.Component;

@Component
public class Encoder {
  public String encode(String rawString) {

    MessageDigest digest = null;
    try {
      digest = MessageDigest.getInstance("SHA-256");
      byte[] encodedhash = digest.digest(rawString.getBytes());
      return Base64.getEncoder().encodeToString(encodedhash);
    } catch (NoSuchAlgorithmException e) {
      throw new RuntimeException("Error while encoding the string", e);
    }
  }
  public boolean matches(String rawString, String encodedString) {
    return encode(rawString).equals(encodedString);
  }
}
