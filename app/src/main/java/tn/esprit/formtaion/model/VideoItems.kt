package  tn.esprit.formtaion.model

data class VideoItems(
    val id: String,
    val title: String,
    // Other properties related to a video
)
data class VideoNamesResponse(val videoFiles: List<String>)

data class VideoDetailsResponse(val videoName: String, /* Add other details as needed */)

