package org.salient.artvideoplayer.activity

import android.content.pm.ActivityInfo
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.widget.Toolbar
import kotlinx.android.synthetic.main.content_main.*
import org.salient.artplayer.VideoViewOld
import org.salient.artplayer.extend.Utils
import org.salient.artplayer.player.SystemMediaPlayer
import org.salient.artplayer.ui.VideoView
import org.salient.artvideoplayer.BaseActivity
import org.salient.artvideoplayer.R
import java.io.IOException

class MainActivity : BaseActivity() {
    private val videoViewOld: VideoViewOld? = null
    private var edUrl: EditText? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        edUrl = findViewById(R.id.edUrl)
        val toolbar = findViewById<View>(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)
        val videoView = findViewById<VideoView>(R.id.salientVideoView)
        val systemMediaPlayer = SystemMediaPlayer()
        try {
            systemMediaPlayer.impl.setDataSource(this, Uri.parse("http://vfx.mtime.cn/Video/2018/07/06/mp4/180706094003288023.mp4"))
        } catch (e: IOException) {
            e.printStackTrace()
        }
        videoView.mediaPlayer = systemMediaPlayer
        btn_start.setOnClickListener {
            //开始播放
//            videoView.bindSurface()
            videoView.prepare()
        }

        btn_fullscreen.setOnClickListener {
            //全屏
            if (!videoView.isPlaying) return@setOnClickListener
            val fullScreenVideoView = VideoView(this)

            fullScreenVideoView.mediaPlayer = systemMediaPlayer
            Utils.hideSupportActionBar(this)
            val decorView = findViewById<ViewGroup>(Window.ID_ANDROID_CONTENT)
            val lp = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
            decorView.addView(fullScreenVideoView, lp)

            val back = Button(this)
            back.text = "back"
            back.setOnClickListener {
                videoView.attach()
                Utils.setRequestedOrientation(this, ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)
                decorView.removeView(fullScreenVideoView)
                decorView.removeView(it)
            }

            decorView.addView(back, ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT))
            Utils.setRequestedOrientation(this, ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE)
        }

        //设置重力监听
//        MediaPlayerManager.INSTANCE.setOnOrientationChangeListener(new OrientationChangeListener());
//
//        // note : usage sample
//        videoView = findViewById(R.id.salientVideoView);
//        //optional: set ControlPanel
//        final ControlPanel controlPanel = new ControlPanel(this);
//        videoView.setControlPanel(controlPanel);
//        //optional: set title
//        TextView tvTitle = controlPanel.findViewById(R.id.tvTitle);
//        tvTitle.setText("西虹市首富 百变首富预告");
//        //required: set url
//        videoView.setUp("http://vfx.mtime.cn/Video/2018/07/06/mp4/180706094003288023.mp4");
//        //videoView.start();
//        //optional: set cover
//        Glide.with(MainActivity.this)
//                .load("http://img5.mtime.cn/mg/2018/07/06/093947.51483272.jpg")
//                .into((ImageView) controlPanel.findViewById(R.id.video_cover));
    }

    override fun onStart() {
        super.onStart()
    }

    override fun onBackPressed() {

        super.onBackPressed()
    }

    override fun onPause() {
        super.onPause()
        hideSoftInput()
        //        MediaPlayerManagerOld.INSTANCE.pause();
    }

    override fun onDestroy() {
        super.onDestroy()
        //        MediaPlayerManagerOld.INSTANCE.releasePlayerAndView(this);
    }

    fun onClick(view: View) {
        when (view.id) {
            R.id.play -> {
                val url = edUrl!!.text.toString()
                SystemMediaPlayer().also {
                    try {
                        it.impl.setDataSource(this, Uri.parse(url))
                    } catch (e: IOException) {
                        e.printStackTrace()
                    }
                }.let {
                    salientVideoView.mediaPlayer = it
                }
                salientVideoView.prepare()
            }
            R.id.fullWindow -> {
                hideSoftInput()
                val fullScreenVideoView = VideoView(this)
                val systemMediaPlayer = SystemMediaPlayer()
                try {
                    systemMediaPlayer.impl.setDataSource(this, Uri.parse("http://vfx.mtime.cn/Video/2018/06/29/mp4/180629124637890547.mp4"))
                } catch (e: IOException) {
                    e.printStackTrace()
                }
                fullScreenVideoView.mediaPlayer = systemMediaPlayer
                //开始播放
                fullScreenVideoView.prepare()

                Utils.hideSupportActionBar(this)
                val decorView = findViewById<ViewGroup>(Window.ID_ANDROID_CONTENT)
                val lp = ViewGroup.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
                decorView.addView(fullScreenVideoView, lp)
                Utils.setRequestedOrientation(this, ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE)

            }
        }
    }
}