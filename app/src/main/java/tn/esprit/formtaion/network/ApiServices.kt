package  tn.esprit.formtaion.network


import tn.esprit.formtaion.model.ChannelModel
import tn.esprit.formtaion.model.PlaylistItemsModel
import tn.esprit.formtaion.model.PlaylistYtModel
import tn.esprit.formtaion.model.VideoYtModel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiServices {

    @GET("channels")
    fun getChannel(
        @Query("part") part: String,
        @Query("id") id: String
    ) : Call<ChannelModel>
    @GET("search")
    fun getVideo(
        @Query("part") part: String,
        @Query("channelId") channelId: String,
        @Query("order") order: String,
        @Query("pageToken") pageToken: String?,
        @Query("q") query: String?
    ) : Call<VideoYtModel>

    @GET("playlists")
    fun getPlaylist(
        @Query("part") part: String,
        @Query("channelId") channelId: String,
        @Query("maxResults") maxResults: String,
        @Query("pageToken") pageToken: String?
    ) : Call<PlaylistYtModel>

    @GET("playlistItems")
    fun getPlaylistItems(
        @Query("part") part: String,
        @Query("playlistId") playlist: String,
        @Query("pageToken") pageToken: String?
    ) : Call<PlaylistItemsModel>

}

