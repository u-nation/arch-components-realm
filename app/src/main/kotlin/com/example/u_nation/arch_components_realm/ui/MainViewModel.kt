package com.example.u_nation.arch_components_realm.ui

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.example.u_nation.arch_components_realm.model.Article
import com.example.u_nation.arch_components_realm.realm.ArticleDao
import com.example.u_nation.arch_components_realm.ui.binding.ArticleBinding
import com.example.u_nation.arch_components_realm.util.DateTimeUtil
import io.realm.OrderedCollectionChangeSet
import io.realm.OrderedRealmCollectionChangeListener
import io.realm.Realm
import io.realm.RealmResults
import timber.log.Timber
import kotlin.properties.Delegates

class MainViewModel : ViewModel() {

    private val realm: Realm = Realm.getDefaultInstance()
    private val articleDao: ArticleDao = ArticleDao(realm)
    val articleList: MutableList<ArticleBinding> = ArrayList()
    var managedArticleList: RealmResults<Article> by Delegates.notNull()
    var managedBookMarkList: RealmResults<Article> by Delegates.notNull()
    val articleLiveData: MutableLiveData<ArticleBinding> = MutableLiveData()

    /** Realm Fine-Grained Notification */
    private val articleListener: OrderedRealmCollectionChangeListener<RealmResults<Article>> = OrderedRealmCollectionChangeListener { collection, changeSet ->
        changeSet.insertionRanges.forEach { range -> notify(collection, range) }
        changeSet.changeRanges.forEach { range -> notify(collection, range) }
    }

    private fun notify(collection: RealmResults<Article>, range: OrderedCollectionChangeSet.Range) {
        (range.startIndex..range.startIndex + range.length - 1).forEach { i ->
            val articleBinding: ArticleBinding = ArticleBinding(article = collection[i])
            val position = articleList.indexOf(articleBinding)
            if (position != -1) {
                articleList[position] = articleBinding
                articleLiveData.value = articleBinding
            }
        }
    }

    init {
        managedArticleList = articleDao.selectAll()
        managedBookMarkList = articleDao.selectBookmarks()
        managedArticleList.addChangeListener(articleListener)

        /*Articleオブジェクト初期生成*/
        articleList.addAll((1..20)
                .map {
                    val updated_at = DateTimeUtil.plusDays(it)
                    val content = "${DateTimeUtil.unixTimeToString(updated_at, DateTimeUtil.TimeFormat.YYYYMMDDHHMM_SLASH)}に執筆された記事"
                    ArticleBinding(article = Article(article_id = it, is_bookmarked = 0, title = "$it の記事", content = content, updated_at = updated_at, bookmark_at = 0))
                }
                .toMutableList())
    }

    fun bookMarkToggle(isLike: Boolean, article: Article?) {
        article ?: return
        if (articleDao.isExist(article.article_id)) {
            if (isLike) articleDao.bookMark(article.article_id)
            else articleDao.unBookMark(article.article_id)
        } else {
            articleDao.insert(article)
        }
    }

    override fun onCleared() {
        managedArticleList.removeChangeListener(articleListener)
        managedBookMarkList.removeAllChangeListeners()
        realm.close()
        super.onCleared()
        Timber.i("onCleared")
    }
}