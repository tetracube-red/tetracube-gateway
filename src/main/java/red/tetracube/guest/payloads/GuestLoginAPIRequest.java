package red.tetracube.guest.payloads;

import com.fasterxml.jackson.annotation.JsonProperty;

public class GuestLoginAPIRequest {

    @JsonProperty("name")
    public String name;

    @JsonProperty("password")
    public String password;
}
