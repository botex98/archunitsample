package io.cloudflight.engineering.archunit.archunitsample.user.api

import io.cloudflight.engineering.archunit.archunitsample.user.api.dto.UserDto
import io.cloudflight.engineering.archunit.archunitsample.user.api.dto.UserSavingDto
import io.swagger.annotations.Api
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.*

@Api("UserApi")
@RequestMapping("/user")
@ResponseBody
interface UserApi {

    @GetMapping("/{id}")
    fun get(@PathVariable("id") id: Long): UserDto?

    @PostMapping(consumes = [MediaType.APPLICATION_JSON_VALUE])
    fun create(@RequestBody userSavingDto: UserSavingDto?): UserDto?
}

