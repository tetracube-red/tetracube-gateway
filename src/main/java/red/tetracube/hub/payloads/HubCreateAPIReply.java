package red.tetracube.hub.payloads;

import com.fasterxml.jackson.annotation.JsonProperty;

public class HubCreateAPIReply {

    @JsonProperty
    private final String name;

    @JsonProperty
    private final String slug;

    public HubCreateAPIReply(String name, String slug) {
        this.name = name;
        this.slug = slug;
    }
}
