package com.example.u_nation.arch_components_realm.realm

import com.example.u_nation.arch_components_realm.model.Article
import com.example.u_nation.arch_components_realm.model.ArticleFields
import com.example.u_nation.arch_components_realm.util.DateTimeUtil
import com.example.u_nation.arch_components_realm.util.typedWhere
import io.realm.Realm
import io.realm.RealmQuery
import io.realm.RealmResults
import io.realm.Sort

class ArticleDao(val realm: Realm) {
    private fun query(): RealmQuery<Article> = realm.typedWhere<Article>()
    private fun queryAsync(realmAsync: Realm): RealmQuery<Article> = realmAsync.typedWhere<Article>()

    fun isExist(article_id: Int): Boolean =query().equalTo(ArticleFields.ARTICLE_ID, article_id).count().toInt() == 1

    fun selectAll(): RealmResults<Article> = query().findAll()
    fun selectBookmarks(): RealmResults<Article> = query().equalTo(ArticleFields.IS_BOOKMARKED, 1).findAllSorted(ArticleFields.BOOKMARK_AT, Sort.DESCENDING)

    fun bookMark(article_id: Int) {
        upsertBookMark(article_id, is_bookmark = 1, bookmark_at = DateTimeUtil.nowUnixTime())
    }

    fun unBookMark(article_id: Int) {
        upsertBookMark(article_id, is_bookmark = 0, bookmark_at = 0)
    }

    private fun upsertBookMark(article_id: Int, is_bookmark: Int, bookmark_at: Long) {
        realm.executeTransactionAsync { realmAsync ->
            val article = queryAsync(realmAsync).equalTo(ArticleFields.ARTICLE_ID, article_id).findFirst()
            article.is_bookmarked = is_bookmark
            article.bookmark_at = bookmark_at
        }
    }

    fun insert(article: Article) {
        realm.executeTransaction { realm ->
            article.is_bookmarked = 1
            article.bookmark_at = DateTimeUtil.nowUnixTime()
            realm.insert(article)
        }
    }
}