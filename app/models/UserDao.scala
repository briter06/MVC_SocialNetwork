package models

import javax.inject.Inject

@javax.inject.Singleton
class UserDao @Inject()():

    private val users: List[User] = List(
        User("username1", "password1")
    )

    def getUser(username: String): Option[User] =
        val user = users.find(u => u.username == username)
        user
