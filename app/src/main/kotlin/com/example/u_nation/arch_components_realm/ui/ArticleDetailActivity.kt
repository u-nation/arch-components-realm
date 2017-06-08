package com.example.u_nation.arch_components_realm.ui

import android.arch.lifecycle.LifecycleActivity
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.example.u_nation.arch_components_realm.R
import com.example.u_nation.arch_components_realm.databinding.ActivityArticleDetailBinding
import com.example.u_nation.arch_components_realm.model.Article
import com.example.u_nation.arch_components_realm.model.`Article$$Parcelable`
import com.example.u_nation.arch_components_realm.util.dataBinding
import com.example.u_nation.arch_components_realm.util.get
import com.example.u_nation.arch_components_realm.util.intent
import com.like.LikeButton
import com.like.OnLikeListener
import org.parceler.Parcels
import kotlin.properties.Delegates

class ArticleDetailActivity : LifecycleActivity() {

    private var viewModel: ArticleDetailViewModel by Delegates.notNull()
    private val binding by lazy { dataBinding<ActivityArticleDetailBinding>(R.layout.activity_article_detail) }

    companion object {
        val KEY_ARTICLE = "article"
        fun createIntent(article: Article, context: Context): Intent = context.intent<ArticleDetailActivity>().putExtra(KEY_ARTICLE, `Article$$Parcelable`(article))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_article_detail)
        val article = Parcels.unwrap<Article>(intent.getParcelableExtra(KEY_ARTICLE))
        binding.title.text = article.title
        binding.content.text = article.content
        viewModel = ViewModelProviders.of(this, ArticleDetailViewModel.Factory(article)).get<ArticleDetailViewModel>()
        viewModel.bookMarkLiveData.observe(this, Observer { isBookMark -> isBookMark?.let { binding.likeBtn.isLiked = it } })
        binding.likeBtn.setOnLikeListener(object : OnLikeListener {
            override fun liked(like: LikeButton) {
                viewModel.bookMarkToggle(true)
            }

            override fun unLiked(unLike: LikeButton) {
                viewModel.bookMarkToggle(false)
            }
        })
    }
}
