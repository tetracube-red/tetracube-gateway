package house_fabric

import (
	"github.com/gin-gonic/gin"
	"log"
	"time"
)

type Person struct {
	Name     string    `form:"name"`
	Address  string    `form:"address"`
	Birthday time.Time `form:"birthday" time_format:"2006-01-02" time_utc:"1"`
}
type HouseFabricAPI struct {
	Engine *gin.Engine
}

func (houseFabricAPI *HouseFabricAPI) BuildHouseFabricEndpoints() {
	houseFabricAPI.Engine.POST("/test", houseFabricAPI.createHouse)
}

func (houseFabricAPI *HouseFabricAPI) createHouse(c *gin.Context) {
	var person Person
	// If `GET`, only `Form` binding engine (`query`) used.
	// If `POST`, first checks the `content-type` for `JSON` or `XML`, then uses `Form` (`form-data`).
	// See more at https://github.com/gin-gonic/gin/blob/master/binding/binding.go#L48
	if c.ShouldBind(&person) == nil {
		log.Println(person.Name)
		log.Println(person.Address)
		log.Println(person.Birthday)
	}

	c.String(200, "Success")
}
