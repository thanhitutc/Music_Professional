package com.example.framgianguyenvanthanhd.music_professional.screens.home.Category_home

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.framgianguyenvanthanhd.music_professional.R
import com.example.framgianguyenvanthanhd.music_professional.data.model.Category
import com.example.framgianguyenvanthanhd.music_professional.helper.GridSpacingItemDecoration
import com.example.framgianguyenvanthanhd.music_professional.screens.home.category.OnItemCategoryClick
import com.example.framgianguyenvanthanhd.music_professional.screens.home.category.detail_songs.DetailSongsCategoryActivity
import com.example.framgianguyenvanthanhd.music_professional.screens.home.category.more.CategoryMoreActivity
import kotlinx.android.synthetic.main.fragment_category_home.*


/**
 * Created by admin on 10/14/2018.
 */
class CategoryHomeFragment : Fragment(), CategoryContract.View, OnItemCategoryClick, View.OnClickListener {

    private lateinit var presenter: CategoryContract.Presenter
    private lateinit var CategoryHomeAdapter: CategoryHomeAdapter

    override fun setPresenter(presenter: CategoryContract.Presenter) {
        this.presenter = presenter
    }

    override fun categorySuccessfully(Categorys: List<Category>) {
        CategoryHomeAdapter = CategoryHomeAdapter(Categorys, this)
        rc_category_home?.adapter = CategoryHomeAdapter
    }

    override fun categoryError(t: Throwable?) {

    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.fragment_category_home, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter = CategoryPresenter()
        presenter.setView(this)
        presenter.onStart()
        presenter.getCategorysHome()
        rc_category_home.layoutManager = GridLayoutManager(activity, 2)
        rc_category_home.addItemDecoration(GridSpacingItemDecoration(2, GridSpacingItemDecoration.dpToPx(context,10), true))

        txt_title_category_home.setOnClickListener(this)
        btn_category_home_more.setOnClickListener(this)
    }

    override fun onItemClick(category: Category) {
        startActivity(DetailSongsCategoryActivity.getInstance(context, category))
    }

    override fun onClick(p0: View?) {
        when(p0?.id) {
            R.id.txt_title_category_home, R.id.btn_category_home_more -> startActivity(CategoryMoreActivity.getInstance(context))
        }
    }
}