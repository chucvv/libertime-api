package vn.com.libertime.serverconfig

import com.typesafe.config.Config
import java.util.*

internal fun Config.toProperties() = Properties().also { prop ->
    for (e in this.entrySet()) {
        prop.setProperty(e.key, this.getString(e.key))
    }
}