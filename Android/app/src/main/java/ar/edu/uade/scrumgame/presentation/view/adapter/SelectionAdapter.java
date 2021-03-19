package ar.edu.uade.scrumgame.presentation.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import javax.inject.Inject;

import ar.edu.uade.scrumgame.R;
import ar.edu.uade.scrumgame.presentation.models.GameContentModel;
import butterknife.BindView;
import butterknife.ButterKnife;

public class SelectionAdapter extends RecyclerView.Adapter<SelectionAdapter.DragDropViewHolder> {
    private List<GameContentModel> gameContentModels;
    private LayoutInflater layoutInflater;
    private Context context;
    private List<GameContentModel> selectedOptions = new LinkedList<>();
    private Boolean enabled = true;

    @Inject
    public SelectionAdapter(Context context) {
        this.context = context;
        this.layoutInflater =
                (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.gameContentModels = new LinkedList<>();
    }

    @Override
    public int getItemCount() {
        return (this.gameContentModels != null) ? this.gameContentModels.size() : 0;
    }

    @NonNull
    @Override
    public DragDropViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = this.layoutInflater.inflate(R.layout.fragment_game_selection_rv_item, parent, false);
        return new DragDropViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DragDropViewHolder holder, final int position) {
        GameContentModel gameContentModel = this.gameContentModels.get(position);
        holder.optionLinearLayout.setTag(gameContentModel.getCorrect());
        holder.optionLinearLayout.setOnClickListener(v -> {
            if (this.enabled) {
                if (this.selectedOptions.contains(gameContentModel)) {
                    this.selectedOptions.remove(gameContentModel);
                    holder.optionLinearLayout.setBackground(ContextCompat.getDrawable(this.context, R.drawable.button_outline));
                    holder.optionAppCompatTextView.setTextColor(ContextCompat.getColor(this.context, R.color.blue));
                } else {
                    this.selectedOptions.add(gameContentModel);
                    holder.optionLinearLayout.setBackground(ContextCompat.getDrawable(this.context, R.drawable.button_fill));
                    holder.optionAppCompatTextView.setTextColor(ContextCompat.getColor(this.context, R.color.white));
                }
            }
        });
        holder.optionAppCompatTextView.setText(gameContentModel.getData());

    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public List<GameContentModel> getSelectedOptions() {
        return this.selectedOptions;
    }

    public void setGameContentModels(Collection<GameContentModel> gameContentModelCollection) {
        this.validateGameModelCollection(gameContentModelCollection);
        this.gameContentModels = (List<GameContentModel>) gameContentModelCollection;
        this.notifyDataSetChanged();
    }

    private void validateGameModelCollection(Collection<GameContentModel> gameContentModelCollection) {
        if (gameContentModelCollection == null) {
            throw new IllegalArgumentException("The list cannot be null");
        }
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    static class DragDropViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.option_ll)
        LinearLayout optionLinearLayout;
        @BindView(R.id.option_tv)
        AppCompatTextView optionAppCompatTextView;

        DragDropViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
