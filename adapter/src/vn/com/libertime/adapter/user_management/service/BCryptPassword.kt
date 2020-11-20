package vn.com.libertime.adapter.user_management.service

import org.mindrot.jbcrypt.BCrypt
import vn.com.libertime.port.required.EncryptedPassword


class BCryptPassword : EncryptedPassword {
    override fun validatePassword(attempt: String, userPassword: String): Boolean {
        return BCrypt.checkpw(attempt, userPassword)
    }

    override fun encryptPassword(password: String): String {
        return BCrypt.hashpw(password, BCrypt.gensalt())
    }
}