package red.tetracube.gateway.housefabric.house;

import io.quarkus.grpc.GrpcClient;
import io.quarkus.security.UnauthorizedException;
import io.smallrye.mutiny.Uni;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import red.tetracube.gateway.extensions.GRPCExceptionExtensions;
import red.tetracube.gateway.housefabric.house.payloads.CreateHouseAPIReply;
import red.tetracube.gateway.settings.properties.GatewayProperties;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@RequestScoped
@Path("/houses")
public class HouseAPI {

    private final Logger LOGGER = LoggerFactory.getLogger(HouseAPI.class);

    @GrpcClient
    HouseProcedure house;

    @Inject
    GatewayProperties gatewayProperties;

    @Inject
    BCryptPasswordEncoder passwordEncoder;

    @POST
    @Path("")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Uni<CreateHouseAPIReply> createHouse(@Valid CreateHouseAPIRequest request) {
        var procedureRequest = request.asProcedureRequest();
        var isMaintenanceCodeValid = this.passwordEncoder.matches(
                request.getMaintenanceCode(),
                this.gatewayProperties.secrets().maintenanceCode()
        );
        if (!isMaintenanceCodeValid) {
            throw new UnauthorizedException("INVALID_MAINTENANCE_CODE");
        }
        return this.house.create(procedureRequest)
                .onFailure()
                .transform(ex -> {
                    LOGGER.warn(ex.getMessage());
                    return GRPCExceptionExtensions.toRestException((Exception)ex);
                })
                .map(CreateHouseAPIReply::new);
    }
}
