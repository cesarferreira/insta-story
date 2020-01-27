package cesarferreira.instastory.callbacks

import android.view.View
import cesarferreira.instastory.Story

interface StoryCallback {
    fun onNextCalled(view: View, story: Story, index: Int)
    fun done()
}
