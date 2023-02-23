package red.tetracube.hub;

import io.quarkus.grpc.GrpcClient;
import io.smallrye.mutiny.Uni;
import io.smallrye.mutiny.unchecked.Unchecked;
import org.jboss.resteasy.reactive.ResponseStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import red.tetracube.extensions.ExceptionMapper;
import red.tetracube.gateway.hub.HubLoungeHubCreateRequest;
import red.tetracube.gateway.hub.HubServices;
import red.tetracube.properties.GatewayProperty;
import red.tetracube.hub.payloads.HubCreateAPIReply;
import red.tetracube.hub.payloads.HubCreateAPIRequest;

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
    @ResponseStatus(201)
    public Uni<HubCreateAPIReply> createHub(@Valid HubCreateAPIRequest request) {
        var maintenanceCodeMatches = this.passwordEncoder.matches(
                request.maintenanceCode,
                this.gatewayProperty.security().maintenanceCode()
        );
        if (!maintenanceCodeMatches) {
            throw new ClientErrorException("INVALID_MAINTENANCE_CODE", Response.Status.UNAUTHORIZED);
        }
        var grpcRequest = HubLoungeHubCreateRequest.newBuilder()
                .setName(request.name)
                .build();
        return this.hub.create(grpcRequest)
                .map(
                        Unchecked.function(hubLoungeHubCreateReply -> {
                            if (hubLoungeHubCreateReply.hasProcessingError()) {
                                throw ExceptionMapper.fromTetraHubErrorReply(hubLoungeHubCreateReply.getProcessingError());
                            }
                            return new HubCreateAPIReply(
                                    hubLoungeHubCreateReply.getName(),
                                    hubLoungeHubCreateReply.getSlug()
                            );
                        })
                );
    }

}
