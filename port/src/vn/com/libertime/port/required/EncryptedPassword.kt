package vn.com.libertime.port.required

interface EncryptedPassword {
    fun validatePassword(attempt: String, userPassword: String): Boolean
    fun encryptPassword(password: String): String
}