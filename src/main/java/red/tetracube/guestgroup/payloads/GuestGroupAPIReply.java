package red.tetracube.guestgroup.payloads;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

public class GuestGroupAPIReply {

    @JsonProperty()
    public UUID id;

    @JsonProperty()
    public String name;
}
