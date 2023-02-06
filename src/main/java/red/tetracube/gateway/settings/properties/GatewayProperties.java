package red.tetracube.gateway.settings.properties;

import io.smallrye.config.ConfigMapping;
import org.eclipse.microprofile.config.inject.ConfigProperty;

@ConfigMapping(prefix = "gateway")
public interface GatewayProperties {

    @ConfigProperty(name = "secrets")
    SecretsProperties secrets();

}
