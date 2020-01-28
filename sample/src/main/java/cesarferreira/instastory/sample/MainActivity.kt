package cesarferreira.instastory.sample

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import cesarferreira.instastory.Story
import cesarferreira.instastory.StoryItem
import cesarferreira.instastory.callbacks.StoryCallback
import kotlinx.android.synthetic.main.activity_main.container

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val imageUrl = "https://picsum.photos/720/1080"
        val videoUrl = "https://flutter.github.io/assets-for-api-docs/assets/videos/butterfly.mp4"

        val listOfViews = listOf(
            StoryItem.Text("Hello", 5),
            StoryItem.RemoteImage(imageUrl, 5),
            StoryItem.RemoteImage(imageUrl, 2),
            StoryItem.RemoteImage(imageUrl, 5),
            StoryItem.Video(videoUrl),
            StoryItem.LocalImage(R.drawable.tovelo, 5),
            StoryItem.CustomLayout(R.layout.custom_view, 5)
        )

        Story(
            applicationContext,
            container,
            listOfViews,
            object : StoryCallback {

                override fun onNextCalled(storyItem: StoryItem, index: Int) {
                    Log.i("StoryItem", "onNext: $storyItem")
                }

                override fun done() {
                    Toast.makeText(this@MainActivity, "Finished!", Toast.LENGTH_LONG).show()
                }
            }).start()
    }
}

