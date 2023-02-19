package red.tetracube.tetrahub.hub;

import io.quarkus.grpc.GrpcClient;
import io.smallrye.mutiny.Uni;
import io.smallrye.mutiny.unchecked.Unchecked;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import red.tetracube.extensions.ExceptionMapper;
import red.tetracube.properties.GatewayProperty;
import red.tetracube.tetrahub.hub.payloads.HubCreateAPIReply;
import red.tetracube.tetrahub.hub.payloads.HubCreateAPIRequest;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/hubs")
public class HubAPI {

    @GrpcClient
    HubServices hub;

    @Inject
    GatewayProperty gatewayProperty;

    @Inject
    BCryptPasswordEncoder passwordEncoder;

    @POST
    @Path("")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Uni<HubCreateAPIReply> createHub(@Valid HubCreateAPIRequest request) {
        var maintenanceCodeMatches = this.passwordEncoder.matches(
                request.maintenanceCode,
                this.gatewayProperty.security().maintenanceCode()
        );
        if (!maintenanceCodeMatches) {
            throw new ClientErrorException("INVALID_MAINTENANCE_CODE", Response.Status.UNAUTHORIZED);
        }
        var grpcRequest = TetraHubHubCreateRequest.newBuilder()
                .setName(request.name)
                .build();
        return this.hub.create(grpcRequest)
                .map(
                        Unchecked.function(tetraHubHubCreateReply -> {
                            if (tetraHubHubCreateReply.hasProcessingError()) {
                                throw ExceptionMapper.fromTetraHubErrorReply(tetraHubHubCreateReply.getProcessingError());
                            }
                            return new HubCreateAPIReply(
                                    tetraHubHubCreateReply.getName(),
                                    tetraHubHubCreateReply.getSlug()
                            );
                        })
                );
    }

}
