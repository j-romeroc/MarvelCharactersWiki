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
import com.jarc.marvelcharacterswiki.models.CharacterDetailModel
import com.jarc.marvelcharacterswiki.models.Thumbnail
import com.jarc.marvelcharacterswiki.ui.presenters.CharacterPresenter
import com.jarc.marvelcharacterswiki.ui.utils.ViewUtils
import kotlinx.android.synthetic.main.fragment_character_detail.*
import org.koin.java.KoinJavaComponent.inject

class CharacterDetailFragment : Fragment() {

    private val presenter: CharacterPresenter by inject(CharacterPresenter::class.java)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_character_detail, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val characterId = arguments?.let { CharacterDetailFragmentArgs.fromBundle(it).characterId}

        if (characterId != null) {
            presenter.fetchCharacterDetail(characterId){ result ->

                activity?.runOnUiThread{

                    when(result) {
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

        character_name.text = character.name
        character_description.text = if(character.description.isNotEmpty()) character.description else "Description not available"


        comics_count.text = "${character.comicsCount} Comics"
        series_count.text = "${character.seriesCount} Series"
        stories_count.text = "${character.storiesCount} Stories"

        more_info_btn.setOnClickListener {
            val action = CharacterDetailFragmentDirections.actionSecondFragmentToWebview(character.detailUrl)
            more_info_btn.findNavController().navigate(action)
        }

        presenter.fetchImage(
            imageInfo = Thumbnail(character.thumbnail.path,character.thumbnail.extension),
            origin = AspectRatio.Origin.DETAIL){bmp ->

            activity?.runOnUiThread {

                when (bmp) {
                    is LayerResult.Success -> {

                        character_image.setImageBitmap(bmp.value)
                    }
                    is LayerResult.Error -> {

                        val defaultBmp = BitmapFactory.decodeResource(
                            activity?.resources,
                            R.drawable.image_not_available_marvel
                        )

                        character_image.setImageBitmap(defaultBmp)
                    }
                }

            }

        }

    }

    private fun renderError(errorInfo: Throwable) {

        activity?.let {

            ViewUtils.onDialog("Error getting Marvel Character",
                it){}
        }
    }

}