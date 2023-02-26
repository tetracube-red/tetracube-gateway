package red.tetracube.guestgroup.payloads;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class GuestsGroupsAPIReply {

    @JsonProperty()
    public List<GuestGroupAPIReply> guestsGroups;
}
