package io.cloudflight.engineering.archunit.archunitsample.user.service.impl

import io.cloudflight.engineering.archunit.archunitsample.user.api.dto.UserDto
import io.cloudflight.engineering.archunit.archunitsample.user.api.dto.UserSavingDto
import io.cloudflight.engineering.archunit.archunitsample.user.entity.User
import io.cloudflight.engineering.archunit.archunitsample.user.repository.UserRepository
import io.cloudflight.engineering.archunit.archunitsample.user.service.UserService
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class UserServiceImpl(
    private val userRepository: UserRepository
) : UserService {

    @Transactional(readOnly = true)
    override fun findById(id: Long): UserDto? {
        val user = userRepository.findById(id).orElse(null)
        return userToUserDto(user)
    }

    @Transactional
    override fun create(userSavingDto: UserSavingDto?): UserDto? {
        val user = userSavingDtoToUser(userSavingDto)
        val savedUser = userRepository.save(user)
        return userToUserDto(savedUser)
    }

    private fun userToUserDto(user: User?): UserDto {
        return UserDto(user?.id, user?.username, user?.email)
    }

    private fun userSavingDtoToUser(userSavingDto: UserSavingDto?): User {
        return User(
            id = null,
            username = userSavingDto?.username ?: "",
            email = userSavingDto?.email ?: "",
            externalId = userSavingDto?.externalId ?: 0L
        )
    }
}
