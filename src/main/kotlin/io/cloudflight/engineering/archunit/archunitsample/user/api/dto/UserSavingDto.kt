package io.cloudflight.engineering.archunit.archunitsample.user.api.dto

data class UserSavingDto(
    val username: String?,
    val email: String?,
    val externalId: Long?,
)
