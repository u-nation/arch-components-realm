package com.example.u_nation.arch_components_realm.ui

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.example.u_nation.arch_components_realm.model.Article
import com.example.u_nation.arch_components_realm.realm.ArticleDao
import io.realm.Realm
import android.arch.lifecycle.ViewModelProvider


class ArticleDetailViewModel(private val article: Article) : ViewModel() {

    private val realm: Realm = Realm.getDefaultInstance()
    private val articleDao: ArticleDao = ArticleDao(realm)
    val bookMarkLiveData: MutableLiveData<Boolean> = MutableLiveData()

    init {
        bookMarkLiveData.value = article.is_bookmarked == 1
    }

    fun bookMarkToggle(isLike: Boolean) {
        bookMarkLiveData.value = isLike

        if (articleDao.isExist(article.article_id)) {
            if (isLike) articleDao.bookMark(article.article_id)
            else articleDao.unBookMark(article.article_id)
        } else {
            articleDao.insert(article)
        }
    }

    override fun onCleared() {
        super.onCleared()
    }

    class Factory(private val article: Article) : ViewModelProvider.NewInstanceFactory() {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return ArticleDetailViewModel(article) as T
        }
    }

    inline fun <reified T : ViewModel> ViewModelProvider.NewInstanceFactory.create(viewModel: ViewModel): T = viewModel as T
}

