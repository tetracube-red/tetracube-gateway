package red.tetracube.gateway.housefabric.house.payloads;

import com.fasterxml.jackson.annotation.JsonProperty;
import red.tetracube.gateway.housefabric.house.CreateHouseReply;

import java.util.UUID;

public class CreateHouseAPIReply {

    @JsonProperty
    private String name;

    @JsonProperty
    private UUID id;

    @JsonProperty
    private String slug;

    public CreateHouseAPIReply() {
    }

    public CreateHouseAPIReply(CreateHouseReply procedureReply) {
        this.name = procedureReply.getName();
        this.id = UUID.fromString(procedureReply.getId());
        this.slug = procedureReply.getSlug();
    }

}
