package com.example.framgianguyenvanthanhd.music_professional.screens.personal.favorite_user

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.*
import android.widget.Toast
import com.example.framgianguyenvanthanhd.music_professional.MainActivity
import com.example.framgianguyenvanthanhd.music_professional.R
import com.example.framgianguyenvanthanhd.music_professional.R.id.isloading_fav_personal
import com.example.framgianguyenvanthanhd.music_professional.Utils.DialogUtils
import com.example.framgianguyenvanthanhd.music_professional.Utils.KeysPref
import com.example.framgianguyenvanthanhd.music_professional.Utils.MenuBottomSheet
import com.example.framgianguyenvanthanhd.music_professional.Utils.SharedPrefs
import com.example.framgianguyenvanthanhd.music_professional.data.model.Song
import com.example.framgianguyenvanthanhd.music_professional.data.repository.PersonalLikeRepository
import com.example.framgianguyenvanthanhd.music_professional.data.repository.SongParameterRepository
import com.example.framgianguyenvanthanhd.music_professional.screens.BaseFragment
import com.example.framgianguyenvanthanhd.music_professional.screens.home.common.DetailSongAdapter
import com.github.rubensousa.bottomsheetbuilder.BottomSheetBuilder
import com.github.rubensousa.bottomsheetbuilder.adapter.BottomSheetItemClickListener
import es.dmoral.toasty.Toasty
import kotlinx.android.synthetic.main.fragment_favorite_personal.*
import kotlinx.android.synthetic.main.fragment_search.*

/**
 * Created by admin on 12/19/2018.
 */
class FavoritePersonalFragment : BaseFragment(), FavoritePersonalContract.FavPersonalView, DetailSongAdapter.OnItemSongClickListener{
    private lateinit var presenter: FavoritePersonalContract.FavPersonalPresenter
    private lateinit var adapter: DetailSongAdapter

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.fragment_favorite_personal, container, false)
    }

    override fun initiateView() {
        val mainActivity = activity as MainActivity
        mainActivity.updateToolbar(false, getString(R.string.favorite))

        presenter = FavoritePersonalPresenter(
                PersonalLikeRepository.getInstance(),
                SongParameterRepository.getInstance(),
                this
        )
        presenter.onStart()
        presenter.getSongsFavorite()
        rv_favorite_personal.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        isloading_fav_personal.visibility = View.VISIBLE

        swipe_fav_personal.setOnRefreshListener {
            presenter.getSongsFavorite()
        }
    }

    override fun setPresenter(presenter: FavoritePersonalContract.FavPersonalPresenter) {
        this.presenter = presenter
    }

    override fun songsFavoriteSuccess(songs: List<Song>) {
        swipe_fav_personal.isRefreshing = false
        isloading_fav_personal.visibility = View.INVISIBLE
        adapter = DetailSongAdapter(songs.toMutableList(), this)
        rv_favorite_personal.adapter = adapter
    }

    override fun songsFavoriteFail() {
        swipe_fav_personal.isRefreshing = false
        isloading_fav_personal.visibility = View.INVISIBLE
        Toasty.error(context, getString(R.string.txt_error), Toast.LENGTH_SHORT, true).show()
    }

    override fun removeFavoriteSuccess(idSong: String) {
        adapter.removeSong(idSong)
        isloading_fav_personal.visibility = View.INVISIBLE
    }

    override fun removeFavoriteFail() {
        isloading_fav_personal.visibility = View.INVISIBLE
        Toasty.error(context, getString(R.string.txt_error),Toast.LENGTH_SHORT,true).show()
    }

    override fun onItemSongClick(song: Song) {

    }

    override fun onMoreBtnClick(song: Song) {
        val dialog = BottomSheetBuilder(activity, R.style.AppTheme_BottomSheetDialog)
                .setMode(BottomSheetBuilder.MODE_LIST)
                .addItem(0,song.name, null)
                .addItem(MenuBottomSheet.ADD_PLAYING.id, MenuBottomSheet.ADD_PLAYING.title, MenuBottomSheet.ADD_PLAYING.icon)
                .addItem(MenuBottomSheet.ADD_PLAYLIST.id, MenuBottomSheet.ADD_PLAYLIST.title, MenuBottomSheet.ADD_PLAYLIST.icon)
                .addItem(MenuBottomSheet.DELETE.id, MenuBottomSheet.DELETE.title, MenuBottomSheet.DELETE.icon)
                .setItemClickListener(BottomSheetItemClickListener { item->
                    when(item.itemId) {
                        MenuBottomSheet.ADD_PLAYING.id -> Log.e("thanhd", "Playing")
                        MenuBottomSheet.ADD_PLAYLIST.id -> Log.e("thanhd", "Play list")
                        MenuBottomSheet.DELETE.id -> {
                            presenter.removeSongFavorite(song.idSong.toString())
                            isloading_fav_personal.visibility = View.VISIBLE
                        }
                    }
                })
                .createDialog()

        dialog.show()
    }

}