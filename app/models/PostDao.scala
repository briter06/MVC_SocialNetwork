package models

import javax.inject.Inject

@javax.inject.Singleton
class PostDao @Inject()():

    private val posts: List[Post] = List(
        Post(
            1,
            "username1",
            "https://wallpapercave.com/wp/wp2033191.jpg",
            "2023-10-10",
            "Photo description 1 Photo description 1 Photo description 1 Photo description 1 Photo description 1 Photo description 1 Photo description 1 Photo description 1 Photo description 1 Photo description 1 Photo description 1 Photo description 1 Photo description 1 Photo description 1 Photo description 1 Photo description 1 Photo description 1 Photo description 1 Photo description 1 Photo description 1 Photo description 1 Photo description 1 Photo description 1 Photo description 1 Photo description 1 Photo description 1 Photo description 1 Photo description 1 Photo description 1 Photo description 1 Photo description 1 Photo description 1 Photo description 1 Photo description 1 Photo description 1 Photo description 1 Photo description 1 Photo description 1 Photo description 1 Photo description 1 Photo description 1 Photo description 1 Photo description 1 Photo description 1 Photo description 1 Photo description 1 Photo description 1 Photo description 1 Photo description 1 Photo description 1 Photo description 1 Photo description 1 Photo description 1 Photo description 1 Photo description 1 Photo description 1 Photo description 1 Photo description 1 Photo description 1 Photo description 1 Photo description 1 Photo description 1 Photo description 1 Photo description 1 Photo description 1 Photo description 1 Photo description 1 Photo description 1 Photo description 1 Photo description 1 Photo description 1 Photo description 1 Photo description 1 Photo description 1 Photo description 1 Photo description 1 Photo description 1 Photo description 1 Photo description 1 Photo description 1 Photo description 1 Photo description 1 Photo description 1 Photo description 1 Photo description 1 Photo description 1 Photo description 1 Photo description 1 Photo description 1 Photo description 1 Photo description 1 Photo description 1 Photo description 1 Photo description 1 Photo description 1 Photo description 1 Photo description 1 Photo description 1 Photo description 1 Photo description 1 Photo description 1 Photo description 1 Photo description 1 Photo description 1 Photo description 1 Photo description 1 Photo description 1 Photo description 1 Photo description 1 Photo description 1 Photo description 1 Photo description 1 Photo description 1 Photo description 1 Photo description 1 Photo description 1 Photo description 1 Photo description 1 Photo description 1 Photo description 1 Photo description 1 Photo description 1 Photo description 1 Photo description 1 Photo description 1 Photo description 1 Photo description 1 Photo description 1 Photo description 1 Photo description 1 Photo description 1 Photo description 1 Photo description 1 Photo description 1 Photo description 1 Photo description 1 Photo description 1 Photo description 1 Photo description 1 Photo description 1 v",
            List(
                "username1"
            ),
            List(
                Comment(
                    "username2",
                    "Awesome"
                ),
                Comment(
                    "username3",
                    "Meh"
                )
            )
        ),
        Post(
            2,
            "username2",
            "https://images8.alphacoders.com/131/1313380.png",
            "2023-05-05",
            "Photo description 2",
            List(),
            List()
        )
    )

    def getPosts(sortBy: String, asc: Boolean): List[Post] =
        sortBy match
            case "date" => posts.sortBy(_.creationDate)(if(asc) Ordering[String] else Ordering[String].reverse)
            case "likes" => posts.sortBy(_.likesNumber)(if(asc) Ordering[Int] else Ordering[Int].reverse)

    def getPost(id: Int): Option[Post] =
        posts.find(p => p.id == id)

    def getPost(username: String) : Option[Post] =
        posts.find(p => p.username == username)
