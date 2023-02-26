package red.tetracube.guestgroup;

import io.quarkus.grpc.GrpcClient;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import red.tetracube.gateway.guestgroup.GetListGroupsReply;
import red.tetracube.gateway.guestgroup.GetListGroupsRequest;
import red.tetracube.gateway.guestgroup.GuestGroupServices;
import red.tetracube.guestgroup.payloads.GuestGroupAPIReply;
import red.tetracube.guestgroup.payloads.GuestsGroupsAPIReply;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.UUID;

@Path("/guest-groups")
public class GuestGroupAPI {

    @GrpcClient
    GuestGroupServices guestgroup;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Uni<GuestsGroupsAPIReply> getGuestsGroups() {
        return guestgroup.listGroups(GetListGroupsRequest.getDefaultInstance())
                .map(GetListGroupsReply::getGroupList)
                .onItem()
                .transformToMulti(grpcGroups -> Multi.createFrom().items(grpcGroups.stream()))
                .map(grpcGroup -> {
                    var apiGroupReply = new GuestGroupAPIReply();
                    apiGroupReply.name = grpcGroup.getName();
                    apiGroupReply.id = UUID.fromString(grpcGroup.getId());
                    return apiGroupReply;
                })
                .collect()
                .asList()
                .map(groups -> {
                    var reply = new GuestsGroupsAPIReply();
                    reply.guestsGroups = groups;
                    return reply;
                });
    }
}
