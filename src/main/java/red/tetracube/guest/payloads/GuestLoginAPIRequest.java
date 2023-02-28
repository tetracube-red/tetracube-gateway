package red.tetracube.guest.payloads;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.smallrye.common.constraint.NotNull;

import javax.validation.constraints.NotEmpty;

public class GuestLoginAPIRequest {

    @NotNull
    @NotEmpty
    @JsonProperty("name")
    public String name;

    @NotNull
    @NotEmpty
    @JsonProperty("password")
    public String password;
}
