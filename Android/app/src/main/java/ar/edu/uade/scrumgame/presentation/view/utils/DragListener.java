package ar.edu.uade.scrumgame.presentation.view.utils;

import android.view.DragEvent;
import android.view.View;
import android.widget.ImageView;

public class DragListener implements View.OnDragListener {
    public interface OnCorrectDragListener {
        void onCorrectDrag(View view);
    }

    private OnCorrectDragListener onCorrectDragListener;

    public DragListener(OnCorrectDragListener onCorrectDragListener) {
        this.onCorrectDragListener = onCorrectDragListener;
    }

    @Override
    public boolean onDrag(View v, DragEvent event) {
        View dragView = (View) event.getLocalState();
        if (event.getAction() == DragEvent.ACTION_DROP) {
            dragView.setVisibility(View.INVISIBLE);
            ImageView dropTarget = (ImageView) v;
            ImageView dropped = (ImageView) dragView;
            String tagDropTarget = (String) dropTarget.getTag(),
                    tagDroppedImage = (String) dropped.getTag();
            if ((tagDropTarget != null) && (tagDropTarget.equals(tagDroppedImage))) {
                dropTarget.setVisibility(View.VISIBLE);
                dropTarget.setAlpha(1f);
                if (onCorrectDragListener != null) {
                    onCorrectDragListener.onCorrectDrag(dropTarget);
                }
            } else {
                dragView.setVisibility(View.VISIBLE);
            }
        }
        if (event.getAction() == DragEvent.ACTION_DRAG_ENDED && !event.getResult()) {
            dragView.setVisibility(View.VISIBLE);
        }
        return true;


    }
}
