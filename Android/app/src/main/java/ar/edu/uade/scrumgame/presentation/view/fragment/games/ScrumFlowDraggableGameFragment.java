package ar.edu.uade.scrumgame.presentation.view.fragment.games;

import android.annotation.SuppressLint;
import android.view.View;
import android.widget.ImageView;

import com.github.florent37.viewtooltip.ViewTooltip;

import ar.edu.uade.scrumgame.R;
import ar.edu.uade.scrumgame.presentation.view.fragment.GameFragment;
import ar.edu.uade.scrumgame.presentation.view.utils.DragListener;
import ar.edu.uade.scrumgame.presentation.view.utils.TouchListener;
import butterknife.BindView;

public class ScrumFlowDraggableGameFragment extends GameFragment implements DragListener.OnDragListener {
    private static final Integer DRAGGABLE_VIEWS = 5;
    @BindView(R.id.productBacklogContainer)
    ImageView productBacklogContainer;
    @BindView(R.id.productBacklog)
    ImageView productBacklog;
    @BindView(R.id.sprintBacklogContainer)
    ImageView sprintBacklogContainer;
    @BindView(R.id.sprintBacklog)
    ImageView sprintBacklog;
    @BindView(R.id.incrementContainer)
    ImageView incrementContainer;
    @BindView(R.id.increment)
    ImageView increment;
    @BindView(R.id.dailyMeetupContainer)
    ImageView dailyMeetUpContainer;
    @BindView(R.id.dailyMeetup)
    ImageView dailyMeetUp;
    @BindView(R.id.sprintContainer)
    ImageView sprintContainer;
    @BindView(R.id.sprint)
    ImageView sprint;
    private Integer correctDrags = 0;

    @Override
    protected Integer getFragmentId() {
        return R.layout.fragment_game_scrum_draggable;
    }

    @Override
    protected void doLoadGame() {
        this.setUpListeners();
    }

    @SuppressLint("ClickableViewAccessibility")
    private void setUpListeners() {
        DragListener.OnDragListener dragListener = this;
        sprintContainer.setOnDragListener(new DragListener(dragListener));
        productBacklogContainer.setOnDragListener(new DragListener(dragListener));
        incrementContainer.setOnDragListener(new DragListener(dragListener));
        sprintBacklogContainer.setOnDragListener(new DragListener(dragListener));
        dailyMeetUpContainer.setOnDragListener(new DragListener(dragListener));
        sprint.setOnTouchListener(new TouchListener());
        productBacklog.setOnTouchListener(new TouchListener());
        increment.setOnTouchListener(new TouchListener());
        sprintBacklog.setOnTouchListener(new TouchListener());
        dailyMeetUp.setOnTouchListener(new TouchListener());
    }

    private void checkCorrectDrags(View view) {
        this.showTooltip(view, v -> {
            this.correctDrags++;
            super.checkAttempt();
            super.onCorrectAttempt();
            if (this.hasCompletedGame()) {
                if (this.onGameCompletedListener != null) {
                    this.onGameCompletedListener.onGameCompleted(gameCode);
                }
            }
        });
    }

    private boolean hasCompletedGame() {
        return this.correctDrags.equals(DRAGGABLE_VIEWS);
    }

    private void showTooltip(View view, ViewTooltip.ListenerHide onHideListener) {
        ViewTooltip.on(view)
                .position(ViewTooltip.Position.BOTTOM)
                .autoHide(true, 10000)
                .onHide(onHideListener)
                .clickToHide(true)
                .corner(30)
                .text(view.getContentDescription().toString())
                .show();
    }

    @Override
    public void onCorrectDrag(View view) {
        this.checkCorrectDrags(view);
    }

    @Override
    public void onFailedDrag(View view) {
        super.checkAttempt();
        super.onFailedAttempt();
    }
}
