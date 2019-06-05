package com.example.framgianguyenvanthanhd.music_professional.screens.home.playlist_top.detail_song

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.View
import com.example.framgianguyenvanthanhd.music_professional.R
import com.example.framgianguyenvanthanhd.music_professional.Utils.*
import com.example.framgianguyenvanthanhd.music_professional.data.model.Playlist
import com.example.framgianguyenvanthanhd.music_professional.data.model.Song
import com.example.framgianguyenvanthanhd.music_professional.data.model.SongPlaying
import com.example.framgianguyenvanthanhd.music_professional.data.repository.PlaylistHomeRepository
import com.example.framgianguyenvanthanhd.music_professional.data.repository.PlaylistPersonalRepository
import com.example.framgianguyenvanthanhd.music_professional.data.repository.SongParameterRepository
import com.example.framgianguyenvanthanhd.music_professional.data.repository.SongPlayingRepository
import com.example.framgianguyenvanthanhd.music_professional.screens.home.common.DetailSongAdapter
import com.example.framgianguyenvanthanhd.music_professional.screens.personal.playlist.add_song.PlaylistForAddActivity
import com.example.framgianguyenvanthanhd.music_professional.service.MediaService
import com.github.rubensousa.bottomsheetbuilder.BottomSheetBuilder
import com.github.rubensousa.bottomsheetbuilder.adapter.BottomSheetItemClickListener
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.layout_detail_songs.*
import java.util.*


/**
 * Created by admin on 10/31/2018.
 */
class DetailPlaylistActivity : AppCompatActivity(), DetailPlaylistContract.DetailPlaylistView,
DetailSongAdapter.OnItemSongClickListener{

    private lateinit var presenter: DetailPlaylistContract.DetailPlPresenter
    private lateinit var adapter : DetailSongAdapter
    private  var isRemoveable = false
    private lateinit var idPlaylist: String

    companion object {
        @JvmStatic
        fun getInstance(context: Context, playlist: Playlist): Intent {
            val intent = Intent(context, DetailPlaylistActivity::class.java)
            intent.action = Constants.PLAYLIST_ACTION
            intent.putExtra(Constants.PLAYLIST_EXTRA, playlist)
            return intent
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_detail_songs)

        initView()
        initData()
    }

    @SuppressLint("ResourceType")
    private fun initView() {
        setSupportActionBar(toolbar_detail)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_back_24dp)
        toolbar_detail.setNavigationOnClickListener { view ->
            finish()
        }
        collapsing_toolbar.setExpandedTitleColor(Color.WHITE)
        collapsing_toolbar.setCollapsedTitleTextAppearance(Color.WHITE)
    }

    private fun initData() {
        presenter = DetailPlaylistPresenter(
                PlaylistHomeRepository.getInstance(),
                SongParameterRepository.getInstance(),
                SongPlayingRepository.getInstance(this),
                PlaylistPersonalRepository.getInstance(),
                this)
        presenter.setView(this)
        presenter.onStart()
        val playlist: Playlist? = intent.getSerializableExtra(Constants.PLAYLIST_EXTRA) as Playlist
        idPlaylist = playlist?.idPlaylist ?:"-1"
        isRemoveable = playlist?.idAccount != "1"
        playlist?.let {
            if (it.background != null){
                Picasso.with(baseContext).load(playlist.background).fit().into(backdrop)
            } else {
                Picasso.with(baseContext).load(R.drawable.img_playlist).fit().into(backdrop)
            }
            Picasso.with(baseContext).load(playlist.image).into(image_detail)
            collapsing_toolbar.title = playlist.name
            collapsing_toolbar.setCollapsedTitleTextColor(Color.WHITE)
            presenter.fetchDetailPlaylist(playlist.idPlaylist.orEmpty())
        }
        rc_detail_songs?.layoutManager = LinearLayoutManager(this,LinearLayoutManager.VERTICAL, false)
        rc_detail_songs?.addItemDecoration(DividerItemDecoration(this, LinearLayoutManager.HORIZONTAL))
        swipe_refresh?.setOnRefreshListener {
            swipe_refresh?.isRefreshing = true
            presenter.fetchDetailPlaylist(playlist?.idPlaylist.orEmpty())
        }
    }

    override fun setPresenter(presenter: DetailPlaylistContract.DetailPlPresenter) {
        this.presenter = presenter
    }

    override fun loadSuccessfully(list: List<Song>) {
        progress_isloading?.visibility = View.INVISIBLE
        swipe_refresh?.isRefreshing = false
        adapter = DetailSongAdapter(list.toMutableList(), this)
        rc_detail_songs?.adapter = adapter
    }

    override fun loadError(t: Throwable) {
        swipe_refresh?.isRefreshing = false
        progress_isloading?.visibility = View.INVISIBLE
    }

    override fun onItemSongClick(song: Song) {
        presenter.updatePlaySong(song.idSong.toString())
        val songPlaying = SongPlaying(song.idSong.toString(), song.name
                ?: "", song.nameSinger ?: "", song.image, song.link?:"")
        startService(MediaService.getInstance(this, songPlaying, 0))
    }

    override fun onMoreBtnClick(song: Song) {
        val dialog = BottomSheetBuilder(this, R.style.AppTheme_BottomSheetDialog)
        dialog.setMode(BottomSheetBuilder.MODE_LIST)
        dialog.addItem(0,song.name, null)
        dialog.addItem(MenuBottomSheet.ADD_PLAYING.id, MenuBottomSheet.ADD_PLAYING.title, MenuBottomSheet.ADD_PLAYING.icon)
        dialog.addItem(MenuBottomSheet.ADD_FAVORITE.id, MenuBottomSheet.ADD_FAVORITE.title, MenuBottomSheet.ADD_FAVORITE.icon)
        if (!isRemoveable){
            dialog.addItem(MenuBottomSheet.ADD_PLAYLIST.id, MenuBottomSheet.ADD_PLAYLIST.title, MenuBottomSheet.ADD_PLAYLIST.icon)
        } else {
            dialog.addItem(MenuBottomSheet.DELETE.id, MenuBottomSheet.DELETE.title, MenuBottomSheet.DELETE.icon)
        }
        dialog.setItemClickListener(BottomSheetItemClickListener { item->
                    when(item.itemId) {
                        MenuBottomSheet.ADD_PLAYING.id -> {
                            val songPlaying = SongPlaying(
                                    song.idSong.toString(),
                                    song.name ?: "",
                                    song.nameSinger ?: "",
                                    song.image,
                                    song.link ?: "-1"
                            )
                            presenter.insertToPlaying(songPlaying)
                        }

                        MenuBottomSheet.ADD_FAVORITE.id -> {
                            if (SharedPrefs.getInstance().get(KeysPref.USER_NAME.name, String::class.java).isEmpty()) {
                                DialogUtils.createDialogConfirm(
                                        this,
                                        "Lỗi",
                                        "Hãy đăng nhập để sử dụng tính năng này",
                                        object: DialogUtils.OnDialogClick{
                                            override fun onClickOk() {
                                                return
                                            }

                                            override fun onCancel() {

                                            }
                                        }
                                )
                                return@BottomSheetItemClickListener
                            }
                            presenter.updateLikeSong(song.idSong.toString())
                        }

                        MenuBottomSheet.ADD_PLAYLIST.id -> {
                            if (SharedPrefs.getInstance().get(KeysPref.USER_NAME.name, String::class.java).isEmpty()) {
                                DialogUtils.createDialogConfirm(
                                        this,
                                        "Lỗi",
                                        "Hãy đăng nhập để sử dụng tính năng này",
                                        object : DialogUtils.OnDialogClick {
                                            override fun onClickOk() {
                                                return
                                            }

                                            override fun onCancel() {

                                            }
                                        }
                                )
                                return@BottomSheetItemClickListener
                            }
                            startActivity(PlaylistForAddActivity.getInstance(
                                    this,
                                    song.idSong.toString()))
                        }

                        MenuBottomSheet.DELETE.id -> {
                            presenter.deleteSongFromPlaylist(song.idSong.toString(), idPlaylist )
                            progress_isloading?.visibility = View.VISIBLE
                        }
                    }
                })
                dialog.createDialog().show()
    }

    override fun deleteSongSuccess(idSong: String) {
        progress_isloading?.visibility = View.INVISIBLE
        adapter.removeSong(idSong)
    }

    override fun deleteSongFail() {
        progress_isloading?.visibility = View.INVISIBLE
    }
}
