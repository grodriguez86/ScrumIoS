package ar.edu.uade.scrumgame.presentation.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import ar.edu.uade.scrumgame.R;
import ar.edu.uade.scrumgame.presentation.models.LevelModel;
import butterknife.BindView;
import butterknife.ButterKnife;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;


public class LevelsAdapter extends RecyclerView.Adapter<LevelsAdapter.LevelViewHolder> {

    public interface OnItemClickListener {
        void onLevelItemClicked(LevelModel levelModel);
    }

    private List<LevelModel> levelsCollection;
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
        View view = this.layoutInflater.inflate(R.layout.row_level, parent, false);
        return new LevelViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LevelViewHolder holder, final int position) {
        LevelModel levelModel = this.levelsCollection.get(position);
        holder.number.setText(String.valueOf(levelModel.getCode()));
        holder.name.setText(levelModel.getName());
        holder.sublevels.setText(String.format(subLevelsText, levelModel.getSublevels().size()));
        holder.action.setText(context.getString(R.string.play_now));
        holder.itemView.setOnClickListener(v -> {
            if (LevelsAdapter.this.onItemClickListener != null) {
                LevelsAdapter.this.onItemClickListener.onLevelItemClicked(levelModel);
            }
        });
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void setLevelsCollection(Collection<LevelModel> levelsCollection) {
        this.validateLevelsCollection(levelsCollection);
        this.levelsCollection = (List<LevelModel>) levelsCollection;
        this.notifyDataSetChanged();
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    private void validateLevelsCollection(Collection<LevelModel> levelsCollection) {
        if (levelsCollection == null) {
            throw new IllegalArgumentException("The list cannot be null");
        }
    }

    static class LevelViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.number)
        AppCompatTextView number;
        @BindView(R.id.name)
        AppCompatTextView name;
        @BindView(R.id.sublevels)
        AppCompatTextView sublevels;
        @BindView(R.id.action)
        AppCompatTextView action;

        LevelViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
