package red.tetracube.gateway.core.beans;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.inject.Singleton;
import javax.ws.rs.Produces;

@Singleton
public class PasswordEncoder {

    @Singleton
    @Produces
    BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
