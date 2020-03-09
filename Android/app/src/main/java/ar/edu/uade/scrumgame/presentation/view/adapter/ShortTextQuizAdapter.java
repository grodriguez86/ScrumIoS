package ar.edu.uade.scrumgame.presentation.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RadioButton;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
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
    private final static Integer[] BACKGROUND_IDS = new Integer[]{R.drawable.gradient_background_1, R.drawable.gradient_background_2, R.drawable.gradient_background_3, R.drawable.gradient_background_4};
    private final static Integer MAXIMUM_NUMBER_OF_OPTIONS = 4;

    public interface OnItemClickListener {
        void onOptionSelected(GameContentModel gameContentModel);
    }

    private Context context;
    private List<GameContentModel> gameContentModels;
    private LayoutInflater layoutInflater;
    private OnItemClickListener onItemClickListener;
    private ShortTextQuizViewHolder lastSelectedOption;

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
        holder.container.setBackground(ContextCompat.getDrawable(this.context, BACKGROUND_IDS[position]));
        holder.option.setText(gameContentModel.getData());
        holder.selected.setTag(gameContentModel.getCorrect());
        holder.selected.setOnClickListener(v -> {
            if (lastSelectedOption != null) {
                lastSelectedOption.selected.setChecked(false);
            }
            lastSelectedOption = holder;
            if (ShortTextQuizAdapter.this.onItemClickListener != null) {
                ShortTextQuizAdapter.this.onItemClickListener.onOptionSelected(gameContentModel);
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

    static class ShortTextQuizViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.container_ll)
        LinearLayout container;
        @BindView(R.id.option_tv)
        AppCompatTextView option;
        @BindView(R.id.selected_rb)
        RadioButton selected;

        ShortTextQuizViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
