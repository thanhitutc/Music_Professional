package com.example.framgianguyenvanthanhd.music_professional.screens.home.playmost

import android.app.Activity
import android.os.Build
import android.os.Bundle
import android.support.annotation.RequiresApi
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.framgianguyenvanthanhd.music_professional.R
import com.example.framgianguyenvanthanhd.music_professional.Utils.*
import com.example.framgianguyenvanthanhd.music_professional.data.model.SongHome
import com.example.framgianguyenvanthanhd.music_professional.data.model.SongPlaying
import com.example.framgianguyenvanthanhd.music_professional.data.repository.PlayMostRepository
import com.example.framgianguyenvanthanhd.music_professional.data.repository.SongParameterRepository
import com.example.framgianguyenvanthanhd.music_professional.data.repository.SongPlayingRepository
import com.example.framgianguyenvanthanhd.music_professional.screens.BaseFragment
import com.example.framgianguyenvanthanhd.music_professional.screens.OnFragmentManager
import com.example.framgianguyenvanthanhd.music_professional.screens.home.common.SongHomeAdapter
import com.example.framgianguyenvanthanhd.music_professional.screens.home.common.song_home_detail.SongHomeDetailActivity
import com.example.framgianguyenvanthanhd.music_professional.screens.personal.playlist.PlaylistPersonalFragment
import com.example.framgianguyenvanthanhd.music_professional.screens.personal.playlist.add_song.PlaylistsForAddFragment
import com.github.rubensousa.bottomsheetbuilder.BottomSheetBuilder
import com.github.rubensousa.bottomsheetbuilder.adapter.BottomSheetItemClickListener
import kotlinx.android.synthetic.main.fragment_playmost.*

/**
 * Created by admin on 10/27/2018.
 */
class PlayMostFragment : BaseFragment(), PlaymostContract.View, View.OnClickListener, SongHomeAdapter.OnItemSongHomeClickListener {
    private lateinit var presenter: PlaymostContract.Presenter
    private lateinit var adapter: SongHomeAdapter
    private lateinit var listener: OnFragmentManager

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.fragment_playmost, container, false)
    }

    override fun initiateView() {

    }

    override fun onAttach(activity: Activity?) {
        super.onAttach(activity)
        if (context is OnFragmentManager){
            listener=  context as OnFragmentManager
        } else {
            throw RuntimeException()
        }
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        rc_playmost_home.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        presenter = PlayMostPresenter(PlayMostRepository.getInstance(),
                SongParameterRepository.getInstance()
        , SongPlayingRepository.getInstance(context))
        presenter.setView(this)
        presenter.onStart()
        presenter.getPlayMostSongs()
        txt_title_playmost_home.setOnClickListener(this)
        btn_playmost_home_more.setOnClickListener(this)
    }

    override fun playMostSongsSuccessfully(songHomeSongs: List<SongHome>) {
        adapter = SongHomeAdapter(songHomeSongs, true, this)
        rc_playmost_home?.adapter = adapter
    }

    override fun playMostSongsError(t: Throwable?) {
    }

    override fun setPresenter(presenter: PlaymostContract.Presenter) {
        this.presenter = presenter
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onClick(p0: View?) {
        when(p0?.id) {
            R.id.txt_title_playmost_home , R.id.btn_playmost_home_more ->
                context.startActivity(SongHomeDetailActivity.getInstance(context ,SongHomeDetailType.SONG_HOME_PLAY_MOST))
        }
    }

    override fun onItemSongClick(song: SongHome) {
        presenter.updatePlaySong(song.idSong.toString())
    }

    override fun onMoreBtnClick(song: SongHome) {
        val dialog = BottomSheetBuilder(activity, R.style.AppTheme_BottomSheetDialog)
                .setMode(BottomSheetBuilder.MODE_LIST)
                .addItem(0,song.nameSong, null)
                .addItem(MenuBottomSheet.ADD_PLAYING.id, MenuBottomSheet.ADD_PLAYING.title, MenuBottomSheet.ADD_PLAYING.icon)
                .addItem(MenuBottomSheet.ADD_FAVORITE.id, MenuBottomSheet.ADD_FAVORITE.title, MenuBottomSheet.ADD_FAVORITE.icon)
                .addItem(MenuBottomSheet.ADD_PLAYLIST.id, MenuBottomSheet.ADD_PLAYLIST.title, MenuBottomSheet.ADD_PLAYLIST.icon)
                .setItemClickListener(BottomSheetItemClickListener { item->
                    when(item.itemId) {
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
                            listener.onDataIdSong(song.idSong ?: "-1")
                        }
                    }
                })
                .createDialog()

        dialog.show()
    }
}