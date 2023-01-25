package red.tetracube.gateway.housefabric.house;

import io.quarkus.grpc.GrpcClient;
import io.smallrye.mutiny.Uni;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import red.tetracube.gateway.extensions.GRPCExceptionExtensions;
import red.tetracube.gateway.housefabric.house.payloads.CreateHouseAPIReply;
import red.tetracube.gateway.housefabric.house.payloads.CreateHouseAPIRequest;

import javax.enterprise.context.RequestScoped;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@RequestScoped
@Path("/houses")
public class HouseAPI {

    private final Logger LOGGER = LoggerFactory.getLogger(HouseAPI.class);

    @GrpcClient
    HouseProcedure house;

    @POST
    @Path("")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Uni<CreateHouseAPIReply> createHouse(@Valid CreateHouseAPIRequest request) {
        var procedureRequest = request.asProcedureRequest();
        return this.house.create(procedureRequest)
                .onFailure()
                .transform(ex -> {
                    LOGGER.warn(ex.getMessage());
                    return GRPCExceptionExtensions.toRestException((Exception)ex);
                })
                .map(CreateHouseAPIReply::new);
    }
}
