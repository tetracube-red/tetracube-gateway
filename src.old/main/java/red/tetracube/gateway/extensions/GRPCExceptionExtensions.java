package red.tetracube.gateway.extensions;

import io.grpc.Status;
import io.grpc.StatusRuntimeException;
import io.grpc.protobuf.ProtoUtils;
import io.grpc.reflection.v1alpha.ErrorResponse;
import red.tetracube.gateway.core.api.payloads.ErrorAPIReply;

import javax.ws.rs.ClientErrorException;
import javax.ws.rs.core.Response;

public final class GRPCExceptionExtensions {

    public static ClientErrorException toRestException(Exception sourceException) {
        var errorReply = new ErrorAPIReply();
        var statusCode = Response.Status.INTERNAL_SERVER_ERROR;

        if (sourceException instanceof StatusRuntimeException) {
            var errorDescription = ((StatusRuntimeException) sourceException).getTrailers().get(ProtoUtils.keyForProto(ErrorResponse.getDefaultInstance()));
            if (((StatusRuntimeException) sourceException).getStatus().equals(Status.ALREADY_EXISTS)) {
                errorReply.setCause(errorDescription.getErrorMessage());
                statusCode = Response.Status.CONFLICT;
            }
        }
        return new ClientErrorException(
                Response.status(statusCode).entity(errorReply).build()
        );
    }

}
