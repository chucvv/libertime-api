package vn.com.libertime.common.extension

public fun <T> concatenate(vararg lists: List<T>): List<T> {
    return listOf(*lists).flatten()
}