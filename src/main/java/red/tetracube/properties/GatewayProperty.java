package red.tetracube.properties;

import io.smallrye.config.ConfigMapping;

@ConfigMapping(prefix = "gateway")
public interface GatewayProperty {

    SecurityGatewayProperty security();

}
