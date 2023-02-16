package red.tetracube;

import io.quarkus.grpc.GrpcClient;
import io.smallrye.mutiny.Uni;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/hello")
public class GreetingResource {

    @GrpcClient
    HelloGrpc hello;

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public Uni<String> hello() {
        return hello.sayHello(
                HelloRequest.newBuilder()
                        .setName("Dave")
                        .build()
        )
                .map(HelloReply::getMessage);
    }
}