package red.tetracube.gateway.housefabric.house.payloads;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.smallrye.common.constraint.NotNull;
import red.tetracube.gateway.housefabric.house.CreateHouseRequest;

import javax.validation.constraints.NotEmpty;

public class CreateHouseAPIRequest {

    @NotNull
    @NotEmpty
    @JsonProperty("name")
    private String name;

    @NotNull
    @NotEmpty
    @JsonProperty("maintenanceCode")
    private String maintenanceCode;

    public String getName() {
        return name;
    }

    public String getMaintenanceCode() {
        return maintenanceCode;
    }

    public CreateHouseRequest asProcedureRequest() {
        return CreateHouseRequest.newBuilder()
                .setName(this.name)
                .build();
    }

}
