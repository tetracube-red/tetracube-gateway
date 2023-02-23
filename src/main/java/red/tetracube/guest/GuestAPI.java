package red.tetracube.guest;

import io.quarkus.grpc.GrpcClient;
import io.smallrye.mutiny.Uni;
import io.smallrye.mutiny.unchecked.Unchecked;
import org.jboss.resteasy.reactive.ResponseStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import red.tetracube.extensions.ExceptionMapper;
import red.tetracube.gateway.guest.GuestServices;
import red.tetracube.gateway.guest.HubLoungeGuestEnrollmentRequest;
import red.tetracube.gateway.guest.HubLoungeGuestLoginRequest;
import red.tetracube.gateway.hub.HubLoungeHubCreateRequest;
import red.tetracube.guest.payloads.GuestEnrollmentAPIRequest;
import red.tetracube.guest.payloads.GuestLoginAPIReply;
import red.tetracube.guest.payloads.GuestLoginAPIRequest;
import red.tetracube.hub.payloads.HubCreateAPIReply;
import red.tetracube.properties.GatewayProperty;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/guests")
public class GuestAPI {

    @GrpcClient
    GuestServices guest;

    @Inject
    GatewayProperty gatewayProperty;

    @Inject
    BCryptPasswordEncoder passwordEncoder;

    @POST
    @Path("")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @ResponseStatus(201)
    public Uni<Void> guestEnrollment(@Valid GuestEnrollmentAPIRequest request) {
        var maintenanceCodeMatches = this.passwordEncoder.matches(
                request.maintenanceCode,
                this.gatewayProperty.security().maintenanceCode()
        );
        if (!maintenanceCodeMatches) {
            throw new ClientErrorException("INVALID_MAINTENANCE_CODE", Response.Status.UNAUTHORIZED);
        }
        var grpcRequest = HubLoungeGuestEnrollmentRequest.newBuilder()
                .setName(request.name)
                .setGuestGroupName(request.guestGroupName)
                .setPassword(request.password)
                .build();
        return this.guest.enrollment(grpcRequest)
                .map(
                        Unchecked.function(hubLoungeGuestEnrollmentReply -> {
                            if (hubLoungeGuestEnrollmentReply.hasProcessingError()) {
                                throw ExceptionMapper.fromTetraHubErrorReply(hubLoungeGuestEnrollmentReply.getProcessingError());
                            }
                            return null;
                        })
                )
                .replaceWithVoid();
    }

    @POST
    @Path("/login")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Uni<GuestLoginAPIReply> doGuestLogin(@Valid GuestLoginAPIRequest request) {
        var grpcRequest = HubLoungeGuestLoginRequest.newBuilder()
                .setName(request.name)
                .setPassword(request.password)
                .build();
        return this.guest.login(grpcRequest)
                .map(
                        Unchecked.function(hubLoungeGuestLoginReply -> {
                            if (hubLoungeGuestLoginReply.hasProcessingError()) {
                                throw ExceptionMapper.fromTetraHubErrorReply(hubLoungeGuestLoginReply.getProcessingError());
                            }
                            var apiResponse = new GuestLoginAPIReply();
                            apiResponse.accessToken = hubLoungeGuestLoginReply.getAccessToken();
                            return apiResponse;
                        })
                );
    }
}
