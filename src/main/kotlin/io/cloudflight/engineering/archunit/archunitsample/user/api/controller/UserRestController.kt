package io.cloudflight.engineering.archunit.archunitsample.user.api.controller

import io.cloudflight.engineering.archunit.archunitsample.user.api.UserApi
import io.cloudflight.engineering.archunit.archunitsample.user.api.dto.UserDto
import io.cloudflight.engineering.archunit.archunitsample.user.api.dto.UserSavingDto
import io.cloudflight.engineering.archunit.archunitsample.user.service.UserService
import org.springframework.web.bind.annotation.RestController

@RestController
class UserRestController(
    private val userService: UserService
) : UserApi {

    override fun get(id: Long): UserDto? = userService.findById(id)

    override fun create(userSavingDto: UserSavingDto?): UserDto? = userService.create(userSavingDto)


}
