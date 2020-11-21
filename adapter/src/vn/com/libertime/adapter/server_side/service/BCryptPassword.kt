package vn.com.libertime.adapter.server_side.service

import org.mindrot.jbcrypt.BCrypt
import vn.com.libertime.port.um.required.PasswordEncryptable


internal class BCryptPassword : PasswordEncryptable {
    override fun validatePassword(attempt: String, userPassword: String): Boolean {
        return BCrypt.checkpw(attempt, userPassword)
    }

    override fun encryptPassword(password: String): String {
        return BCrypt.hashpw(password, BCrypt.gensalt())
    }
}