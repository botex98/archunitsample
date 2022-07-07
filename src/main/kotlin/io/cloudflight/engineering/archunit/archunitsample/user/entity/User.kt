package io.cloudflight.engineering.archunit.archunitsample.user.entity

import javax.persistence.*

@Entity
data class User(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: Long?,
    val username: String,
    @Column(unique=true)
    val email: String,
    val externalId: Long,
)
