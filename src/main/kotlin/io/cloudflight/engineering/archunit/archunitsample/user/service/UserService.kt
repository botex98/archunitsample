package io.cloudflight.engineering.archunit.archunitsample.user.service

import io.cloudflight.engineering.archunit.archunitsample.user.api.dto.UserDto
import io.cloudflight.engineering.archunit.archunitsample.user.api.dto.UserSavingDto

interface UserService {

    fun findById(id: Long): UserDto?

    fun create(userSavingDto: UserSavingDto?): UserDto?
}
