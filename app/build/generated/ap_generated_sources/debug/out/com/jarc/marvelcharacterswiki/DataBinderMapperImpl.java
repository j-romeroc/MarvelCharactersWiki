package com.jarc.marvelcharacterswiki;

import android.util.SparseArray;
import android.util.SparseIntArray;
import android.view.View;
import androidx.databinding.DataBinderMapper;
import androidx.databinding.DataBindingComponent;
import androidx.databinding.ViewDataBinding;
import com.jarc.marvelcharacterswiki.databinding.CharacterItemBindingImpl;
import com.jarc.marvelcharacterswiki.databinding.FragmentCharacterDetailBindingImpl;
import com.jarc.marvelcharacterswiki.databinding.FragmentCharactersListBindingImpl;
import com.jarc.marvelcharacterswiki.databinding.FragmentWebViewBindingImpl;
import java.lang.IllegalArgumentException;
import java.lang.Integer;
import java.lang.Object;
import java.lang.Override;
import java.lang.RuntimeException;
import java.lang.String;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DataBinderMapperImpl extends DataBinderMapper {
  private static final int LAYOUT_CHARACTERITEM = 1;

  private static final int LAYOUT_FRAGMENTCHARACTERDETAIL = 2;

  private static final int LAYOUT_FRAGMENTCHARACTERSLIST = 3;

  private static final int LAYOUT_FRAGMENTWEBVIEW = 4;

  private static final SparseIntArray INTERNAL_LAYOUT_ID_LOOKUP = new SparseIntArray(4);

  static {
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.jarc.marvelcharacterswiki.R.layout.character_item, LAYOUT_CHARACTERITEM);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.jarc.marvelcharacterswiki.R.layout.fragment_character_detail, LAYOUT_FRAGMENTCHARACTERDETAIL);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.jarc.marvelcharacterswiki.R.layout.fragment_characters_list, LAYOUT_FRAGMENTCHARACTERSLIST);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.jarc.marvelcharacterswiki.R.layout.fragment_web_view, LAYOUT_FRAGMENTWEBVIEW);
  }

  @Override
  public ViewDataBinding getDataBinder(DataBindingComponent component, View view, int layoutId) {
    int localizedLayoutId = INTERNAL_LAYOUT_ID_LOOKUP.get(layoutId);
    if(localizedLayoutId > 0) {
      final Object tag = view.getTag();
      if(tag == null) {
        throw new RuntimeException("view must have a tag");
      }
      switch(localizedLayoutId) {
        case  LAYOUT_CHARACTERITEM: {
          if ("layout/character_item_0".equals(tag)) {
            return new CharacterItemBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for character_item is invalid. Received: " + tag);
        }
        case  LAYOUT_FRAGMENTCHARACTERDETAIL: {
          if ("layout/fragment_character_detail_0".equals(tag)) {
            return new FragmentCharacterDetailBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for fragment_character_detail is invalid. Received: " + tag);
        }
        case  LAYOUT_FRAGMENTCHARACTERSLIST: {
          if ("layout/fragment_characters_list_0".equals(tag)) {
            return new FragmentCharactersListBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for fragment_characters_list is invalid. Received: " + tag);
        }
        case  LAYOUT_FRAGMENTWEBVIEW: {
          if ("layout/fragment_web_view_0".equals(tag)) {
            return new FragmentWebViewBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for fragment_web_view is invalid. Received: " + tag);
        }
      }
    }
    return null;
  }

  @Override
  public ViewDataBinding getDataBinder(DataBindingComponent component, View[] views, int layoutId) {
    if(views == null || views.length == 0) {
      return null;
    }
    int localizedLayoutId = INTERNAL_LAYOUT_ID_LOOKUP.get(layoutId);
    if(localizedLayoutId > 0) {
      final Object tag = views[0].getTag();
      if(tag == null) {
        throw new RuntimeException("view must have a tag");
      }
      switch(localizedLayoutId) {
      }
    }
    return null;
  }

  @Override
  public int getLayoutId(String tag) {
    if (tag == null) {
      return 0;
    }
    Integer tmpVal = InnerLayoutIdLookup.sKeys.get(tag);
    return tmpVal == null ? 0 : tmpVal;
  }

  @Override
  public String convertBrIdToString(int localId) {
    String tmpVal = InnerBrLookup.sKeys.get(localId);
    return tmpVal;
  }

  @Override
  public List<DataBinderMapper> collectDependencies() {
    ArrayList<DataBinderMapper> result = new ArrayList<DataBinderMapper>(1);
    result.add(new androidx.databinding.library.baseAdapters.DataBinderMapperImpl());
    return result;
  }

  private static class InnerBrLookup {
    static final SparseArray<String> sKeys = new SparseArray<String>(1);

    static {
      sKeys.put(0, "_all");
    }
  }

  private static class InnerLayoutIdLookup {
    static final HashMap<String, Integer> sKeys = new HashMap<String, Integer>(4);

    static {
      sKeys.put("layout/character_item_0", com.jarc.marvelcharacterswiki.R.layout.character_item);
      sKeys.put("layout/fragment_character_detail_0", com.jarc.marvelcharacterswiki.R.layout.fragment_character_detail);
      sKeys.put("layout/fragment_characters_list_0", com.jarc.marvelcharacterswiki.R.layout.fragment_characters_list);
      sKeys.put("layout/fragment_web_view_0", com.jarc.marvelcharacterswiki.R.layout.fragment_web_view);
    }
  }
}
