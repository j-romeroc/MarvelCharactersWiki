package com.jarc.marvelcharacterswiki.ui.adapters

import android.graphics.BitmapFactory
import android.view.ViewGroup
import androidx.lifecycle.LiveData
import androidx.lifecycle.findViewTreeLifecycleOwner
import androidx.recyclerview.widget.RecyclerView
import com.jarc.domain.models.CharacterModel
import com.jarc.marvelcharacterswiki.R
import com.jarc.marvelcharacterswiki.ui.models.CharacterImage
import com.jarc.marvelcharacterswiki.ui.fragments.CharacterImageListener


class CharacterListAdapter(
    private val listener: CharacterImageListener,
    private val characterImageLiveData: LiveData<CharacterImage>
) :
    RecyclerView.Adapter<CharacterListViewHolder>() {

    var characters: MutableList<CharacterModel> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterListViewHolder {

        parent.findViewTreeLifecycleOwner()

        return CharacterListViewHolder.create(parent, characterImageLiveData, parent.context)
    }

    override fun onBindViewHolder(holder: CharacterListViewHolder, position: Int) {
        holder.bind(characters[position], position)
        listener.getCharacterImage(characters[position].thumbnail, position)
/*
        holder.itemView.character_id.text = characters[position].id.toString()
        holder.itemView.character_name.text = characters[position].name

        viewModel.getImageForList(characters[position].thumbnail, AspectRatio.Origin.LIST) { result ->

            val context = holder.itemView.context

            result.onSuccess { holder.itemView.character_img.setImageBitmap(it) }

            result.onFailure {
                val defaultBmp = BitmapFactory.decodeResource(
                    context.resources,
                    R.drawable.image_not_available_marvel
                )
                holder.itemView.character_img.setImageBitmap(defaultBmp)
            }

        }

        holder.itemView.setOnClickListener {
            val action =
                CharactersListFragmentDirections.actionInitFragmentToSecondFragment(characters[position].id.toString())

            holder.itemView.findNavController().navigate(action)
        }
*/

    }


    override fun getItemCount(): Int {
        return characters.count()
    }


    /*   internal inner class CharactersListsViewHolder(itemView: View) :
           RecyclerView.ViewHolder(itemView)*/

}