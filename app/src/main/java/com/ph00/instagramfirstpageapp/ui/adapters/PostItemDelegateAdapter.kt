package com.ph00.instagramfirstpageapp.ui.adapters

import com.example.delegateadapter.delegate.KDelegateAdapter
import com.ph00.instagramfirstpageapp.R
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_post.*
import org.koin.core.KoinComponent
import org.koin.core.inject


class PostItemDelegateAdapter :
    KDelegateAdapter<PostItemViewModel>(), KoinComponent {
    private val picasso by inject<Picasso>()
    private val likedPosts = mutableListOf<Int>()

    override fun getLayoutId() = R.layout.item_post

    override fun isForViewType(items: MutableList<*>, position: Int) =
        items[position] is PostItemViewModel

    override fun onBind(item: PostItemViewModel, viewHolder: KViewHolder) {
        with(viewHolder) {
            user_name_text.text = item.post.userName
            user_location_text.text = item.post.userLocation
            picasso.load(item.post.imageUrl).into(post_image)
            picasso.load(item.post.userProfileImageUrl).into(user_profile_image)

            if (likedPosts.contains(item.post.id)) {
                like_button.setBackgroundResource(R.drawable.ic_favourite_filled)
                likes_count_text.text = (item.post.likes + 1).toString()
            } else {
                like_button.setBackgroundResource(R.drawable.ic_favourite_outlined)
                likes_count_text.text = (item.post.likes).toString()
            }

            like_button.setOnClickListener {
                if (likedPosts.contains(item.post.id)) {
                    like_button.setBackgroundResource(R.drawable.ic_favourite_outlined)
                    likes_count_text.text = (item.post.likes).toString()
                    likedPosts.remove(item.post.id)
                } else {
                    like_button.setBackgroundResource(R.drawable.ic_favourite_filled)
                    likes_count_text.text = (item.post.likes + 1).toString()
                    likedPosts.add(item.post.id)
                }
            }
        }
    }


}