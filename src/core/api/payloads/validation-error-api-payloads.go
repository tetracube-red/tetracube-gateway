package payloads

type ValidationError struct {
	Field  string `json:"field"`
	Reason string `json:"reason"`
}
