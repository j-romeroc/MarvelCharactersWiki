package com.jarc.marvelcharacterswiki.ui.fragments

import android.os.Bundle
import androidx.navigation.NavDirections
import com.jarc.marvelcharacterswiki.R
import kotlin.Int
import kotlin.String

public class CharactersListFragmentDirections private constructor() {
  private data class ActionInitFragmentToSecondFragment(
    public val characterId: String
  ) : NavDirections {
    public override val actionId: Int = R.id.action_initFragment_to_secondFragment

    public override val arguments: Bundle
      get() {
        val result = Bundle()
        result.putString("characterId", this.characterId)
        return result
      }
  }

  public companion object {
    public fun actionInitFragmentToSecondFragment(characterId: String): NavDirections =
        ActionInitFragmentToSecondFragment(characterId)
  }
}
