package models
import javax.inject.Inject
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@javax.inject.Singleton
class PostDao @Inject()():

    private var posts: List[Post] = List(
        Post(
            "1",
            "teaiok-1",
            "/assets/images/eiffel_tower.jpg",
            "2023-08-10",
            // Description obtained from https://en.wikipedia.org/wiki/Eiffel_Tower
            "The Eiffel Tower is a wrought-iron lattice tower on the Champ de Mars in Paris, France. It is named after the engineer Gustave Eiffel, whose company designed and built the tower from 1887 to 1889.\n\nLocally nicknamed \"La dame de fer\" (French for \"Iron Lady\"), it was constructed as the centerpiece of the 1889 World's Fair, and to crown the centennial anniversary of the French Revolution. Although initially criticised by some of France's leading artists and intellectuals for its design, it has since become a global cultural icon of France and one of the most recognisable structures in the world. The tower received 5,889,000 visitors in 2022. The Eiffel Tower is the most visited monument with an entrance fee in the world: 6.91 million people ascended it in 2015. It was designated a monument historique in 1964, and was named part of a UNESCO World Heritage Site (\"Paris, Banks of the Seine\") in 1991.",
            List(
                "starship",
                "mavensun",
                "oldeuboi",
                "ranmaven",
                "loghydra",
                "biscuits",
                "muttonmine",
                "volleyball",
                "cymbalsong",
                "grapefruit",
                "novavalley",
                "gandalfneo"
            ),
            List(
                Comment(
                    "mavensun",
                    "I loved that place"
                ),
                Comment(
                    "ranmaven",
                    "I don't know. It's overrated"
                ),
                Comment(
                    "gandalfneo",
                    "I've lived here for 5 years now and it's the best place in the world"
                ),
                Comment(
                    "muttonmine",
                    "It was too expensive to go to the top"
                ),
                Comment(
                    "volleyball",
                    "I love french food!!"
                ),
                Comment(
                    "biscuits",
                    "The night lights are awesome"
                )
            )
        ),
        Post(
            "2",
            "oldeuboi",
            "/assets/images/roman_colosseum.jpg",
            "2023-05-05",
            // Description obtained from https://en.wikipedia.org/wiki/Colosseum
            "The Colosseum is an elliptical amphitheatre in the centre of the city of Rome, Italy, just east of the Roman Forum. It is the largest ancient amphitheatre ever built, and is still the largest standing amphitheatre in the world, despite its age. Construction began under the emperor Vespasian in 72 and was completed in AD 80 under his successor and heir, Titus. Further modifications were made during the reign of Domitian. The three emperors who were patrons of the work are known as the Flavian dynasty, and the amphitheatre was named the Flavian Amphitheatre by later classicists and archaeologists for its association with their family name (Flavius).",
            List(
                "teaiok-1",
                "baseball",
                "icestorm",
                "flycedar",
                "ranorgan",
                "starship"
            ),
            List(
                Comment(
                    "icestorm",
                    "It's incredible how this place was used"
                )
            )
        ),
        Post(
            "3",
            "briter06",
            "/assets/images/atomium.jpg",
            "2023-09-12",
            // Description obtained from https://en.wikipedia.org/wiki/Atomium
            "The Atomium is a landmark modernist building in Brussels, Belgium, originally constructed as the centrepiece of the 1958 Brussels World's Fair (Expo 58). Designed by the engineer André Waterkeyn and the architects André and Jean Polak as a tribute to scientific progress, as well as to symbolise Belgian engineering skills at the time, it is located on the Heysel/Heizel Plateau in Laeken (northern part of the City of Brussels), where the exhibition took place. It is the city's most popular tourist attraction, and serves as a museum, an art centre and a cultural destination",
            List(
                "teaiok-1",
                "baseball",
                "icestorm",
                "flycedar",
                "ranorgan",
                "starship",
                "grapefruit",
                "novavalley",
                "gandalfneo"
            ),
            List(
                Comment(
                  "teaiok-1",
                  "I totally love Brussels"
                ),
                Comment(
                    "gandalfneo",
                    "Beautiful city"
                ),
                Comment(
                    "starship",
                    "The view is amazing"
                )
            )
        )
    )

    def getPosts(sortBy: String, asc: Boolean): List[Post] =
        sortBy match
            case "date" => posts.sortBy(_.creationDate)(if(asc) Ordering[String] else Ordering[String].reverse)
            case "likes" => posts.sortBy(_.likesNumber)(if(asc) Ordering[Int] else Ordering[Int].reverse)

    def getPost(id: String): Option[Post] =
        posts.find(p => p.id == id)

    def createPost(id: String, username: String, photoPath: String, description: String) =
        posts = Post(
            id,
            username,
            photoPath,
            LocalDateTime
              .now()
              .format(DateTimeFormatter.ofPattern("yyyy-MM-dd")),
            description,
            List(),
            List()
        ) :: posts
