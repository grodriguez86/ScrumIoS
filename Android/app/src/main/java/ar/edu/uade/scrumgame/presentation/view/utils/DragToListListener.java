package ar.edu.uade.scrumgame.presentation.view.utils;

import android.view.DragEvent;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

import ar.edu.uade.scrumgame.R;
import ar.edu.uade.scrumgame.presentation.models.GameContentModel;
import ar.edu.uade.scrumgame.presentation.view.adapter.DragDropAdapter;

public class DragToListListener implements View.OnDragListener {
    private Boolean dropped = false;

    @Override
    public boolean onDrag(View v, DragEvent event) {
        View dragView = (View) event.getLocalState();
        int action = event.getAction();
        if (action == DragEvent.ACTION_DROP) {
            dropped = true;
            RecyclerView target;
            if (v.getId() == R.id.options_rv ) {
                target = v.getRootView().findViewById(R.id.options_rv);
            } else if (v.getId() == R.id.answers_rv ) {
                target = v.getRootView().findViewById(R.id.answers_rv);
            } else {
                target = (RecyclerView) v.getParent();
            }
            RecyclerView source = (RecyclerView) dragView.getParent();
            DragDropAdapter adapterSource = (DragDropAdapter) source.getAdapter();
            if (adapterSource != null && target != null) {
                int positionSource = (int) dragView.getTag();
                GameContentModel gameContentModel = adapterSource.getGameContentModelAt(positionSource);
                DragDropAdapter adapterTarget = (DragDropAdapter) target.getAdapter();
                if (gameContentModel != null && adapterTarget != null) {
                    adapterSource.removeItem(positionSource);
                    adapterTarget.addItem(gameContentModel);
                    v.setVisibility(View.VISIBLE);
                }
            }
        }
        if ((event.getAction() == DragEvent.ACTION_DRAG_ENDED && !event.getResult()) || !dropped) {
            dragView.post(() -> dragView.setVisibility(View.VISIBLE));
        }
        return true;
    }
}