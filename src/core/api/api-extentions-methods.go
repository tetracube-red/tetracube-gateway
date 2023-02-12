package api

import (
	"fmt"
	"github.com/go-playground/validator/v10"
	"reflect"
	"tetracube.red/gateway/src/core/api/payloads"
)

func MapValidationError(verr validator.ValidationErrors, bodyType reflect.Type) []payloads.ValidationError {
	var errs []payloads.ValidationError

	for _, f := range verr {
		err := f.ActualTag()
		if f.Param() != "" {
			err = fmt.Sprintf("%s=%s", err, f.Param())
		}
		field, _ := bodyType.FieldByName(f.Field())
		fieldName := getStructTag(field, "json")
		errs = append(errs, payloads.ValidationError{Field: fieldName, Reason: err})
	}

	return errs
}

func getStructTag(f reflect.StructField, tagName string) string {
	return string(f.Tag.Get(tagName))
}
