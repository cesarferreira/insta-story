package cesarferreira.instastory.sample

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import cesarferreira.instastory.InstaStory
import cesarferreira.instastory.StoryItem
import cesarferreira.instastory.callbacks.StoryCallback
import kotlinx.android.synthetic.main.activity_main.container

class MainActivity : AppCompatActivity() {

    lateinit var story: InstaStory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val imageUrl = "https://picsum.photos/720/1080"
        val videoUrl = "https://flutter.github.io/assets-for-api-docs/assets/videos/butterfly.mp4"

        val listOfViews = listOf(
            StoryItem.Text(text = "Hello", durationInSeconds = 5),
            StoryItem.RemoteImage(imageUrl = imageUrl, durationInSeconds = 5),
            StoryItem.RemoteImage(imageUrl = imageUrl, durationInSeconds = 2),
            StoryItem.RemoteImage(imageUrl = imageUrl, durationInSeconds = 5),
            StoryItem.Video(videoUrl = videoUrl),
            StoryItem.LocalImage(R.drawable.some_image, durationInSeconds = 5),
            StoryItem.CustomLayout(R.layout.custom_view, durationInSeconds = 5)
        )

        story = InstaStory(
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
            })
        story.start()
    }

    override fun onResume() {
        super.onResume()
        story.resume()
    }

    override fun onPause() {
        story.pause(false)
        super.onPause()
    }

    override fun onDestroy() {
        story.release()
        super.onDestroy()
    }
}

