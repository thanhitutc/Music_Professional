package com.example.framgianguyenvanthanhd.music_professional.screens.personal.playlist

import android.content.DialogInterface
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import com.example.framgianguyenvanthanhd.music_professional.MainActivity
import com.example.framgianguyenvanthanhd.music_professional.R
import com.example.framgianguyenvanthanhd.music_professional.Utils.MenuBottomSheet
import com.example.framgianguyenvanthanhd.music_professional.data.model.Playlist
import com.example.framgianguyenvanthanhd.music_professional.data.repository.PlaylistPersonalRepository
import com.example.framgianguyenvanthanhd.music_professional.helper.GridSpacingItemDecoration
import com.example.framgianguyenvanthanhd.music_professional.screens.BaseFragment
import com.example.framgianguyenvanthanhd.music_professional.screens.home.playlist_top.OnItemPlaylistClick
import com.example.framgianguyenvanthanhd.music_professional.screens.home.playlist_top.detail_song.DetailPlaylistActivity
import com.github.rubensousa.bottomsheetbuilder.BottomSheetBuilder
import com.github.rubensousa.bottomsheetbuilder.adapter.BottomSheetItemClickListener
import es.dmoral.toasty.Toasty
import kotlinx.android.synthetic.main.fragment_favorite_personal.*
import kotlinx.android.synthetic.main.fragment_playlist_personal.*
import kotlinx.android.synthetic.main.item_playlist_personal.*

/**
 * Created by admin on 12/22/2018.
 */
class PlaylistPersonalFragment: BaseFragment(),PlaylistPersonalContract.PlaylistPersonalView,
        OnItemPlaylistClick, PlaylistPersonalAdapter.OnClickPlaylistMoreButton, View.OnClickListener
{

    private lateinit var presenter: PlaylistPersonalContract.PlaylistPersonalPresenter
    private lateinit var adapter: PlaylistPersonalAdapter

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.fragment_playlist_personal, container, false)
    }

    override fun initiateView() {
        val mainActivity = activity as MainActivity
        mainActivity.updateToolbar(false, getString(R.string.playlist))

        presenter = PlaylistPersonalPresenter(
                PlaylistPersonalRepository.getInstance(),
                this
        )
        presenter.setView(this)
        presenter.onStart()
        presenter.fetchPlaylists()
        rv_playlist_personal.layoutManager = GridLayoutManager(context, 2)
        rv_playlist_personal.addItemDecoration(GridSpacingItemDecoration(2, GridSpacingItemDecoration.dpToPx(context, 10), true))
        isloading_playlist_personal.visibility = View.VISIBLE

        btn_add_playlist.setOnClickListener (this)

        playlist_personal_swipe.setOnRefreshListener {
            presenter.fetchPlaylists()
        }
    }

    override fun setPresenter(presenter: PlaylistPersonalContract.PlaylistPersonalPresenter) {
        this.presenter = presenter
    }

    override fun onClick(p0: View?) {
        when (p0?.id) {
            R.id.btn_add_playlist -> {
                createDialogCreatPlaylist()
            }
        }
    }

    override fun onItemClick(playlist: Playlist) {
        context.startActivity(DetailPlaylistActivity.getInstance(context, playlist))
    }

    override fun onClickMoreBtn(playlist: Playlist) {
        val dialog = BottomSheetBuilder(activity, R.style.AppTheme_BottomSheetDialog)
                .setMode(BottomSheetBuilder.MODE_LIST)
                .addItem(0,playlist.name, null)
                .addItem(MenuBottomSheet.DELETE.id, MenuBottomSheet.DELETE.title, MenuBottomSheet.DELETE.icon)
                .setItemClickListener(BottomSheetItemClickListener { item->
                    when(item.itemId) {
                        MenuBottomSheet.DELETE.id -> {
                            presenter.deletePlaylist(playlist.idPlaylist ?: "-1")
                            isloading_playlist_personal.visibility = View.VISIBLE
                        }
                    }
                })
                .createDialog()

        dialog.show()
    }

    override fun fetchPlaylistSuccess(playlist: List<Playlist>) {
        isloading_playlist_personal.visibility = View.INVISIBLE
        playlist_personal_swipe.isRefreshing = false
        adapter = PlaylistPersonalAdapter(
                playlist.toMutableList(),
                this,
                this
        )
        rv_playlist_personal.adapter = adapter
    }

    override fun fetchPlaylistsFail() {
        playlist_personal_swipe.isRefreshing = false
        isloading_playlist_personal.visibility = View.INVISIBLE
        Toasty.error(context, getString(R.string.txt_error), Toast.LENGTH_SHORT, true).show()
    }

    override fun createPlaylistSuccess(playlist: Playlist) {
        isloading_playlist_personal.visibility = View.INVISIBLE
        adapter.addPlaylist(playlist)
    }

    override fun createPlaylistFail() {
        isloading_playlist_personal.visibility = View.INVISIBLE
        Toasty.error(context, getString(R.string.txt_error), Toast.LENGTH_SHORT, true).show()
    }

    override fun deletePlaylistSuccess(idPlaylist: String) {
        isloading_playlist_personal.visibility = View.INVISIBLE
        adapter.deletePlaylist(idPlaylist)
    }

    override fun deletePlaylistFail() {
        isloading_playlist_personal.visibility = View.INVISIBLE
        Toasty.error(context, getString(R.string.txt_error), Toast.LENGTH_SHORT, true).show()
    }

    override fun deleteSongPlaylistSuccess() {
        isloading_playlist_personal.visibility = View.INVISIBLE

    }

    override fun deleteSongPlaylistFail() {
        isloading_playlist_personal.visibility = View.INVISIBLE

    }

    private fun createDialogCreatPlaylist() {
        val inflater = this.layoutInflater
        val al = inflater.inflate(R.layout.layout_dialog_add_playlist, null)
        val editTextNewAlbum = al.findViewById<EditText>(R.id.edit_text_new_album)
        val dialog = AlertDialog.Builder(activity)
        dialog.setTitle(activity.resources.getString(R.string.txt_add_playlist))
        dialog.setView(al)
        dialog.setNegativeButton(activity.resources.getString(android.R.string.ok)
        ) { dialog, which ->
            presenter.createPlaylist(editTextNewAlbum.text.toString())
            isloading_playlist_personal.visibility = View.VISIBLE
        }

        dialog.setPositiveButton(activity.resources.getString(android.R.string.cancel)
        ) { dialog, which -> dialog.dismiss() }
        dialog.create()
        dialog.show()
    }
}