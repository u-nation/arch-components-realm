package com.example.u_nation.arch_components_realm.model

import io.realm.ArticleRealmProxy
import io.realm.RealmObject
import io.realm.annotations.Index
import io.realm.annotations.PrimaryKey
import org.parceler.Parcel

@Parcel(implementations = arrayOf(ArticleRealmProxy::class),
        value = Parcel.Serialization.BEAN,
        analyze = arrayOf(Article::class))
open class Article(
        @PrimaryKey
        var article_id: Int = 0,
        @Index
        var is_bookmarked: Int = 0,
        @Index
        var title: String = "",
        @Index
        var content: String = "",
        @Index
        var updated_at: Long = 0L,
        @Index
        var bookmark_at: Long = 0L) : RealmObject()