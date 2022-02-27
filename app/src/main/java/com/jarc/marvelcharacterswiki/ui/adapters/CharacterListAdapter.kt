package com.jarc.marvelcharacterswiki.ui.adapters

import android.view.ViewGroup
import androidx.lifecycle.LiveData
import androidx.lifecycle.findViewTreeLifecycleOwner
import androidx.recyclerview.widget.RecyclerView
import com.jarc.domain.models.CharacterModel
import com.jarc.marvelcharacterswiki.ui.fragments.CharacterImageListener
import com.jarc.marvelcharacterswiki.ui.models.CharacterImage


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
    }


    override fun getItemCount(): Int {
        return characters.count()
    }


}