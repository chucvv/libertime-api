package vn.com.libertime.um.domain.exception

data class ExistedStateException(override val message: String = "") : Exception()