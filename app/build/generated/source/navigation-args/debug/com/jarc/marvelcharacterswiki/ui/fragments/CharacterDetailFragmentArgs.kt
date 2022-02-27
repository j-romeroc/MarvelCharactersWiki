package com.jarc.marvelcharacterswiki.ui.fragments

import android.os.Bundle
import androidx.lifecycle.SavedStateHandle
import androidx.navigation.NavArgs
import java.lang.IllegalArgumentException
import kotlin.String
import kotlin.jvm.JvmStatic

public data class CharacterDetailFragmentArgs(
  public val characterId: String
) : NavArgs {
  public fun toBundle(): Bundle {
    val result = Bundle()
    result.putString("characterId", this.characterId)
    return result
  }

  public fun toSavedStateHandle(): SavedStateHandle {
    val result = SavedStateHandle()
    result.set("characterId", this.characterId)
    return result
  }

  public companion object {
    @JvmStatic
    public fun fromBundle(bundle: Bundle): CharacterDetailFragmentArgs {
      bundle.setClassLoader(CharacterDetailFragmentArgs::class.java.classLoader)
      val __characterId : String?
      if (bundle.containsKey("characterId")) {
        __characterId = bundle.getString("characterId")
        if (__characterId == null) {
          throw IllegalArgumentException("Argument \"characterId\" is marked as non-null but was passed a null value.")
        }
      } else {
        throw IllegalArgumentException("Required argument \"characterId\" is missing and does not have an android:defaultValue")
      }
      return CharacterDetailFragmentArgs(__characterId)
    }

    @JvmStatic
    public fun fromSavedStateHandle(savedStateHandle: SavedStateHandle):
        CharacterDetailFragmentArgs {
      val __characterId : String?
      if (savedStateHandle.contains("characterId")) {
        __characterId = savedStateHandle["characterId"]
        if (__characterId == null) {
          throw IllegalArgumentException("Argument \"characterId\" is marked as non-null but was passed a null value")
        }
      } else {
        throw IllegalArgumentException("Required argument \"characterId\" is missing and does not have an android:defaultValue")
      }
      return CharacterDetailFragmentArgs(__characterId)
    }
  }
}
