package com.ph00.instagramfirstpageapp.ui.adapters.stories

import androidx.recyclerview.widget.RecyclerView
import com.example.delegateadapter.delegate.KDelegateAdapter
import com.example.delegateadapter.delegate.diff.DiffUtilCompositeAdapter
import com.ph00.instagramfirstpageapp.R
import kotlinx.android.synthetic.main.item_stories.*

class StoriesItemDelegateAdapter(
    private val diffAdapter: DiffUtilCompositeAdapter,
    private val rvLayoutManager: RecyclerView.LayoutManager
) :
    KDelegateAdapter<StoriesItemViewModel>() {

    override fun getLayoutId() = R.layout.item_stories

    override fun isForViewType(items: MutableList<*>, position: Int) =
        items[position] is StoriesItemViewModel

    override fun onBind(item: StoriesItemViewModel, viewHolder: KViewHolder) {
        viewHolder.story_recycler.apply {
            adapter = diffAdapter
            layoutManager = rvLayoutManager
        }
        diffAdapter.swapData(item.list)
    }
}