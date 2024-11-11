package net.dodo.jwtimplementation.configuration;

import net.dodo.jwtimplementation.features.authentication.model.AuthenticationUser;
import net.dodo.jwtimplementation.features.authentication.repository.AuthenticationUserRepository;
import net.dodo.jwtimplementation.features.utils.Encoder;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LoadDatabaseConfiguration {

  private final Encoder encoder;

  public LoadDatabaseConfiguration(Encoder encoder) {
    this.encoder = encoder;
  }


  @Bean
  public CommandLineRunner initDatabase(AuthenticationUserRepository repository) {
    return args -> {
      AuthenticationUser user = new AuthenticationUser();
      user.setEmail("user@gmail.com");
      user.setPassword(encoder.encode("password"));
      repository.save(user);
    };

  }
}
