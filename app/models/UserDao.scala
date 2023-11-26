package models

import javax.inject.Inject

@javax.inject.Singleton
class UserDao @Inject()():

    private var users: List[User] = List(
        User("briter06", "briter06"),
        User("starship", "starship"),
        User("mavensun", "mavensun"),
        User("oldeuboi", "oldeuboi"),
        User("ranmaven", "ranmaven"),
        User("loghydra", "loghydra"),
        User("biscuits", "biscuits"),
        User("muttonmine", "muttonmine"),
        User("volleyball", "volleyball"),
        User("cymbalsong", "cymbalsong"),
        User("grapefruit", "grapefruit"),
        User("novavalley", "novavalley"),
        User("gandalfneo", "gandalfneo"),
        User("teaiok-1", "teaiok-1"),
        User("baseball", "baseball"),
        User("icestorm", "icestorm"),
        User("flycedar", "flycedar"),
        User("ranorgan", "ranorgan")
    )

    def getUser(username: String): Option[User] =
        val user = users.find(u => u.username == username)
        user

    def createUser(username: String, password: String) =
        val user = User(username, password)
        users = user :: users