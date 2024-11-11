package net.dodo.jwtimplementation.features.authentication.controller;

import jakarta.validation.Valid;
import net.dodo.jwtimplementation.features.authentication.dto.AuthenticationRequestBody;
import net.dodo.jwtimplementation.features.authentication.dto.AuthenticationResponseBody;
import net.dodo.jwtimplementation.features.authentication.model.AuthenticationUser;
import net.dodo.jwtimplementation.features.authentication.service.AuthenticationService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/authentication")
public class AuthenticationController {

  private final AuthenticationService authenticationService;

  public AuthenticationController(AuthenticationService authenticationService) {
    this.authenticationService = authenticationService;
  }

  @GetMapping("/")
  public String hello() {
    return "User";
  }

  @GetMapping("/user")
  public AuthenticationUser getUser(@RequestAttribute("authenticatedUser") AuthenticationUser user) {
    return user;
  }

  @PostMapping("/register")
  public AuthenticationResponseBody registerPage(@Valid @RequestBody AuthenticationRequestBody authenticationRequestBody) {
    return authenticationService.register(authenticationRequestBody);

  }

  @PostMapping("/login")
  public AuthenticationResponseBody loginPage(@Valid @RequestBody AuthenticationRequestBody authenticationRequestBody) {
    return authenticationService.login(authenticationRequestBody);
  }

}
