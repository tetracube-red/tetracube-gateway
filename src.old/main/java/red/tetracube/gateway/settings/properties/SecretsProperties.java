package red.tetracube.gateway.settings.properties;

import org.eclipse.microprofile.config.inject.ConfigProperty;

public interface SecretsProperties {

    @ConfigProperty(name = "maintenance-code")
    String maintenanceCode();

}
