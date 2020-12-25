rootProject.name = "libertime_api"
include(
    ":app",
    ":common",
    ":infrastructure",
    ":server-config",
    ":kafka",
    ":port",
    ":adapter",
    ":domain",
    ":database",
    ":cache",
    ":chatting"
)

project(":server-config").projectDir = file("infrastructure/server-config")
project(":database").projectDir = file("infrastructure/database")
project(":cache").projectDir = file("infrastructure/cache")
project(":kafka").projectDir = file("infrastructure/kafka")