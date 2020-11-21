package vn.com.libertime.port.um.required

public interface PasswordEncryptable {
    public fun validatePassword(attempt: String, userPassword: String): Boolean
    public fun encryptPassword(password: String): String
}