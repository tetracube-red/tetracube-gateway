package red.tetracube.gateway.core.api.payloads;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ErrorAPIReply {

    @JsonProperty
    private String cause;

    public void setCause(String cause) {
        this.cause = cause;
    }

}
