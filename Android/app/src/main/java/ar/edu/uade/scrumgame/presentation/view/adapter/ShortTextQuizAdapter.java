package ar.edu.uade.scrumgame.presentation.view.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import ar.edu.uade.scrumgame.R;
import ar.edu.uade.scrumgame.presentation.models.GameContentModel;
import butterknife.BindView;
import butterknife.ButterKnife;


public class ShortTextQuizAdapter extends RecyclerView.Adapter<ShortTextQuizAdapter.ShortTextQuizViewHolder> {
    private final static Integer MAXIMUM_NUMBER_OF_OPTIONS = 4;

    public interface OnItemClickListener {
        void onOptionSelected(GameContentModel gameContentModel);
    }

    private Context context;
    private List<GameContentModel> gameContentModels;
    private LayoutInflater layoutInflater;
    private OnItemClickListener onItemClickListener;
    private ShortTextQuizViewHolder lastSelectedOption;
    private Boolean enabled = true;

    @Inject
    public ShortTextQuizAdapter(Context context) {
        this.context = context;
        this.layoutInflater =
                (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.gameContentModels = Collections.emptyList();
    }

    @Override
    public int getItemCount() {
        return (this.gameContentModels != null) ? this.gameContentModels.size() : 0;
    }

    @NonNull
    @Override
    public ShortTextQuizViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = this.layoutInflater.inflate(R.layout.fragment_game_short_text_quiz_rv_item, parent, false);
        return new ShortTextQuizViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ShortTextQuizViewHolder holder, final int position) {
        GameContentModel gameContentModel = this.gameContentModels.get(position);
        holder.selected.setText(gameContentModel.getData());
        holder.selected.setTag(gameContentModel.getCorrect());
        holder.selected.setOnClickListener(v -> {
            if (this.enabled) {
                if (lastSelectedOption != null) {
                    lastSelectedOption.selected.setTextColor(ContextCompat.getColor(this.context, R.color.violet));
                    lastSelectedOption.selected.setChecked(false);
                }
                lastSelectedOption = holder;
                lastSelectedOption.selected.setTextColor(Color.WHITE);
                if (ShortTextQuizAdapter.this.onItemClickListener != null) {
                    ShortTextQuizAdapter.this.onItemClickListener.onOptionSelected(gameContentModel);
                }
            }
        });
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void setGameContentModels(Collection<GameContentModel> gameContentModelCollection) {
        this.validateGameModelCollection(gameContentModelCollection);
        this.gameContentModels = (List<GameContentModel>) gameContentModelCollection;
        this.notifyDataSetChanged();
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    private void validateGameModelCollection(Collection<GameContentModel> gameContentModelCollection) {
        if (gameContentModelCollection == null) {
            throw new IllegalArgumentException("The list cannot be null");
        }
        if (gameContentModelCollection.size() > MAXIMUM_NUMBER_OF_OPTIONS) {
            throw new IllegalArgumentException("The list has too many elements");
        }
    }


    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    static class ShortTextQuizViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.selected_rb)
        RadioButton selected;

        ShortTextQuizViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
