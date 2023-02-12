FROM docker.io/golang:1.20.0-alpine as builder
WORKDIR /source
COPY src ./src
RUN ls ./
COPY go.mod ./
COPY go.sum ./
COPY Makefile ./
RUN apk add make
RUN make build

FROM docker.io/golang:1.20.0-alpine
WORKDIR /gateway
ENV GIN_MODE=release
COPY --from=builder /source/out/gateway ./
CMD ["./gateway"]