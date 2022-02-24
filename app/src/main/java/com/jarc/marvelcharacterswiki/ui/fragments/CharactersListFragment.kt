package com.jarc.marvelcharacterswiki.ui.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jarc.core.utils.CustomError
import com.jarc.core.utils.LayerResult
import com.jarc.domain.models.CharacterModel
import com.jarc.marvelcharacterswiki.R
import com.jarc.marvelcharacterswiki.databinding.FragmentCharactersListBinding
import com.jarc.marvelcharacterswiki.ui.adapters.CharacterListAdapter
import com.jarc.marvelcharacterswiki.models.CharacterModel
import com.jarc.marvelcharacterswiki.ui.presenters.CharacterPresenterImpl
import com.jarc.marvelcharacterswiki.ui.utils.ViewUtils
import com.jarc.marvelcharacterswiki.ui.presenters.CharacterPresenter
import com.jarc.marvelcharacterswiki.ui.viewmodels.CharacterViewModel
import kotlinx.android.synthetic.main.fragment_characters_list.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.java.KoinJavaComponent.inject

class CharactersListFragment : Fragment() {

    private val viewModel by viewModel<CharacterViewModel>()

    private var adapter: CharacterListAdapter =
        CharacterListAdapter(viewModel)

    private lateinit var binding: FragmentCharactersListBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCharactersListBinding.inflate(inflater)
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        if (adapter.characters.isNullOrEmpty())
            askForData()
    }


    private fun setupRecyclerView() {

        binding.rvCharacters.apply {

            this.layoutManager = activity?.let { fa ->
                LinearLayoutManager(
                    fa
                )
            }

            this.addOnScrollListener(object :
                CharactersListScrollListener(this.layoutManager as LinearLayoutManager) {
                override fun loadMoreItems() {
                    askForData()
                }

                override fun isLastPage() = false

                override fun isLoading() = progressBar.visibility == View.VISIBLE

            })
        }

        binding.rvCharacters.adapter = adapter
    }


    private fun askForData() {
        progressBar?.visibility = View.VISIBLE

        presenter.fetchCharacterList { result ->

            activity?.runOnUiThread {

                progressBar?.visibility = View.GONE

                when (result) {

                    is LayerResult.Success -> {
                        result.value?.let { renderView(it) }
                    }
                    is LayerResult.Error -> {
                        renderError(result.error as CustomError)
                    }
                }
            }
        }
    }

    private fun renderView(characters: List<CharacterModel>) {
        val lastPosition = if (adapter.characters.isNullOrEmpty()) 0 else adapter.characters.size
        adapter.characters.addAll(characters)

        Log.d("Fragment List", "CharacterslistReceived")

        adapter.notifyItemRangeInserted(lastPosition, characters.size)

    }

    private fun renderError(errorInfo: CustomError) {

        val errorOriginLayer = errorInfo.getErrorOriginLayerMsg()
        val errorDescription = errorInfo.getErrorDetailedMsg()
        activity?.let {
            ViewUtils.onDialog(
                "Error: <$errorDescription> \nThrown in $errorOriginLayer \nShould retry?",
                it
            ) {
                askForData()
            }
        }
        Log.d("Fragment List", "Error: ${errorInfo.localizedMessage}")
    }


}

private abstract class CharactersListScrollListener(val layoutManager: LinearLayoutManager) :
    RecyclerView.OnScrollListener() {

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)

        val PAGE_SIZE = 4

        val visibleItemCount = layoutManager.childCount
        val totalItemCount = layoutManager.itemCount
        val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()

        if (!isLoading() && !isLastPage()) {
            if (visibleItemCount + firstVisibleItemPosition >= totalItemCount
                && firstVisibleItemPosition >= 0
                && totalItemCount >= PAGE_SIZE
            ) {
                loadMoreItems()
            }
        }
    }

    abstract fun loadMoreItems()

    abstract fun isLastPage(): Boolean

    abstract fun isLoading(): Boolean

}