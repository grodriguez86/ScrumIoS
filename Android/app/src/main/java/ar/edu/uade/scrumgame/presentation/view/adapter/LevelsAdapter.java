package ar.edu.uade.scrumgame.presentation.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import ar.edu.uade.scrumgame.R;
import ar.edu.uade.scrumgame.presentation.models.LevelModel;
import ar.edu.uade.scrumgame.presentation.models.ProgressModel;
import ar.edu.uade.scrumgame.presentation.models.UserOverallDataModel;
import butterknife.BindView;
import butterknife.ButterKnife;


public class LevelsAdapter extends RecyclerView.Adapter<LevelsAdapter.LevelViewHolder> {

    public interface OnItemClickListener {
        void onLevelItemClicked(LevelModel levelModel);
    }

    private List<LevelModel> levelsCollection;
    private List<ProgressModel> progressModelCollection;
    private UserOverallDataModel userOverallDataModel;
    private LayoutInflater layoutInflater;
    private OnItemClickListener onItemClickListener;
    private String subLevelsText;
    private Context context;

    @Inject
    LevelsAdapter(Context context) {
        this.context = context;
        this.layoutInflater =
                (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.subLevelsText = context.getString(R.string.menu_level_row_sublevels);
        this.levelsCollection = Collections.emptyList();
    }

    @Override
    public int getItemCount() {
        return (this.levelsCollection != null) ? this.levelsCollection.size() : 0;
    }

    @NonNull
    @Override
    public LevelViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = this.layoutInflater.inflate(R.layout.row_level_unlocked, parent, false);
        return new LevelViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LevelViewHolder holder, final int position) {
        LevelModel levelModel = this.levelsCollection.get(position);
        holder.itemView.setOnClickListener(v -> {
        });
        ProgressModel progressModel = this.progressModelCollection.size() >= position + 1 ?
                this.progressModelCollection.get(position) :
                null;
        if (progressModel == null || progressModel.isBlocked()) { // Level blocked
            holder.playLabel.setText(R.string.menu_play_unavailable);
            holder.itemView.setAlpha(0.67f);
            holder.itemView.setBackgroundResource(R.drawable.card_level_locked);
            holder.progressLabel.setVisibility(View.INVISIBLE);
            holder.statusImage.setBackgroundResource(R.mipmap.level_status_locked_foreground);
            holder.statusImage.setVisibility(View.VISIBLE);
        } else {
            holder.itemView.setBackgroundResource(R.drawable.card_level);
            holder.progressLabel.setVisibility(View.VISIBLE);
            holder.itemView.setAlpha(1f);
            int progressPercentage = levelModel.calculateProgressPercentage(progressModel);

            if (progressPercentage < 100) { // Level incomplete
                holder.statusImage.setVisibility(View.INVISIBLE);
                holder.progressLabel.setText(String.format("%d%%", progressPercentage));
                holder.playLabel.setText(R.string.menu_play_available);
                holder.itemView.setOnClickListener(v -> {
                    if (LevelsAdapter.this.onItemClickListener != null) {
                        LevelsAdapter.this.onItemClickListener.onLevelItemClicked(levelModel);
                    }
                });
            } else { // Level completed
                holder.statusImage.setBackgroundResource(R.mipmap.level_status_completed_foreground);
                holder.statusImage.setVisibility(View.VISIBLE);
                holder.playLabel.setText(R.string.menu_play_completed);
                holder.progressLabel.setText("100%");
            }
        }
        holder.number.setText(String.valueOf(levelModel.getCode()));
        holder.name.setText(levelModel.getName());
        holder.sublevels.setText(String.format(subLevelsText, levelModel.getSublevels().size()));
//        holder.action.setText(context.getString(R.string.menu_play_available));
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void setLevelsCollection(Collection<LevelModel> levelsCollection,
                                    Collection<ProgressModel> progressModelCollection,
                                    UserOverallDataModel userOverallDataModel) {
        this.validateLevelsCollection(levelsCollection);
        this.levelsCollection = (List<LevelModel>) levelsCollection;
        this.validateProgressCollection(progressModelCollection);
        this.progressModelCollection = (List<ProgressModel>) progressModelCollection;
        this.validateUserOverallDataModel(userOverallDataModel);
        this.userOverallDataModel = userOverallDataModel;
        this.notifyDataSetChanged();
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    private void validateLevelsCollection(Collection<LevelModel> levelsCollection) {
        if (levelsCollection == null)
            throw new IllegalArgumentException("The level list cannot be null");
    }

    private void validateProgressCollection(Collection<ProgressModel> progressModelCollection) {
        if (progressModelCollection == null)
            throw new IllegalArgumentException("The progress list cannot be null");
    }

    private void validateUserOverallDataModel(UserOverallDataModel userOverallDataModel) {
        if (userOverallDataModel == null || userOverallDataModel.getCurrentAvailableLevel() < 1)
            throw new IllegalArgumentException("Invalid userOverallDataModel");
    }

    static class LevelViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.number)
        TextView number;
        //        AppCompatTextView number;
        @BindView(R.id.name)
        TextView name;
        //        AppCompatTextView name;
        @BindView(R.id.sublevels)
        TextView sublevels;
        //<<<<<<
//        <HEAD
//        TextView sublevels;
        @BindView(R.id.playLabel)
        TextView playLabel;
        @BindView(R.id.progressLabel)
        TextView progressLabel;
        @BindView(R.id.statusImage)
        ImageView statusImage;
//=======
//        @BindView(R.id.sublevels)
//        AppCompatTextView sublevels;
//        @BindView(R.id.action)
//        AppCompatTextView action;
//>>>>>>>
//
//        new_levels_android

        LevelViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
