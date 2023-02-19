package red.tetracube.extensions;

import org.jboss.resteasy.reactive.RestResponse;
import red.tetracube.tetrahub.errors.TetraHubProcessErrorReply;

import javax.ws.rs.ClientErrorException;

public class ExceptionMapper {

    public static ClientErrorException fromTetraHubErrorReply(TetraHubProcessErrorReply errorReply) {
        var description = errorReply.getDescription();
        var statusCode = RestResponse.StatusCode.BAD_GATEWAY;
        switch (errorReply.getReason()){
            case CONFLICTS -> {
                statusCode = RestResponse.StatusCode.CONFLICT;
            }
            case INTERNAL_ERROR, UNRECOGNIZED -> {
                statusCode = RestResponse.StatusCode.INTERNAL_SERVER_ERROR;
            }
        }
        return new ClientErrorException(description, statusCode);
    }

}
