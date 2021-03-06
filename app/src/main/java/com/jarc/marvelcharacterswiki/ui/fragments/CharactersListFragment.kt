package com.jarc.marvelcharacterswiki.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jarc.core.utils.CustomError
import com.jarc.domain.models.CharacterModel
import com.jarc.domain.models.Thumbnail
import com.jarc.marvelcharacterswiki.databinding.FragmentCharactersListBinding
import com.jarc.marvelcharacterswiki.ui.adapters.CharacterListAdapter
import com.jarc.marvelcharacterswiki.ui.utils.ViewUtils
import com.jarc.marvelcharacterswiki.ui.viewmodels.CharacterViewModel
import kotlinx.android.synthetic.main.fragment_characters_list.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class CharactersListFragment : Fragment(), CharacterImageListener {

    private val viewModel by viewModel<CharacterViewModel>()

    private lateinit var adapter: CharacterListAdapter

    private lateinit var binding: FragmentCharactersListBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        adapter = CharacterListAdapter(this, viewModel.listImageLiveData)
        binding = FragmentCharactersListBinding.inflate(inflater)
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupObservers()
        setupRecyclerView()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        if (adapter.characters.isNullOrEmpty()) {
            viewModel.getCharacterList()
        }
    }

    private fun setupObservers() {
        viewModel.characterListLiveData.observe(viewLifecycleOwner) {
            renderView(it)
        }

        viewModel.getListError.observe(viewLifecycleOwner) {
            renderError(it as CustomError)
        }
    }


    private fun setupRecyclerView() {

        progressBar?.visibility = View.VISIBLE

        binding.rvCharacters.apply {

            this.layoutManager = activity?.let { fragmentActivity ->
                LinearLayoutManager(
                    fragmentActivity
                )
            }

            this.addOnScrollListener(object :
                CharactersListScrollListener(this.layoutManager as LinearLayoutManager) {
                override fun loadMoreItems() {
                    viewModel.getCharacterList()
                }

                override fun isLastPage() = false

                override fun isLoading() = progressBar.visibility == View.VISIBLE

            })
        }

        binding.rvCharacters.adapter = adapter
    }

    private fun renderView(characters: List<CharacterModel>) {
        progressBar?.visibility = View.GONE

        val lastPosition = if (adapter.characters.isNullOrEmpty()) 0 else adapter.characters.size
        adapter.characters.addAll(characters)

        Log.d("FragmentList", "CharacterslistReceived")

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
                viewModel.getCharacterList()
            }
        }
        Log.d("FragmentList", "Error: ${errorInfo.localizedMessage}")
    }

    override fun getCharacterImage(
        imageInfo: Thumbnail,
        position: Int
    ) {
        viewModel.getImageForList(imageInfo, position)
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