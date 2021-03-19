package ar.edu.uade.scrumgame.presentation.view.utils;

import android.view.DragEvent;
import android.view.View;
import android.widget.ImageView;

public class DragListener implements View.OnDragListener {
    public interface OnDragListener {
        void onCorrectDrag(View view);

        void onFailedDrag(View view);
    }

    private OnDragListener dragListener;

    public DragListener(OnDragListener dragListener) {
        this.dragListener = dragListener;
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
                if (dragListener != null) {
                    dragListener.onCorrectDrag(dropTarget);
                }
            } else {
                dragView.setVisibility(View.VISIBLE);
                if (dragListener != null) {
                    dragListener.onFailedDrag(dropTarget);
                }
            }
        }
        if (event.getAction() == DragEvent.ACTION_DRAG_ENDED && !event.getResult()) {
            dragView.setVisibility(View.VISIBLE);
        }
        return true;
    }
}
