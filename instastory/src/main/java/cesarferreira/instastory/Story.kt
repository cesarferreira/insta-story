package cesarferreira.instastory

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.media.MediaPlayer
import android.net.Uri
import android.view.GestureDetector
import android.view.GestureDetector.SimpleOnGestureListener
import android.view.Gravity
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.VideoView
import androidx.annotation.DrawableRes
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import cesarferreira.instastory.callbacks.ProgressTimeWatcher
import cesarferreira.instastory.callbacks.StoryCallback
import cesarferreira.instastory.utils.toPixel
import com.squareup.picasso.Callback
import com.squareup.picasso.MemoryPolicy
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.progress_story_view.view.currentlyDisplayedView
import kotlinx.android.synthetic.main.progress_story_view.view.leftLay
import kotlinx.android.synthetic.main.progress_story_view.view.linearProgressIndicatorLay
import kotlinx.android.synthetic.main.progress_story_view.view.loaderProgressbar
import kotlinx.android.synthetic.main.progress_story_view.view.rightLay

@SuppressLint("ViewConstructor")
class Story(
    context: Context,
    private val passedInContainerView: ViewGroup,
    private val storyItems: List<StoryItem>,
    private var storyCallback: StoryCallback,
    @DrawableRes private var mProgressDrawable: Int = R.drawable.white_lightgrey_drawable
) : ConstraintLayout(context) {
    private var currentlyShownIndex = 0
    private lateinit var currentView: StoryItem
    private var libSliderViewList = mutableListOf<MyProgressBar>()
    private lateinit var view: View
    private var pausedState: Boolean = false
    lateinit var gestureDetector: GestureDetector

    init {
        initView()
        init()
        storyItems.map { it.view = it.fillView(this.context) }
    }

    private fun StoryItem.fillView(context: Context): View {

        return when (this) {
            is StoryItem.Text -> {
                val textView = TextView(context)
                textView.text = text
                textView.textSize = 20f.toPixel(context).toFloat()
                textView.gravity = Gravity.CENTER
                textView.setTextColor(Color.parseColor("#ffffff"))

                textView
            }
            is StoryItem.RemoteImage -> ImageView(context)
            is StoryItem.Video -> VideoView(context)

            is StoryItem.LocalImage -> {
                ImageView(context).apply {
                    setImageDrawable(
                        ContextCompat.getDrawable(
                            context,
                            this@fillView.drawable
                        )
                    )
                }
            }
            is StoryItem.CustomView -> customView
            is StoryItem.CustomLayout -> LayoutInflater.from(
                context
            ).inflate(layout, null)
        }
    }

    private fun init() {
        storyItems.forEachIndexed { index, sliderView ->
            val myProgressBar = MyProgressBar(
                context,
                index,
                sliderView.durationInSeconds,
                object : ProgressTimeWatcher {
                    override fun onEnd(indexFinished: Int) {
                        currentlyShownIndex = indexFinished + 1
                        next()
                    }
                },
                mProgressDrawable
            )
            libSliderViewList.add(myProgressBar)
            view.linearProgressIndicatorLay.addView(myProgressBar)
        }
    }

    fun callPause(pause: Boolean) {
        try {
            if (pause) {
                if (!pausedState) {
                    this.pausedState = !pausedState
                    pause(false)
                }
            } else {
                if (pausedState) {
                    this.pausedState = !pausedState
                    resume()
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun initView() {
        view = View.inflate(
            context,
            R.layout.progress_story_view, this
        )
        val params = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.MATCH_PARENT
        )

        gestureDetector = GestureDetector(context, SingleTapConfirm())

        val touchListener = object : OnTouchListener {
            override fun onTouch(v: View?, event: MotionEvent?): Boolean {
                if (gestureDetector.onTouchEvent(event)) {
                    // single tap
                    if (v?.id == view.rightLay.id) {
                        next()
                    } else if (v?.id == view.leftLay.id) {
                        prev()
                    }
                    return true
                } else {
                    // your code for move and drag
                    return when (event?.action) {
                        MotionEvent.ACTION_DOWN -> {
                            callPause(true)
                            true
                        }

                        MotionEvent.ACTION_UP -> {
                            callPause(false)
                            true
                        }
                        else -> false
                    }
                }
            }
        }

        view.leftLay.setOnTouchListener(touchListener)
        view.rightLay.setOnTouchListener(touchListener)

        this.layoutParams = params
        passedInContainerView.addView(this)
    }

    private fun show() {
        view.loaderProgressbar.visibility = View.GONE
        if (currentlyShownIndex != 0) {
            for (i in 0..0.coerceAtLeast(currentlyShownIndex - 1)) {
                libSliderViewList[i].progress = 100
                libSliderViewList[i].cancelProgress()
            }
        }

        if (currentlyShownIndex != libSliderViewList.size - 1) {
            for (i in currentlyShownIndex + 1 until libSliderViewList.size) {
                libSliderViewList[i].progress = 0
                libSliderViewList[i].cancelProgress()
            }
        }

        currentView = storyItems[currentlyShownIndex]

        libSliderViewList[currentlyShownIndex].startProgress()


        when (currentView) {
            is StoryItem.RemoteImage -> {
                pause(true)
                loadRemoteImage((currentView as StoryItem.RemoteImage))
            }
            is StoryItem.Video -> {
                pause(true)
                playVideo(
                    currentlyShownIndex,
                    (currentView as StoryItem.Video)
                )
            }
        }
        storyCallback.onNextCalled(currentView, currentlyShownIndex)

        view.currentlyDisplayedView.removeAllViews()
        view.currentlyDisplayedView.addView(currentView.view)
        val params = LinearLayout.LayoutParams(
            LayoutParams.MATCH_PARENT,
            LayoutParams.MATCH_PARENT, 1f
        )

        if (currentView.view is ImageView) {
            (currentView.view as ImageView).scaleType = ImageView.ScaleType.FIT_CENTER
            (currentView.view as ImageView).adjustViewBounds = true
        }
        currentView.view.layoutParams = params
    }

    private fun loadRemoteImage(remoteImage: StoryItem.RemoteImage) {
        Picasso.get()
            .load(remoteImage.imageUrl)
            .memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE)
            .into(remoteImage.view as ImageView, object : Callback {
                override fun onSuccess() {
                    resume()
                }

                override fun onError(e: Exception?) {
                    e?.printStackTrace()
                }
            })
    }

    fun start() {
        show()
    }

    private fun playVideo(
        index: Int,
        videoStoryItem: StoryItem.Video
    ) {
        val uri = Uri.parse(videoStoryItem.videoUrl)
        val videoView = videoStoryItem.view as VideoView

        videoView.setVideoURI(uri)

        videoView.requestFocus()
        videoView.start()

        videoView.setOnInfoListener(object : MediaPlayer.OnInfoListener {
            override fun onInfo(mp: MediaPlayer?, what: Int, extra: Int): Boolean {
                if (what == MediaPlayer.MEDIA_INFO_VIDEO_RENDERING_START) {
                    editDurationAndResume(index, videoView.duration / 1000)
                    return true
                }
                return false
            }
        })
    }

    fun editDurationAndResume(index: Int, newDurationInSecons: Int) {
        view.loaderProgressbar.visibility = View.GONE
        libSliderViewList[index].editDurationAndResume(newDurationInSecons)
    }

    fun pause(withLoader: Boolean) {
        if (withLoader) {
            view.loaderProgressbar.visibility = View.VISIBLE
        }
        libSliderViewList[currentlyShownIndex].pauseProgress()
        if (storyItems[currentlyShownIndex] is StoryItem.Video) {
            (storyItems[currentlyShownIndex].view as VideoView).pause()
        }
    }

    fun resume() {
        view.loaderProgressbar.visibility = View.GONE
        libSliderViewList[currentlyShownIndex].resumeProgress()
        if (storyItems[currentlyShownIndex] is StoryItem.Video) {
            (storyItems[currentlyShownIndex].view as VideoView).start()
        }
    }

    private fun stop() {
    }

    fun next() {
        try {
            if (currentView == storyItems[currentlyShownIndex]) {
                currentlyShownIndex++
                if (storyItems.size <= currentlyShownIndex) {
                    finish()
                    return
                }
            }
            show()
        } catch (e: IndexOutOfBoundsException) {
            finish()
        }
    }

    private fun finish() {
        storyCallback.done()
        for (progressBar in libSliderViewList) {
            progressBar.cancelProgress()
            progressBar.progress = 100
        }
    }

    fun prev() {
        try {
            if (currentView == storyItems[currentlyShownIndex]) {
                currentlyShownIndex--
                if (0 > currentlyShownIndex) {
                    currentlyShownIndex = 0
                }
            }
        } catch (e: IndexOutOfBoundsException) {
            currentlyShownIndex -= 2
        } finally {
            show()
        }
    }

    private inner class SingleTapConfirm : SimpleOnGestureListener() {

        override fun onSingleTapUp(event: MotionEvent): Boolean {
            return true
        }
    }
}