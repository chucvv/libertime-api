package vn.com.libertime.common.log

public interface Logger {
    public fun info(message: String)
    public fun error(message: String, exception: Exception)
}