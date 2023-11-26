package models

import javax.inject.Inject

/**
 * DAO of the User model
 */
@javax.inject.Singleton
class UserDao @Inject()():

    /**
     * In memory database for the users
     */
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

    /**
     * Get a user based on the username
     * @param username - Username of the user
     * @return - User with that username
     */
    def getUser(username: String): Option[User] =
        val user = users.find(u => u.username == username)
        user

    /**
     * Create a new user
     * @param username - Username of the new user
     * @param password - Password of the new user
     */
    def createUser(username: String, password: String): Unit =
        val user = User(username, password)
        users = user :: users