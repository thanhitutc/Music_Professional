package com.example.framgianguyenvanthanhd.music_professional.screens.home.Category_home

import com.example.framgianguyenvanthanhd.music_professional.data.datasource.CategoryDataSource
import com.example.framgianguyenvanthanhd.music_professional.data.model.Category
import com.example.framgianguyenvanthanhd.music_professional.data.repository.CategoryRepository

/**
 * Created by admin on 10/14/2018.
 */
class CategoryPresenter : CategoryContract.Presenter {

    private lateinit var view: CategoryContract.View
    private lateinit var repository: CategoryRepository

    init {
        repository = CategoryRepository.getInstance()
    }

    override fun setView(view: CategoryContract.View) {
        this.view = view
    }

    override fun onStart() {
        view.setPresenter(this)
    }

    override fun onStop() {

    }

    override fun getCategorysHome() {
        repository.getCategory(object : CategoryDataSource.OnResponseCategory {
            override fun onSuccess(Categorys: List<Category>?) {
                Categorys?.let {
                    view.categorySuccessfully(Categorys)
                }
            }

            override fun onError(t: Throwable?) {
                view.categoryError(t)
            }
        })
    }
}