package red.tetracube.guest.payloads;

import com.fasterxml.jackson.annotation.JsonProperty;

public class GuestLoginAPIReply {

    @JsonProperty("accessToken")
    public String accessToken;
}
