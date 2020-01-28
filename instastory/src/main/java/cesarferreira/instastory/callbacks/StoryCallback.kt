package cesarferreira.instastory.callbacks

import cesarferreira.instastory.StoryItem

interface StoryCallback {
    fun onNextCalled(storyItem: StoryItem, index: Int)
    fun done()
}
