package cesarferreira.instastory

import android.view.View
import androidx.annotation.DrawableRes
import androidx.annotation.LayoutRes

sealed class StoryItem(open val durationInSeconds: Int) {
    lateinit var view: View
    data class Text(val text: String, override val durationInSeconds: Int) : StoryItem(durationInSeconds)
    data class RemoteImage(val imageUrl: String, override val durationInSeconds: Int) : StoryItem(durationInSeconds)
    data class LocalImage(@DrawableRes internal val drawable: Int, override val durationInSeconds: Int) : StoryItem(durationInSeconds)
    data class Video(val videoUrl: String) : StoryItem(10)
    data class CustomView(val customView: View, override val durationInSeconds: Int) : StoryItem(durationInSeconds)
    data class CustomLayout(@LayoutRes internal val layout: Int, override val durationInSeconds: Int) : StoryItem(durationInSeconds)
}
