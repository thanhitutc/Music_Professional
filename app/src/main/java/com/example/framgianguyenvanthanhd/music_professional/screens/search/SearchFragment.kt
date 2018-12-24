package com.example.framgianguyenvanthanhd.music_professional.screens.search

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.ActionBar
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.SearchView
import android.util.Log
import android.view.*
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import com.example.framgianguyenvanthanhd.music_professional.MainActivity
import com.example.framgianguyenvanthanhd.music_professional.R
import com.example.framgianguyenvanthanhd.music_professional.data.model.Song
import com.example.framgianguyenvanthanhd.music_professional.data.model.SongPlaying
import com.example.framgianguyenvanthanhd.music_professional.data.repository.SearchRepository
import com.example.framgianguyenvanthanhd.music_professional.data.repository.SongParameterRepository
import com.example.framgianguyenvanthanhd.music_professional.screens.OnUpdateDataPlayingListener
import com.example.framgianguyenvanthanhd.music_professional.screens.home.common.DetailSongAdapter
import com.example.framgianguyenvanthanhd.music_professional.service.MediaService
import es.dmoral.toasty.Toasty
import kotlinx.android.synthetic.main.fragment_search.*


/**
 * Created by admin on 12/16/2018.
 */
class SearchFragment : Fragment(), SearchContract.SearchView, DetailSongAdapter.OnItemSongClickListener {
    private lateinit var presenter: SearchContract.SearchPresenter
    private lateinit var adapter: DetailSongAdapter
    private lateinit var listenerUpdatePlaying: OnUpdateDataPlayingListener

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.fragment_search, container, false)
    }

    override fun onAttach(activity: Activity?) {
        super.onAttach(activity)

        if (context is OnUpdateDataPlayingListener) {
            listenerUpdatePlaying = context as OnUpdateDataPlayingListener
        } else {
            throw Throwable("do not attach")
        }
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initToolbar()
        val mainActivity = activity as MainActivity
        mainActivity.isDisplayToolbar(false)
        mainActivity.isDisplayBottomNavigation(false)
        presenter = SearchPresenter(SearchRepository.getInstance(), SongParameterRepository.getInstance(),this)
        presenter.onStart()
        rv_search.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
    }

    override fun setPresenter(presenter: SearchContract.SearchPresenter) {
        this.presenter = presenter
    }

    override fun searchSuccess(songs: List<Song>) {
        if (songs.isEmpty()) {
            Toasty.warning(context, getString(R.string.txt_search_no_result), Toast.LENGTH_SHORT, true).show()
        }
        isloading_search.visibility = View.INVISIBLE
        adapter = DetailSongAdapter(songs.toMutableList(), this)
        rv_search.adapter = adapter
    }

    override fun searchFail() {
        isloading_search?.visibility = View.INVISIBLE
        Toasty.error(context, getString(R.string.txt_error), Toast.LENGTH_SHORT, true).show()
    }

    override fun onItemSongClick(song: Song) {
        presenter.updatePlaySong(song.idSong.toString())
        listenerUpdatePlaying.onUpdateSongPlaying(song.name ?: "", song.nameSinger ?: "", song.image
                ?: "")
        val linkSong = song.link ?: ""
        val songPlaying = SongPlaying(song.idSong.toString(), song.name
                ?: "", song.nameSinger ?: "", song.image, linkSong)
        activity.startService(MediaService.getInstance(activity, songPlaying, 0))
    }

    override fun onMoreBtnClick(song: Song) {

    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        inflater?.inflate(R.menu.menu_search_bar, menu)
        val itemSearch = menu?.findItem(R.id.menu_search_detail)
        val mSearchView = itemSearch?.actionView as SearchView
        mSearchView.isFocusable = true
        mSearchView.isIconified = false
        mSearchView.requestFocusFromTouch()
        mSearchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(text: String?): Boolean {
                presenter.search(text ?: "")
                isloading_search.visibility = View.VISIBLE
                return true
            }

            override fun onQueryTextChange(p0: String?): Boolean {
                Log.e("thanh", p0)
                return true
            }
        })
        super.onCreateOptionsMenu(menu, inflater)
    }

    private fun initToolbar() {
        (activity as AppCompatActivity).setSupportActionBar(toolbar_search)
        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
        val actionBar = (activity as AppCompatActivity).supportActionBar
        val searchView = SearchView(context)
        actionBar?.customView = searchView
        actionBar?.displayOptions = ActionBar.DISPLAY_SHOW_CUSTOM
        searchView.isFocusable = true
        searchView.isIconified = false
        searchView.requestFocusFromTouch()
        toolbar_search.title = context.getString(R.string.search_title)
        toolbar_search.setOnClickListener {
            actionBar?.displayOptions = ActionBar.DISPLAY_SHOW_CUSTOM
            searchView.isFocusable = true
            searchView.isIconified = false
            searchView.requestFocusFromTouch()
        }
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(text: String?): Boolean {
                presenter.search(text ?: "")
                isloading_search.visibility = View.VISIBLE
                val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?
                imm!!.hideSoftInputFromWindow(searchView.windowToken, 0)
                return true
            }

            override fun onQueryTextChange(p0: String?): Boolean {
                return true
            }
        })
    }


}