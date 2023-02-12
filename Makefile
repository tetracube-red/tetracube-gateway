proto:
	protoc proto/*.proto --go-grpc_out=. --go_out=.

build:
	go build -o out/gateway ./src/main/main.go