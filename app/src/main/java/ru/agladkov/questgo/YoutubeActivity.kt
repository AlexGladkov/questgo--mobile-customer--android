package ru.agladkov.questgo

import android.os.Bundle
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import com.google.android.youtube.player.YouTubeBaseActivity
import com.google.android.youtube.player.YouTubeInitializationResult
import com.google.android.youtube.player.YouTubePlayer
import kotlinx.android.synthetic.main.activity_youtube.*
import ru.agladkov.questgo.common.models.VideoCellModel

class YoutubeActivity : YouTubeBaseActivity(), YouTubePlayer.OnInitializedListener {
    private val YOUTUBE_API_KEY = "AIzaSyCXRRLplWiUBc2eoA3-jGzPy2dFVPmp1eM"
    private var model: VideoCellModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_youtube)
        model = intent.extras?.get(modelKey) as? VideoCellModel

        youtubeVideoView.initialize(YOUTUBE_API_KEY, this)
    }

    override fun onInitializationSuccess(
        provider: YouTubePlayer.Provider?,
        player: YouTubePlayer?,
        wasRestored: Boolean
    ) {
        model?.let {
            if (!wasRestored) {
                player?.cueVideo(it.value)
            }
        }
    }

    override fun onInitializationFailure(
        p0: YouTubePlayer.Provider?,
        p1: YouTubeInitializationResult?
    ) {

    }

    companion object {
        const val modelKey = "MODEL_KEY"
    }
}