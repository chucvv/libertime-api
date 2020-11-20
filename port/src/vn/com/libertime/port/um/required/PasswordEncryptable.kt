package vn.com.libertime.port.um.required

interface PasswordEncryptable {
    fun validatePassword(attempt: String, userPassword: String): Boolean
    fun encryptPassword(password: String): String
}