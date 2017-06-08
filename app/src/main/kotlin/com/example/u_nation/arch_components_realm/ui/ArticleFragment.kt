package com.example.u_nation.arch_components_realm.ui

import android.arch.lifecycle.LifecycleFragment
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.u_nation.arch_components_realm.R
import com.example.u_nation.arch_components_realm.util.findById
import com.example.u_nation.arch_components_realm.util.get
import jp.co.mstyle.loverecipe.presentation.adapter.ArticleAdapter
import kotlin.properties.Delegates

class ArticleFragment : LifecycleFragment() {
    private var adapter: ArticleAdapter by Delegates.notNull<ArticleAdapter>()
    private var mainViewModel: MainViewModel by Delegates.notNull()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.article_recycler_view, container, false)
        mainViewModel = ViewModelProviders.of(activity).get<MainViewModel>()
        adapter = ArticleAdapter(
                onClickArticle = { article -> startActivity(ArticleDetailActivity.createIntent(article, context)) },
                onLikeButton = { isLike, article -> mainViewModel.bookMarkToggle(isLike, article) }
        )
        val recyclerView = view.findById<RecyclerView>(R.id.article_list)
        (recyclerView.itemAnimator as DefaultItemAnimator).supportsChangeAnimations = false
        recyclerView.addItemDecoration(DividerItemDecoration(activity, DividerItemDecoration.VERTICAL))
        recyclerView.adapter = adapter
        recyclerView.setHasFixedSize(true)
        adapter.addAll(mainViewModel.articleList)
        mainViewModel.articleLiveData.observe(this, Observer { articleBinding ->
            articleBinding?.let { adapter.change(it) }
        })
        return view
    }
}