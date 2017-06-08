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


class ArticleAdapter(val onClickArticle: (article: Article) -> Unit,
                     val onLikeButton: (isLike: Boolean, article: Article?) -> Unit) : RecyclerArrayAdapter<ArticleBinding, ArticleAdapter.ViewHolder>() {

    override fun getItemViewType(position: Int): Int = 1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder = ViewHolder(parent.inflateBinding<ArticleRowBinding>())

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.binding.articleBinding = item
        holder.binding.executePendingBindings()

        holder.binding.rootRow.setOnClickListener(holder)
        holder.binding.likeBtn.setOnLikeListener(object : OnLikeListener {
            override fun liked(like: LikeButton) {
                onLikeButton(true, item.article)
            }

            override fun unLiked(unLike: LikeButton) {
                onLikeButton(false, item.article)
            }
        })
    }

    inner class ViewHolder(val binding: ArticleRowBinding) : RecyclerView.ViewHolder(binding.root), View.OnClickListener {

        override fun onClick(view: View) {
            val item = getItem(layoutPosition)
            onClickArticle(item.article)
        }
    }
}
