package payloads

type HouseAPIRequest struct {
	MaintenanceCode string `json:"maintenance_code" binding:"required"`
	Name            string `json:"name" binding:"required"`
}
