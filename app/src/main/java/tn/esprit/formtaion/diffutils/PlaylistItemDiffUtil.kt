package  tn.esprit.formtaion.diffutils



import androidx.recyclerview.widget.DiffUtil
import tn.esprit.formtaion.model.PlaylistYtModel
import tn.esprit.formtaion.model.VideoYtModel

class PlaylistItemDiffUtil(
    private val oldList: List<PlaylistYtModel.PlaylistItem>,
    private val newList: List<PlaylistYtModel.PlaylistItem>
) : DiffUtil.Callback() {
    override fun getOldListSize(): Int {
        return oldList.size
    }

    override fun getNewListSize(): Int {
        return newList.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition] == newList[newItemPosition]
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldVideo = oldList[oldItemPosition]
        val newVideo = newList[newItemPosition]
        return oldVideo.snippetYt.title == newVideo.snippetYt.title
    }
}