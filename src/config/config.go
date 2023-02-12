package config

import (
	"fmt"
	"github.com/spf13/viper"
	"log"
)

type Config struct {
	Port string `mapstructure:"PORT"`
}

func LoadConfig() (config Config, err error) {
	log.Println("Loading environment variables")
	basePath := "."
	viper.AddConfigPath(fmt.Sprintf("%s/gateway-config", basePath))
	viper.SetConfigName("")
	viper.SetConfigType("env")
	viper.AutomaticEnv()
	err = viper.ReadInConfig()
	if err != nil {
		log.Fatal(err)
		return
	}
	err = viper.Unmarshal(&config)
	return
}
