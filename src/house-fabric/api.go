package house_fabric

import (
	"errors"
	"github.com/gin-gonic/gin"
	"github.com/go-playground/validator/v10"
	"log"
	"net/http"
	"reflect"
	"tetracube.red/gateway/src/core/api"
	"tetracube.red/gateway/src/house-fabric/payloads"
)

type HouseFabricAPI struct {
	Engine *gin.Engine
}

func (houseFabricAPI *HouseFabricAPI) BuildHouseFabricEndpoints() {
	houseFabricAPI.Engine.POST("/houses", houseFabricAPI.createHouse)
}

func (houseFabricAPI *HouseFabricAPI) createHouse(c *gin.Context) {
	var house payloads.HouseAPIRequest

	if bindingError := c.ShouldBind(&house); bindingError != nil {
		log.Println("Binding error", bindingError)
		var validationErrors validator.ValidationErrors
		if errors.As(bindingError, &validationErrors) {
			bodyType := reflect.TypeOf(&house).Elem()
			c.JSON(http.StatusBadRequest, gin.H{"errors": api.MapValidationError(validationErrors, bodyType)})
			return
		}
		return
	}

	log.Println("Going to create house with name ", house.Name)

	c.String(201, "Success")
}
