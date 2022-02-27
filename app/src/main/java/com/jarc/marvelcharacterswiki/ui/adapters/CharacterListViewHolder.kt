package com.jarc.marvelcharacterswiki.ui.adapters

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.LiveData
import androidx.lifecycle.findViewTreeLifecycleOwner
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.jarc.domain.models.CharacterModel
import com.jarc.marvelcharacterswiki.R
import com.jarc.marvelcharacterswiki.ui.models.CharacterImage
import com.jarc.marvelcharacterswiki.databinding.CharacterItemBinding
import com.jarc.marvelcharacterswiki.ui.fragments.CharactersListFragmentDirections

class CharacterListViewHolder(
    private val binding: CharacterItemBinding,
    characterImageLiveData: LiveData<CharacterImage>,
    private val context: Context
) : RecyclerView.ViewHolder(binding.root) {

    private var characterPosition = 0

    companion object {
        @JvmStatic
        fun create(
            parent: ViewGroup, characterImageLiveData: LiveData<CharacterImage>, context: Context
        ): CharacterListViewHolder {
            val binding = CharacterItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
            binding.lifecycleOwner = parent.findViewTreeLifecycleOwner()
            return CharacterListViewHolder(
                binding, characterImageLiveData, context
            )
        }
    }

    init {
        characterImageLiveData.observe(requireNotNull(binding.lifecycleOwner)) {
            if (characterPosition == it.position) {
                val image = it.image
                    ?: BitmapFactory.decodeResource(
                        context.resources,
                        R.drawable.image_not_available_marvel
                    )
                binding.characterImg.setImageBitmap(image)
                binding.notifyChange()

            }
        }
    }

    fun bind(character: CharacterModel, position: Int) {
        characterPosition = position
        binding.tvCharacterId.text = character.id.toString()
        binding.tvCharacterName.text = character.name

        binding.itemMainLayout.setOnClickListener {
            val action =
                CharactersListFragmentDirections.actionInitFragmentToSecondFragment(character.id.toString())

            binding.root.findNavController().navigate(action)
        }
    }

}