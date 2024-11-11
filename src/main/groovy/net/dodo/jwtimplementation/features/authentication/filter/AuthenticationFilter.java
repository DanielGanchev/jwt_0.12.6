package net.dodo.jwtimplementation.features.authentication.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import net.dodo.jwtimplementation.features.authentication.model.AuthenticationUser;
import net.dodo.jwtimplementation.features.authentication.service.AuthenticationService;
import net.dodo.jwtimplementation.features.utils.JsonWebToken;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationFilter extends HttpFilter {

  private final List<String> unprotectedEndpoints = Arrays.asList(
      "/api/v1/authentication/register",
      "/api/v1/authentication/login",
      "/api/v1/authentication/send-password-reset-token",
      "/api/v1/authentication/reset-password"
  );

   private final JsonWebToken jsonWebToken;
   private final AuthenticationService authenticationService;


  public AuthenticationFilter(JsonWebToken jsonWebToken,
      AuthenticationService authenticationService) {
    this.jsonWebToken = jsonWebToken;
    this.authenticationService = authenticationService;


  }
  @Override
  protected void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
    response.addHeader("Access-Control-Allow-Origin", "*");
    response.addHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
    response.addHeader("Access-Control-Allow-Headers", "Content-Type, Authorization");

    if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
      response.setStatus(HttpServletResponse.SC_OK);
      return;
    }


    String path = request.getRequestURI();
    if (unprotectedEndpoints.contains(path)) {
      chain.doFilter(request, response);
      return;
    }

   try{
      String authorization = request.getHeader("Authorization");
      if (authorization == null || !authorization.startsWith("Bearer ")) {
        throw new ServletException("Token not found");
      }

      String token = authorization.substring(7);
      if (jsonWebToken.isTokenExpired(token)) {
        throw new ServletException("Token expired");
      }

      String email = jsonWebToken.getEmailFromToken(token);
      AuthenticationUser user = authenticationService.getUser(email);
      request.setAttribute("authenticatedUser", user);
      chain.doFilter(request, response);


   }catch (Exception e){
     response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
     response.setContentType("application/json");
     response.getWriter().write("{\"message\": \"Invalid token or token is missing\"}");
   }
  }
}
