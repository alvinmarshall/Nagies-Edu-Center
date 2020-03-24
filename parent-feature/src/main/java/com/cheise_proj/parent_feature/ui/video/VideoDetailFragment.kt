package com.cheise_proj.parent_feature.ui.video

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.cheise_proj.parent_feature.R
import com.google.android.exoplayer2.ExoPlayerFactory
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector
import com.google.android.exoplayer2.ui.AspectRatioFrameLayout
import com.google.android.exoplayer2.upstream.DataSource
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.google.android.exoplayer2.util.Util
import kotlinx.android.synthetic.main.fragment_video_detail.*

/**
 * A simple [Fragment] subclass.
 */
class VideoDetailFragment : Fragment() {

    private val args: VideoDetailFragmentArgs by navArgs()
    private var simpleExoPlayer: SimpleExoPlayer? = null
    private var playUrl: String? = null
    private var shouldAutoPlay: Boolean = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_video_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        playUrl = args.videoUrl
    }

    private fun initializePlayer() {
        activity?.window?.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        );
        val bandwidthMeter = DefaultBandwidthMeter()
        val videoTrackSelectionFactory = AdaptiveTrackSelection.Factory(bandwidthMeter)
        val trackSelector = DefaultTrackSelector(videoTrackSelectionFactory)

        val mediaDataSourceFactory: DataSource.Factory
        simpleExoPlayer = ExoPlayerFactory.newSimpleInstance(context!!, trackSelector)

        mediaDataSourceFactory =
            DefaultDataSourceFactory(context, Util.getUserAgent(context!!, "mediaPlayerSample"))
        val mediaSource = ProgressiveMediaSource.Factory(mediaDataSourceFactory).createMediaSource(
            Uri.parse(playUrl)
        )

        simpleExoPlayer?.prepare(mediaSource, false, false)
        simpleExoPlayer?.playWhenReady = true
        player_view.apply {
            requestFocus()
            resizeMode = AspectRatioFrameLayout.RESIZE_MODE_FILL
            player = simpleExoPlayer
        }
    }

    private fun releasePlayer() {
        simpleExoPlayer?.release()
        shouldAutoPlay = simpleExoPlayer?.playWhenReady!!
    }

    override fun onPause() {
        releasePlayer()
        super.onPause()
    }

    override fun onStop() {
        (activity as AppCompatActivity).supportActionBar?.show()
        super.onStop()
    }

    override fun onResume() {
        (activity as AppCompatActivity).supportActionBar?.hide()
        initializePlayer()
        super.onResume()
    }

}
