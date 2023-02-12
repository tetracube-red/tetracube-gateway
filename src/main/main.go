package main

import (
	"fmt"
	"github.com/gin-gonic/gin"
	"log"
	"tetracube.red/gateway/src/config"
	housefabric "tetracube.red/gateway/src/house-fabric"
)

func main() {
	configuration, loadConfigErr := config.LoadConfig()
	if loadConfigErr != nil {
		log.Fatalln("Cannot initialize configurations")
	}

	log.Printf("Exposing APIs service on port %s\n", configuration.Port)
	r := gin.New()
	houseFabricAPI := housefabric.HouseFabricAPI{
		Engine: r,
	}
	houseFabricAPI.BuildHouseFabricEndpoints()
	address := fmt.Sprintf(":%s", configuration.Port)
	err := r.Run(address)
	if err != nil {
		log.Fatalln("Cannot run gateway service")
	}
}
