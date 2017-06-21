package com.example.u_nation.arch_components_realm.ui.binding

import android.databinding.ObservableBoolean
import android.databinding.ObservableField
import com.example.u_nation.arch_components_realm.model.Article
import com.example.u_nation.arch_components_realm.util.DateTimeUtil

data class ArticleBinding(var article: Article) {

    val is_bookmarked: ObservableBoolean = ObservableBoolean()
    val title: ObservableField<String> = ObservableField()
    val updated_at: ObservableField<String> = ObservableField()

    init {
        this.title.set(article.title)
        this.updated_at.set(DateTimeUtil.unixTimeToString(article.updated_at, DateTimeUtil.TimeFormat.YYYYMMDDHHMM_SLASH))
        this.is_bookmarked.set(article.is_bookmarked == 1)
    }

    override fun equals(other: Any?): Boolean {
        val articleBinding = other as ArticleBinding
        return this.article.article_id == articleBinding.article.article_id
    }

    override fun hashCode(): Int = article.article_id
}