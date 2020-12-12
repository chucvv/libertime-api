rootProject.name = "libertime_api"
include("app", "common", "infrastructure", "server-config", "port", "adapter", "domain", "database", "chatting")

project(":server-config").projectDir = file("infrastructure/server-config")
project(":database").projectDir = file("infrastructure/database")