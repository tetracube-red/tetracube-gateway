package red.tetracube.hub.payloads;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.smallrye.common.constraint.NotNull;

import javax.validation.constraints.NotEmpty;

public class HubCreateAPIRequest {

    @NotNull
    @NotEmpty
    @JsonProperty()
    public String maintenanceCode;

    @NotNull
    @NotEmpty
    @JsonProperty()
    public String name;

}
