package com.jarc.marvelcharacterswiki.ui.fragments

import android.graphics.BitmapFactory
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import com.jarc.core.utils.AspectRatio
import com.jarc.core.utils.LayerResult
import com.jarc.marvelcharacterswiki.R
import com.jarc.marvelcharacterswiki.databinding.FragmentCharacterDetailBinding
import com.jarc.marvelcharacterswiki.models.CharacterDetailModel
import com.jarc.marvelcharacterswiki.models.Thumbnail
import com.jarc.marvelcharacterswiki.ui.presenters.CharacterPresenter
import com.jarc.marvelcharacterswiki.ui.utils.ViewUtils
import kotlinx.android.synthetic.main.fragment_character_detail.*
import org.koin.java.KoinJavaComponent.inject

class CharacterDetailFragment : Fragment() {

    private val presenter: CharacterPresenter by inject(CharacterPresenter::class.java)

    private lateinit var binding: FragmentCharacterDetailBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCharacterDetailBinding.inflate(inflater)
        binding.lifecycleOwner = this
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val characterId = arguments?.let { CharacterDetailFragmentArgs.fromBundle(it).characterId }

        if (characterId != null) {
            presenter.fetchCharacterDetail(characterId) { result ->

                activity?.runOnUiThread {

                    when (result) {
                        is LayerResult.Success -> {
                            result.value?.let { renderView(it) }
                        }
                        is LayerResult.Error -> {
                            renderError(result.error)
                        }
                    }
                }
            }
        }
    }


    private fun renderView(character: CharacterDetailModel) {

        binding.tvCharacterName.text = character.name
        binding.tvCharacterDescription.text =
            character.description.ifEmpty { "Description not available" }

        binding.tvComicsCount.text = "${character.comicsCount} Comics"
        binding.tvSeriesCount.text = "${character.seriesCount} Series"
        binding.tvStoriesCount.text = "${character.storiesCount} Stories"

        binding.btnMoreInfo.setOnClickListener {
            val action =
                CharacterDetailFragmentDirections.actionSecondFragmentToWebview(character.detailUrl)
            binding.btnMoreInfo.findNavController().navigate(action)
        }

        presenter.fetchImage(
            imageInfo = Thumbnail(character.thumbnail.path, character.thumbnail.extension),
            origin = AspectRatio.Origin.DETAIL
        ) { bmp ->

            activity?.runOnUiThread {

                when (bmp) {
                    is LayerResult.Success -> {

                        binding.characterImage.setImageBitmap(bmp.value)
                    }
                    is LayerResult.Error -> {

                        val defaultBmp = BitmapFactory.decodeResource(
                            activity?.resources,
                            R.drawable.image_not_available_marvel
                        )

                        binding.characterImage.setImageBitmap(defaultBmp)
                    }
                }

            }

        }

    }

    private fun renderError(errorInfo: Throwable) {

        activity?.let {

            ViewUtils.onDialog(
                "Error getting Marvel Character",
                it
            ) {}
        }
    }

}