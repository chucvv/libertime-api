package vn.com.libertime.port.um.required

public interface Cacheable {
    public suspend fun put(key: String, value: String)
    public suspend fun get(key: String): String?
}