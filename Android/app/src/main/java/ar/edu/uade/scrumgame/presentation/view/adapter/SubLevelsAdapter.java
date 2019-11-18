package ar.edu.uade.scrumgame.presentation.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import ar.edu.uade.scrumgame.R;
import ar.edu.uade.scrumgame.presentation.models.SubLevelModel;
import butterknife.BindView;
import butterknife.ButterKnife;


public class SubLevelsAdapter extends RecyclerView.Adapter<SubLevelsAdapter.SubLevelViewHolder> {

    public interface OnItemClickListener {
        void onSubLevelItemClicked(SubLevelModel subLevelModel);
    }

    private List<SubLevelModel> subLevelsCollection;
    private LayoutInflater layoutInflater;
    private OnItemClickListener onItemClickListener;

    @Inject
    SubLevelsAdapter(Context context) {
        this.layoutInflater =
                (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.subLevelsCollection = Collections.emptyList();
    }

    @Override
    public int getItemCount() {
        return (this.subLevelsCollection != null) ? this.subLevelsCollection.size() : 0;
    }

    @NonNull
    @Override
    public SubLevelViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = this.layoutInflater.inflate(R.layout.fragment_sublevel_card, parent, false);
        return new SubLevelViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SubLevelViewHolder holder, final int position) {
        SubLevelModel subLevelModel = this.subLevelsCollection.get(position);
        holder.text.setText(subLevelModel.getName());
        holder.itemView.setOnClickListener(v -> {
            if (SubLevelsAdapter.this.onItemClickListener != null) {
                SubLevelsAdapter.this.onItemClickListener.onSubLevelItemClicked(subLevelModel);
            }
        });
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void setSubLevelsCollection(Collection<SubLevelModel> subLevelsCollection) {
        this.validateSubLevelsCollection(subLevelsCollection);
        this.subLevelsCollection = (List<SubLevelModel>) subLevelsCollection;
        this.notifyDataSetChanged();
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    private void validateSubLevelsCollection(Collection<SubLevelModel> subLevelsCollection) {
        if (subLevelsCollection == null) {
            throw new IllegalArgumentException("The sublevel list cannot be null");
        }
    }

    static class SubLevelViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.subLevelText)
        TextView text;
        // TODO: progress indicator

        SubLevelViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
