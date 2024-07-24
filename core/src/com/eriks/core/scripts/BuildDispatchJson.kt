import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.io.InputStream

@Serializable
data class Match(
    val id: String,
    val p1: String,
    val p2: String,
    val p3: String,
    val p4: String,
    val p5: String,
    val map: String,
    val date: String,
    val result: String
)

@Serializable
data class Pack(
    val id: String,
    val players: List<String>
)

@Serializable
data class PackList(
    val packList: List<Pack>
)

fun readMatchesFromFile(fileName: String): List<Match> {
    val matches = mutableListOf<Match>()
    val inputStream: InputStream? = Thread.currentThread().contextClassLoader.getResourceAsStream(fileName)

    inputStream?.bufferedReader()?.forEachLine { line ->
        // Skip the header line
        if (line.startsWith("Match id")) return@forEachLine

        // Split the line by tabs or spaces
        val values = line.split(Regex("\\s+"))

        if (values.size == 11) {
            val match = Match(
                id = values[0],
                p1 = values[1],
                p2 = values[2],
                p3 = values[3],
                p4 = values[4],
                p5 = values[5],
                map = values[6],
                date = values[7],
                result = values[8]
            )
            matches.add(match)
//            if (values[8] == "VITORIA") {
//                val match = Match(
//                    id = values[0]+"-V",
//                    p1 = values[1],
//                    p2 = values[2],
//                    p3 = values[3],
//                    p4 = values[4],
//                    p5 = values[5],
//                    map = values[6],
//                    date = values[7],
//                    result = values[8]
//                )
//                matches.add(match)
//            }
        }
    } ?: println("File not found: $fileName")

    return matches
}

fun matchesToJsonFile(matches: List<Match>, filePath: String) {
    val packs = matches.map { match ->
        Pack(
            id = match.id,
            players = listOf(match.p1, match.p2, match.p3, match.p4, match.p5)
        )
    }

    val packList = PackList(packs)
    val jsonString = Json.encodeToString(packList)
    println(jsonString)
//    File(filePath).writeText(jsonString)
}

fun main() {
    val filePath = "matches.txt"
    val matches = readMatchesFromFile(filePath)

    matches.forEach { println(it) }

    val jsonFilePath = "com/eriks/core/scripts/matches.json"
    matchesToJsonFile(matches, jsonFilePath)
}
