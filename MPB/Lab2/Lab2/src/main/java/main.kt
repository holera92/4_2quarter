import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.cio.*
import io.ktor.client.features.logging.*
import io.ktor.client.request.*
import io.ktor.client.response.*
import io.ktor.client.statement.*
import io.ktor.client.statement.HttpResponse
import kotlinx.coroutines.runBlocking

val client = HttpClient(CIO) {
    install(Logging)
}

fun main() {
    runBlocking {

        val response: HttpResponse = client.request<HttpStatement>("https://evilinsult.com/generate_insult.php") {
            parameter("lang", "ru")
            parameter("type", "json")
        }.execute()

        println(response.receive() as String)

    }
}