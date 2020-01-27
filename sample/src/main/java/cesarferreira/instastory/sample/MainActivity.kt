package cesarferreira.instastory.sample

import android.os.Bundle
import android.view.View
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

        val imageUrl = "https://i.pinimg.com/564x/14/90/af/1490afa115fe062b12925c594d93a96c.jpg"
        val videoUrl = "https://flutter.github.io/assets-for-api-docs/assets/videos/butterfly.mp4"

        val listOfViews = listOf(
            StoryItem.Text("Hello", 5),
            StoryItem.RemoteImage(imageUrl, 5),
            StoryItem.Video(videoUrl, 5),
            StoryItem.LocalImage(R.drawable.tovelo, 5),
            StoryItem.CustomLayout(R.layout.custom_view, 5)
        )

        Story(
            applicationContext,
            container,
            listOfViews,
            object : StoryCallback {
                // TODO do i really need this? or can I just get a callback with the item
                override fun onNextCalled(
                    view: View,
                    story: Story,
                    index: Int
                ) {
                }

                override fun done() {
                    Toast.makeText(this@MainActivity, "Finished!", Toast.LENGTH_LONG).show()
                }
            }).start()
    }
}

