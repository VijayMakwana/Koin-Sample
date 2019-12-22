package com.gojek.assignment.ui.repositories.trending


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.DividerItemDecoration
import com.gojek.assignment.BR
import com.gojek.assignment.R
import com.gojek.assignment.api.helper.AlertErrorHandler
import com.gojek.assignment.api.helper.Result
import com.gojek.assignment.api.helper.handleNoInternet
import com.gojek.assignment.api.model.RepoModel
import com.gojek.assignment.databinding.FragmentTrendingRepoBinding
import com.gojek.assignment.databinding.ItemTrendingRepoBinding
import com.gojek.assignment.utils.hide
import com.gojek.assignment.utils.liveadapter.LiveAdapter
import com.gojek.assignment.utils.setUpToolbar
import com.gojek.assignment.utils.show
import org.koin.androidx.viewmodel.ext.android.viewModel


/**
 * A simple [Fragment] subclass.
 */
class TrendingRepoFragment : Fragment() {

    private val mViewModel: TrendingRepoViewModel by viewModel()
    private lateinit var mBinding: FragmentTrendingRepoBinding
    private var mCurrentExpandedPos = -1

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        mBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_trending_repo, container, false)
        mBinding.lifecycleOwner = this
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // setup toolbar
        setUpToolbar(mBinding.toolbar, getString(R.string.title_trending)) {}

        // set error handler
        setupErrorHandler()

        // setup repo list
        setupRepoList()

        // add swipe to refresh
        setupSwipeToRefresh()

    }

    private fun setupSwipeToRefresh() {
        mBinding.swipeRefreshList.setOnRefreshListener {
            mViewModel.fetchTrendingRepos()
        }
    }

    private fun setupRepoList() {
        context?.let { ctx ->
            // set divider
            val itemDecorator =
                DividerItemDecoration(ctx, DividerItemDecoration.VERTICAL)
            ContextCompat.getDrawable(ctx, R.drawable.divider)?.let {
                itemDecorator.setDrawable(it)
            }

            mBinding.listTrendingRepo.addItemDecoration(itemDecorator)
        }

        // setup liveAdapter
        LiveAdapter(mViewModel.repoList, BR.repo)
            .map<RepoModel, ItemTrendingRepoBinding>(R.layout.item_trending_repo) {
                onBind { holder ->

                    // show/hide description as per mCurrentExpandedPos
                    if (holder.adapterPosition == mCurrentExpandedPos) {
                        holder.binding.repoDescContainer.show()
                    } else {
                        holder.binding.repoDescContainer.hide()
                    }
                }
                onClick { holder ->

                    if (mCurrentExpandedPos == holder.adapterPosition) {
                        mCurrentExpandedPos =
                            -1 //same pos is click which is already expanded then collapse that one
                        updateClickedItem(holder.adapterPosition)
                    } else {
                        val oldPosition = mCurrentExpandedPos // store old expanded pos
                        mCurrentExpandedPos = holder.adapterPosition // store current clicked pos
                        updateClickedItem(oldPosition)
                        updateClickedItem(mCurrentExpandedPos)
                    }

                }
            }
            .into(mBinding.listTrendingRepo)
    }

    /**
     * set error handling mechanism
     */
    private fun setupErrorHandler() {
        mBinding.layoutState.errorHandler = AlertErrorHandler(context) { isNetworkAvailable ->
            // manage no internet
            handleNoInternet(mBinding.layoutNoInternet, isNetworkAvailable) {
                // call fetch repository
                mViewModel.fetchTrendingRepos()
            }
        }

        @Suppress("UNCHECKED_CAST")
        mBinding.result = mViewModel.reposResult as LiveData<Result<Any>>
    }

    /**
     * update the Ui for current Item
     */
    private fun updateClickedItem(position: Int) {
        mBinding.listTrendingRepo.adapter?.notifyItemChanged(position)
    }

}
