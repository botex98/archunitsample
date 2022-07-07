package io.cloudflight.engineering.archunit.archunitsample.user.repository

import io.cloudflight.engineering.archunit.archunitsample.user.entity.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface UserRepository: JpaRepository<User, Long> {

}
