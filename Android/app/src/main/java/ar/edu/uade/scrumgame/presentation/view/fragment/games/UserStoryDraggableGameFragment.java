package ar.edu.uade.scrumgame.presentation.view.fragment.games;

import android.annotation.SuppressLint;
import android.view.View;
import android.widget.ImageView;

import ar.edu.uade.scrumgame.R;
import ar.edu.uade.scrumgame.presentation.view.fragment.GameFragment;
import ar.edu.uade.scrumgame.presentation.view.utils.DragListener;
import ar.edu.uade.scrumgame.presentation.view.utils.TouchListener;
import butterknife.BindView;

public class UserStoryDraggableGameFragment extends GameFragment implements DragListener.OnDragListener {
    private static final Integer DRAGGABLE_VIEWS = 3;
    @BindView(R.id.compradorContainer)
    ImageView compradorContainer;
    @BindView(R.id.busquedaContainer)
    ImageView busquedaContainer;
    @BindView(R.id.productoContainer)
    ImageView productoContainer;
    @BindView(R.id.producto)
    ImageView producto;
    @BindView(R.id.pago)
    ImageView pago;
    @BindView(R.id.busqueda)
    ImageView busqueda;
    @BindView(R.id.comprador)
    ImageView comprador;
    @BindView(R.id.duenio)
    ImageView duenio;
    private Integer correctDrags = 0;

    @Override
    protected Integer getFragmentId() {
        return R.layout.fragment_game_user_story_draggable;
    }

    @Override
    protected void doLoadGame() {
        this.setUpListeners();
    }

    @Override
    protected void doLoadCompletedGame() {
        this.compradorContainer.setAlpha(1f);
        this.busquedaContainer.setAlpha(1f);
        this.productoContainer.setAlpha(1f);
        this.comprador.setVisibility(View.INVISIBLE);
        this.busqueda.setVisibility(View.INVISIBLE);
        this.producto.setVisibility(View.INVISIBLE);
        this.duenio.setVisibility(View.INVISIBLE);
        this.pago.setVisibility(View.INVISIBLE);
    }

    @SuppressLint("ClickableViewAccessibility")
    private void setUpListeners() {
        DragListener.OnDragListener dragListener = this;
        this.compradorContainer.setOnDragListener(new DragListener(dragListener));
        this.busquedaContainer.setOnDragListener(new DragListener(dragListener));
        this.productoContainer.setOnDragListener(new DragListener(dragListener));
        this.comprador.setOnTouchListener(new TouchListener());
        this.busqueda.setOnTouchListener(new TouchListener());
        this.producto.setOnTouchListener(new TouchListener());
        this.duenio.setOnTouchListener(new TouchListener());
        this.pago.setOnTouchListener(new TouchListener());
    }

    private void checkCorrectDrags() {
        this.correctDrags++;
        super.checkAttempt();
        super.onCorrectAttempt();
        if (this.hasCompletedGame()) {
            if (this.onGameCompletedListener != null) {
                this.onGameCompletedListener.onGameCompleted(gameCode);
            }
        }
    }

    private boolean hasCompletedGame() {
        return this.correctDrags.equals(DRAGGABLE_VIEWS);
    }

    @Override
    public void onCorrectDrag(View view) {
        this.checkCorrectDrags();
    }

    @Override
    public void onFailedDrag(View view) {
        super.checkAttempt();
        super.onFailedAttempt();
    }
}
