import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull

fun String.toMediaTypeOrNull(): MediaType? {
    return try {
        this.toMediaTypeOrNull()
    } catch (e: IllegalArgumentException) {
        null
    }
}
