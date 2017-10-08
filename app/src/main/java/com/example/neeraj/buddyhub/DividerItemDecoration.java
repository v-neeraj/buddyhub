package com.example.neeraj.buddyhub;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by ankitgarg on 06/10/17.
 */

public class DividerItemDecoration extends RecyclerView.ItemDecoration {

    private int height;

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);



        if (parent.getChildAdapterPosition(view) != parent.getAdapter().getItemCount() - 1) {
            outRect.bottom = height;
        }
    }

    public DividerItemDecoration(int verticalSpaceHeight) {
        height=verticalSpaceHeight;
    }
}
