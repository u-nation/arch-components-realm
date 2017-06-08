package com.example.u_nation.arch_components_realm.ui

import android.arch.lifecycle.LifecycleFragment
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.helper.ItemTouchHelper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.u_nation.arch_components_realm.R
import com.example.u_nation.arch_components_realm.model.Article
import com.example.u_nation.arch_components_realm.util.findById
import com.example.u_nation.arch_components_realm.util.get
import jp.co.mstyle.loverecipe.presentation.adapter.BookMarkAdapter
import kotlin.properties.Delegates

class BookMarkFragment : LifecycleFragment() {

    private var adapter: BookMarkAdapter by Delegates.notNull()
    private val mainViewModel by lazy { ViewModelProviders.of(activity).get<MainViewModel>() }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.article_recycler_view, container, false)
        adapter = BookMarkAdapter(
                items = mainViewModel.managedBookMarkList,
                onClickArticle = { article -> startActivity(ArticleDetailActivity.createIntent(article, context)) },
                onLikeButton = { isLike, article -> mainViewModel.bookMarkToggle(isLike, article) }
        )
        val recyclerView = view.findById<RecyclerView>(R.id.article_list)
        (recyclerView.itemAnimator as DefaultItemAnimator).supportsChangeAnimations = false
        recyclerView.addItemDecoration(DividerItemDecoration(activity, DividerItemDecoration.VERTICAL))
        recyclerView.adapter = adapter
        recyclerView.setHasFixedSize(true)
        ItemTouchHelper(TouchHelperCallback()).attachToRecyclerView(recyclerView)
        return view
    }

    inner class TouchHelperCallback : ItemTouchHelper.SimpleCallback(ItemTouchHelper.DOWN, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {

        override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
            return false
        }

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            val article: Article? = (viewHolder as BookMarkAdapter.ViewHolder).getArticle()
            mainViewModel.bookMarkToggle(isLike = false, article = article)
        }

        override fun isLongPressDragEnabled(): Boolean {
            return false
        }
    }
}