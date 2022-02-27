package com.jarc.marvelcharacterswiki.databinding;
import com.jarc.marvelcharacterswiki.R;
import com.jarc.marvelcharacterswiki.BR;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.View;
@SuppressWarnings("unchecked")
public class FragmentCharacterDetailBindingImpl extends FragmentCharacterDetailBinding  {

    @Nullable
    private static final androidx.databinding.ViewDataBinding.IncludedLayouts sIncludes;
    @Nullable
    private static final android.util.SparseIntArray sViewsWithIds;
    static {
        sIncludes = null;
        sViewsWithIds = new android.util.SparseIntArray();
        sViewsWithIds.put(R.id.mainContent, 1);
        sViewsWithIds.put(R.id.appBar, 2);
        sViewsWithIds.put(R.id.collapsingToolbar, 3);
        sViewsWithIds.put(R.id.containerImageLayout, 4);
        sViewsWithIds.put(R.id.characterImage, 5);
        sViewsWithIds.put(R.id.textLayout, 6);
        sViewsWithIds.put(R.id.tvCharacterName, 7);
        sViewsWithIds.put(R.id.tvCharacterDescription, 8);
        sViewsWithIds.put(R.id.appearsOnLayout, 9);
        sViewsWithIds.put(R.id.tvAppearsOn, 10);
        sViewsWithIds.put(R.id.tvComicsCount, 11);
        sViewsWithIds.put(R.id.tvSeriesCount, 12);
        sViewsWithIds.put(R.id.tvStoriesCount, 13);
        sViewsWithIds.put(R.id.btnMoreInfo, 14);
    }
    // views
    @NonNull
    private final androidx.constraintlayout.widget.ConstraintLayout mboundView0;
    // variables
    // values
    // listeners
    // Inverse Binding Event Handlers

    public FragmentCharacterDetailBindingImpl(@Nullable androidx.databinding.DataBindingComponent bindingComponent, @NonNull View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 15, sIncludes, sViewsWithIds));
    }
    private FragmentCharacterDetailBindingImpl(androidx.databinding.DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 0
            , (com.google.android.material.appbar.AppBarLayout) bindings[2]
            , (android.widget.LinearLayout) bindings[9]
            , (androidx.appcompat.widget.AppCompatButton) bindings[14]
            , (android.widget.ImageView) bindings[5]
            , (com.google.android.material.appbar.CollapsingToolbarLayout) bindings[3]
            , (androidx.constraintlayout.widget.ConstraintLayout) bindings[4]
            , (androidx.coordinatorlayout.widget.CoordinatorLayout) bindings[1]
            , (androidx.constraintlayout.widget.ConstraintLayout) bindings[6]
            , (android.widget.TextView) bindings[10]
            , (android.widget.TextView) bindings[8]
            , (android.widget.TextView) bindings[7]
            , (android.widget.TextView) bindings[11]
            , (android.widget.TextView) bindings[12]
            , (android.widget.TextView) bindings[13]
            );
        this.mboundView0 = (androidx.constraintlayout.widget.ConstraintLayout) bindings[0];
        this.mboundView0.setTag(null);
        setRootTag(root);
        // listeners
        invalidateAll();
    }

    @Override
    public void invalidateAll() {
        synchronized(this) {
                mDirtyFlags = 0x1L;
        }
        requestRebind();
    }

    @Override
    public boolean hasPendingBindings() {
        synchronized(this) {
            if (mDirtyFlags != 0) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean setVariable(int variableId, @Nullable Object variable)  {
        boolean variableSet = true;
            return variableSet;
    }

    @Override
    protected boolean onFieldChange(int localFieldId, Object object, int fieldId) {
        switch (localFieldId) {
        }
        return false;
    }

    @Override
    protected void executeBindings() {
        long dirtyFlags = 0;
        synchronized(this) {
            dirtyFlags = mDirtyFlags;
            mDirtyFlags = 0;
        }
        // batch finished
    }
    // Listener Stub Implementations
    // callback impls
    // dirty flag
    private  long mDirtyFlags = 0xffffffffffffffffL;
    /* flag mapping
        flag 0 (0x1L): null
    flag mapping end*/
    //end
}