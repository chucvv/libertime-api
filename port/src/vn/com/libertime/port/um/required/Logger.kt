package vn.com.libertime.port.um.required

public interface Logger {
    public fun info(message: String)
    public fun error(message: String, exception: Exception)
}