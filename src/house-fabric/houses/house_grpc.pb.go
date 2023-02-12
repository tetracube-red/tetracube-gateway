// Code generated by protoc-gen-go-grpc. DO NOT EDIT.
// versions:
// - protoc-gen-go-grpc v1.2.0
// - protoc             v3.19.6
// source: proto/house.proto

package houses

import (
	context "context"
	grpc "google.golang.org/grpc"
	codes "google.golang.org/grpc/codes"
	status "google.golang.org/grpc/status"
)

// This is a compile-time assertion to ensure that this generated file
// is compatible with the grpc package it is being compiled against.
// Requires gRPC-Go v1.32.0 or later.
const _ = grpc.SupportPackageIsVersion7

// HouseProceduresClient is the client API for HouseProcedures service.
//
// For semantics around ctx use and closing/ending streaming RPCs, please refer to https://pkg.go.dev/google.golang.org/grpc/?tab=doc#ClientConn.NewStream.
type HouseProceduresClient interface {
	Create(ctx context.Context, in *CreateHouseRequest, opts ...grpc.CallOption) (*CreateHouseReply, error)
}

type houseProceduresClient struct {
	cc grpc.ClientConnInterface
}

func NewHouseProceduresClient(cc grpc.ClientConnInterface) HouseProceduresClient {
	return &houseProceduresClient{cc}
}

func (c *houseProceduresClient) Create(ctx context.Context, in *CreateHouseRequest, opts ...grpc.CallOption) (*CreateHouseReply, error) {
	out := new(CreateHouseReply)
	err := c.cc.Invoke(ctx, "/house.HouseProcedures/Create", in, out, opts...)
	if err != nil {
		return nil, err
	}
	return out, nil
}

// HouseProceduresServer is the server API for HouseProcedures service.
// All implementations must embed UnimplementedHouseProceduresServer
// for forward compatibility
type HouseProceduresServer interface {
	Create(context.Context, *CreateHouseRequest) (*CreateHouseReply, error)
	mustEmbedUnimplementedHouseProceduresServer()
}

// UnimplementedHouseProceduresServer must be embedded to have forward compatible implementations.
type UnimplementedHouseProceduresServer struct {
}

func (UnimplementedHouseProceduresServer) Create(context.Context, *CreateHouseRequest) (*CreateHouseReply, error) {
	return nil, status.Errorf(codes.Unimplemented, "method Create not implemented")
}
func (UnimplementedHouseProceduresServer) mustEmbedUnimplementedHouseProceduresServer() {}

// UnsafeHouseProceduresServer may be embedded to opt out of forward compatibility for this service.
// Use of this interface is not recommended, as added methods to HouseProceduresServer will
// result in compilation errors.
type UnsafeHouseProceduresServer interface {
	mustEmbedUnimplementedHouseProceduresServer()
}

func RegisterHouseProceduresServer(s grpc.ServiceRegistrar, srv HouseProceduresServer) {
	s.RegisterService(&HouseProcedures_ServiceDesc, srv)
}

func _HouseProcedures_Create_Handler(srv interface{}, ctx context.Context, dec func(interface{}) error, interceptor grpc.UnaryServerInterceptor) (interface{}, error) {
	in := new(CreateHouseRequest)
	if err := dec(in); err != nil {
		return nil, err
	}
	if interceptor == nil {
		return srv.(HouseProceduresServer).Create(ctx, in)
	}
	info := &grpc.UnaryServerInfo{
		Server:     srv,
		FullMethod: "/house.HouseProcedures/Create",
	}
	handler := func(ctx context.Context, req interface{}) (interface{}, error) {
		return srv.(HouseProceduresServer).Create(ctx, req.(*CreateHouseRequest))
	}
	return interceptor(ctx, in, info, handler)
}

// HouseProcedures_ServiceDesc is the grpc.ServiceDesc for HouseProcedures service.
// It's only intended for direct use with grpc.RegisterService,
// and not to be introspected or modified (even as a copy)
var HouseProcedures_ServiceDesc = grpc.ServiceDesc{
	ServiceName: "house.HouseProcedures",
	HandlerType: (*HouseProceduresServer)(nil),
	Methods: []grpc.MethodDesc{
		{
			MethodName: "Create",
			Handler:    _HouseProcedures_Create_Handler,
		},
	},
	Streams:  []grpc.StreamDesc{},
	Metadata: "proto/house.proto",
}
