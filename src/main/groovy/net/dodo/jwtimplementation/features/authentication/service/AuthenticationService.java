package net.dodo.jwtimplementation.features.authentication.service;

import net.dodo.jwtimplementation.features.authentication.dto.AuthenticationRequestBody;
import net.dodo.jwtimplementation.features.authentication.dto.AuthenticationResponseBody;
import net.dodo.jwtimplementation.features.authentication.model.AuthenticationUser;
import net.dodo.jwtimplementation.features.authentication.repository.AuthenticationUserRepository;
import net.dodo.jwtimplementation.features.utils.Encoder;
import net.dodo.jwtimplementation.features.utils.JsonWebToken;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {
    private final AuthenticationUserRepository authenticationUserRepository;
    private final Encoder encoder;
    private final JsonWebToken jsonWebToken;

  public AuthenticationService(AuthenticationUserRepository authenticationUserRepository,
      Encoder encoder, JsonWebToken jsonWebToken) {
    this.authenticationUserRepository = authenticationUserRepository;
    this.encoder = encoder;
    this.jsonWebToken = jsonWebToken;
  }

  public AuthenticationUser getUser(String email) {
    return authenticationUserRepository
        .findByEmail(email)
        .orElseThrow(() -> new IllegalArgumentException("User not found"));
  }

  public AuthenticationResponseBody register(AuthenticationRequestBody authenticationRequestBody) {

    if (authenticationUserRepository.findByEmail(authenticationRequestBody.getEmail()).isPresent()) {
      return new AuthenticationResponseBody(null, "User already exists");
    }

    authenticationUserRepository.save(new AuthenticationUser(authenticationRequestBody.getEmail(),
        encoder.encode(authenticationRequestBody.getPassword())));

    return new AuthenticationResponseBody("token", "User registered");
  }

  public AuthenticationResponseBody login(AuthenticationRequestBody authenticationRequestBody) {
    AuthenticationUser user = authenticationUserRepository.findByEmail(authenticationRequestBody.getEmail())
        .orElseThrow(() -> new IllegalArgumentException("User not found"));

    if (!encoder.matches(authenticationRequestBody.getPassword(), user.getPassword())) {
      return new AuthenticationResponseBody(null, "Invalid password");
    }

    String token = jsonWebToken.generateToken(user.getEmail());
    return new AuthenticationResponseBody(token, "User logged in");
  }
}
