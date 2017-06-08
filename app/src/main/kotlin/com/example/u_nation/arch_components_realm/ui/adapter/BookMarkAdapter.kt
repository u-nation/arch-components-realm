package jp.co.mstyle.loverecipe.presentation.adapter

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.example.u_nation.arch_components_realm.databinding.ArticleRowBinding
import com.example.u_nation.arch_components_realm.model.Article
import com.example.u_nation.arch_components_realm.ui.binding.ArticleBinding
import com.example.u_nation.arch_components_realm.util.inflateBinding
import com.like.LikeButton
import com.like.OnLikeListener
import io.realm.RealmRecyclerViewAdapter
import io.realm.RealmResults
import timber.log.Timber


class BookMarkAdapter(val items: RealmResults<Article>,
                      val onClickArticle: (article: Article) -> Unit,
                      val onLikeButton: (isLike: Boolean, article: Article) -> Unit) : RealmRecyclerViewAdapter<Article, BookMarkAdapter.ViewHolder>(items, true) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder = ViewHolder(parent.inflateBinding<ArticleRowBinding>())

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        item ?: return
        holder.binding.articleBinding = ArticleBinding(item)
        holder.binding.executePendingBindings()
        holder.binding.rootRow.setOnClickListener(holder)
        holder.binding.likeBtn.setOnLikeListener(object : OnLikeListener {
            override fun liked(like: LikeButton) {
                onLikeButton(true, item)
            }

            override fun unLiked(unLike: LikeButton) {
                onLikeButton(false, item)
            }
        })
    }

    inner class ViewHolder(val binding: ArticleRowBinding) : RecyclerView.ViewHolder(binding.root), View.OnClickListener {

        fun getArticle(): Article? = getItem(layoutPosition)

        override fun onClick(view: View) {
            val item = getItem(layoutPosition)
            item ?: return
            Timber.i("position = $layoutPosition title = ${item.title}")
            onClickArticle(item)
        }
    }
}
