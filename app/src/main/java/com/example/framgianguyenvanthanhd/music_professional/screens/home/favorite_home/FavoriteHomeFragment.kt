package com.example.framgianguyenvanthanhd.music_professional.screens.home.favorite_home

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.framgianguyenvanthanhd.music_professional.R
import com.example.framgianguyenvanthanhd.music_professional.Utils.*
import com.example.framgianguyenvanthanhd.music_professional.data.model.SongHome
import com.example.framgianguyenvanthanhd.music_professional.data.model.SongPlaying
import com.example.framgianguyenvanthanhd.music_professional.data.repository.FavoriteRepository
import com.example.framgianguyenvanthanhd.music_professional.data.repository.SongParameterRepository
import com.example.framgianguyenvanthanhd.music_professional.data.repository.SongPlayingRepository
import com.example.framgianguyenvanthanhd.music_professional.screens.OnFragmentManager
import com.example.framgianguyenvanthanhd.music_professional.screens.OnUpdateDataPlayingListener
import com.example.framgianguyenvanthanhd.music_professional.screens.home.common.SongHomeAdapter
import com.example.framgianguyenvanthanhd.music_professional.screens.home.common.song_home_detail.SongHomeDetailActivity
import com.example.framgianguyenvanthanhd.music_professional.service.MediaService
import com.github.rubensousa.bottomsheetbuilder.BottomSheetBuilder
import com.github.rubensousa.bottomsheetbuilder.adapter.BottomSheetItemClickListener
import kotlinx.android.synthetic.main.fragment_favorite_home.*

/**
 * Created by admin on 10/27/2018.
 */
class FavoriteHomeFragment : Fragment(), FavoriteHomeContract.View, View.OnClickListener, SongHomeAdapter.OnItemSongHomeClickListener {
    private lateinit var presenter: FavoriteHomeContract.Presenter
    private lateinit var mAdapter: SongHomeAdapter

    private lateinit var listener: OnUpdateDataPlayingListener
    private lateinit var listenerInsertSongPlaylist: OnFragmentManager

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (context is OnUpdateDataPlayingListener) {
            listener = context as OnUpdateDataPlayingListener
        } else {
            throw Throwable("do not attach")
        }

        if (context is OnFragmentManager){
            listenerInsertSongPlaylist=  context as OnFragmentManager
        } else {
            throw RuntimeException()
        }
    }

    override fun setPresenter(presenter: FavoriteHomeContract.Presenter) {
        this.presenter = presenter
    }

    override fun favoriteSongsSuccessfully(songHomeSongs: List<SongHome>) {
        mAdapter = SongHomeAdapter(songHomeSongs, true, this)
        rc_favorite_home?.adapter = mAdapter
    }

    override fun favoriteSongsError(t: Throwable?) {
        Toast.makeText(context, t.toString(), Toast.LENGTH_SHORT).show()
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.fragment_favorite_home, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        rc_favorite_home?.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        presenter = FavoriteHomePresenter(FavoriteRepository.getInstance(),
                SongParameterRepository.getInstance(),
                SongPlayingRepository.getInstance(context))
        presenter.setView(this)
        presenter.onStart()
        presenter.getFavoriteSongs()
        txt_title_favorite_home.setOnClickListener(this)
        btn_favorite_home_more.setOnClickListener(this)
    }

    override fun onClick(p0: View?) {
        when (p0?.id) {
            R.id.txt_title_favorite_home, R.id.btn_favorite_home_more ->
                startActivity(SongHomeDetailActivity.getInstance(context, SongHomeDetailType.SONG_HOME_FAVORITE))
        }
    }

    override fun onItemSongClick(song: SongHome) {
        listener.onUpdateSongPlaying(song.nameSong ?: "", song.nameSinger, song.image ?: "")
        presenter.updatePlaySong(song.idSong.toString())
        val linkSong = song.link ?: ""
        val songPlaying = SongPlaying(song.idSong.toString(), song.nameSong
                ?: "", song.nameSinger, song.image, linkSong)
        activity.startService(MediaService.getInstance(activity, songPlaying, 0))
    }

    override fun onMoreBtnClick(song: SongHome) {
        val dialog = BottomSheetBuilder(activity, R.style.AppTheme_BottomSheetDialog)
                .setMode(BottomSheetBuilder.MODE_LIST)
                .addItem(0, song.nameSong, null)
                .addItem(MenuBottomSheet.ADD_PLAYING.id, MenuBottomSheet.ADD_PLAYING.title, MenuBottomSheet.ADD_PLAYING.icon)
                .addItem(MenuBottomSheet.ADD_FAVORITE.id, MenuBottomSheet.ADD_FAVORITE.title, MenuBottomSheet.ADD_FAVORITE.icon)
                .addItem(MenuBottomSheet.ADD_PLAYLIST.id, MenuBottomSheet.ADD_PLAYLIST.title, MenuBottomSheet.ADD_PLAYLIST.icon)
                .setItemClickListener(BottomSheetItemClickListener { item ->
                    when (item.itemId) {
                        MenuBottomSheet.ADD_PLAYING.id -> {
                            val songPlaying = SongPlaying(
                                    song.idSong ?: "-1",
                                    song.nameSong ?: "",
                                    song.nameSinger,
                                    song.image,
                                    song.link ?: "-1"
                            )
                            presenter.insertToPlaying(songPlaying)
                        }
                        MenuBottomSheet.ADD_FAVORITE.id -> {
                            if (SharedPrefs.getInstance().get(KeysPref.USER_NAME.name, String::class.java).isEmpty()) {
                                DialogUtils.createDialogConfirm(
                                        activity,
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
                            presenter.updateLikeSong(song.idSong.toString())
                        }
                        MenuBottomSheet.ADD_PLAYLIST.id -> {
                            if (SharedPrefs.getInstance().get(KeysPref.USER_NAME.name, String::class.java).isEmpty()) {
                                DialogUtils.createDialogConfirm(
                                        activity,
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
                            listenerInsertSongPlaylist.onDataIdSong(song.idSong ?: "-1")
                        }
                    }
                })
                .createDialog()

        dialog.show()
    }
}
