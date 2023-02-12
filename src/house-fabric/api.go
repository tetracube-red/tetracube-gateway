package house_fabric

import (
	"context"
	"errors"
	"github.com/gin-gonic/gin"
	"github.com/go-playground/validator/v10"
	"google.golang.org/grpc"
	"google.golang.org/grpc/credentials/insecure"
	"log"
	"net/http"
	"reflect"
	"tetracube.red/gateway/src/core/api"
	"tetracube.red/gateway/src/house-fabric/houses"
	"tetracube.red/gateway/src/house-fabric/payloads"
	"time"
)

type HouseFabricAPI struct {
	Engine *gin.Engine
}

func (houseFabricAPI *HouseFabricAPI) BuildHouseFabricEndpoints() {
	houseFabricAPI.Engine.POST("/houses", houseFabricAPI.createHouse)
}

func (houseFabricAPI *HouseFabricAPI) createHouse(ginContext *gin.Context) {
	var house payloads.HouseAPIRequest

	if bindingError := ginContext.ShouldBind(&house); bindingError != nil {
		log.Println("Binding error", bindingError)
		var validationErrors validator.ValidationErrors
		if errors.As(bindingError, &validationErrors) {
			bodyType := reflect.TypeOf(&house).Elem()
			ginContext.JSON(http.StatusBadRequest, gin.H{"errors": api.MapValidationError(validationErrors, bodyType)})
			return
		}
		return
	}

	log.Println("Going to create house with name ", house.Name)
	connection, err := grpc.Dial("localhost:8081", grpc.WithTransportCredentials(insecure.NewCredentials()))
	if err != nil {
		ginContext.JSON(http.StatusInternalServerError, gin.H{"errors": "cannot reach house service"})
		return
	}
	defer connection.Close()
	houseFabricClient := houses.NewHouseProceduresClient(connection)
	ctx, cancel := context.WithTimeout(context.Background(), time.Second)
	defer cancel()
	reply, err := houseFabricClient.Create(ctx, &houses.CreateHouseRequest{Name: house.Name})
	if err != nil {
		ginContext.JSON(http.StatusInternalServerError, gin.H{"errors": "cannot store data"})
		return
	}

	log.Println(reply)
	ginContext.String(201, "Success")
}
