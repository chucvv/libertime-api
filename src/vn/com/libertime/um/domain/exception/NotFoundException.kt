package vn.com.libertime.um.domain.exception

data class NotFoundException(override val message: String = "") : Exception()