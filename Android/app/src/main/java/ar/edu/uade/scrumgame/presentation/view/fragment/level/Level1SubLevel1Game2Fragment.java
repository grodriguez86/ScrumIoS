package ar.edu.uade.scrumgame.presentation.view.fragment.level;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.Nullable;

import com.github.florent37.viewtooltip.ViewTooltip;

import ar.edu.uade.scrumgame.R;
import ar.edu.uade.scrumgame.presentation.view.fragment.GameFragment;
import ar.edu.uade.scrumgame.presentation.view.utils.DragListener;
import ar.edu.uade.scrumgame.presentation.view.utils.TouchListener;
import butterknife.BindView;
import butterknife.ButterKnife;

public class Level1SubLevel1Game2Fragment extends GameFragment {
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


    public Level1SubLevel1Game2Fragment() {
        this.setRetainInstance(true);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View fragmentView = inflater.inflate(R.layout.fragment_level_one_sublevel_two, container, false);
        ButterKnife.bind(this, fragmentView);
        this.setUpListeners();
        return fragmentView;
    }

    @SuppressLint("ClickableViewAccessibility")
    private void setUpListeners() {
        DragListener.OnCorrectDragListener onCorrectDragListener = this::checkCorrectDrags;
        sprintContainer.setOnDragListener(new DragListener(onCorrectDragListener));
        productBacklogContainer.setOnDragListener(new DragListener(onCorrectDragListener));
        incrementContainer.setOnDragListener(new DragListener(onCorrectDragListener));
        sprintBacklogContainer.setOnDragListener(new DragListener(onCorrectDragListener));
        dailyMeetUpContainer.setOnDragListener(new DragListener(onCorrectDragListener));
        sprint.setOnTouchListener(new TouchListener());
        productBacklog.setOnTouchListener(new TouchListener());
        increment.setOnTouchListener(new TouchListener());
        sprintBacklog.setOnTouchListener(new TouchListener());
        dailyMeetUp.setOnTouchListener(new TouchListener());

    }

    private void checkCorrectDrags(View view) {
        this.correctDrags++;
        this.showTooltip(view, v -> {
            if (this.hasCompletedGame()) {
                this.onGameCompletedListener.onGameCompleted("");
            }
        });
    }

    private boolean hasCompletedGame() {
        return this.correctDrags.equals(DRAGGABLE_VIEWS);
    }

    private void showTooltip(View view, ViewTooltip.ListenerHide onHideListener) {
        ViewTooltip.on(view)
                .position(ViewTooltip.Position.BOTTOM)
                .autoHide(true, 3000)
                .onHide(onHideListener)
                .clickToHide(true)
                .corner(30)
                .text(view.getContentDescription().toString())
                .show();
    }
}

