package com.jarc.marvelcharacterswiki.ui.adapters

import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.jarc.core.utils.AspectRatio
import com.jarc.domain.models.CharacterModel
import com.jarc.marvelcharacterswiki.R
import com.jarc.marvelcharacterswiki.ui.fragments.CharactersListFragmentDirections
import com.jarc.marvelcharacterswiki.ui.viewmodels.CharacterViewModel
import kotlinx.android.synthetic.main.character_item.view.*


class CharacterListAdapter(private val viewModel: CharacterViewModel) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var characters: MutableList<CharacterModel> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return CharactersListsViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.character_item, parent, false)
        )

    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
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

    }

    override fun getItemCount(): Int {
        return characters.count()
    }

    internal inner class CharactersListsViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView)

}