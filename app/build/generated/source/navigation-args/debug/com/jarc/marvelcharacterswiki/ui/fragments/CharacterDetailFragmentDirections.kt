package com.jarc.marvelcharacterswiki.ui.fragments

import android.os.Bundle
import androidx.navigation.NavDirections
import com.jarc.marvelcharacterswiki.R
import kotlin.Int
import kotlin.String

public class CharacterDetailFragmentDirections private constructor() {
  private data class ActionSecondFragmentToWebview(
    public val url: String
  ) : NavDirections {
    public override val actionId: Int = R.id.action_secondFragment_to_webview

    public override val arguments: Bundle
      get() {
        val result = Bundle()
        result.putString("url", this.url)
        return result
      }
  }

  public companion object {
    public fun actionSecondFragmentToWebview(url: String): NavDirections =
        ActionSecondFragmentToWebview(url)
  }
}
