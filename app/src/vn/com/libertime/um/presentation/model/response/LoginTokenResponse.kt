package vn.com.libertime.um.presentation.model.response

import vn.com.libertime.um.domain.entity.CredentialEntity

data class LoginTokenResponse(val tokens: CredentialEntity)