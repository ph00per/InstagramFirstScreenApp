package com.ph00.instagramfirstpageapp.ui.adapters.post

import android.view.View
import com.example.delegateadapter.delegate.KDelegateAdapter
import com.ph00.instagramfirstpageapp.R
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_post.*
import org.koin.core.KoinComponent
import org.koin.core.inject


open class PostItemDelegateAdapter :
    KDelegateAdapter<PostItemViewModel>(), KoinComponent {
    private val picasso by inject<Picasso>()

    override fun getLayoutId() = R.layout.item_post

    override fun isForViewType(items: MutableList<*>, position: Int) =
        items[position] is PostItemViewModel

    override fun onBind(item: PostItemViewModel, viewHolder: KViewHolder) {
        with(viewHolder) {
            user_name.text = item.post.userName
            user_location.text = item.post.userLocation
            picasso.load(item.post.imageUrl).into(post_image)
            picasso.load(item.post.userProfileImageUrl).into(user_profile_image)
            likes_count.text = item.post.likes.toString()

            item.post.comments?.run {
                getOrNull(0)?.let { comment ->
                    first_comment_username.apply {
                        text = comment.userName
                        visibility = View.VISIBLE
                    }
                    first_comment.apply {
                        text = comment.comment
                        visibility = View.VISIBLE
                    }
                }
                getOrNull(1)?.let { comment ->
                    second_comment_username.apply {
                        text = comment.userName
                        visibility = View.VISIBLE
                    }
                    second_comment.apply {
                        text = comment.comment
                        visibility = View.VISIBLE
                    }
                }
            }
        }
    }

    override fun onRecycled(holder: KViewHolder) {
        super.onRecycled(holder)
        with(holder) {
            first_comment_username.visibility = View.GONE
            first_comment.visibility = View.GONE
            second_comment_username.visibility = View.GONE
            second_comment.visibility = View.GONE
        }
    }
}

