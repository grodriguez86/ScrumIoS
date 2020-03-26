package ar.edu.uade.scrumgame.presentation.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import ar.edu.uade.scrumgame.R;
import ar.edu.uade.scrumgame.presentation.models.GameContentModel;
import butterknife.BindView;
import butterknife.ButterKnife;


public class ImageQuizAdapter extends RecyclerView.Adapter<ImageQuizAdapter.ImageQuizViewHolder> {

    public interface OnItemClickListener {
        void onImageSelected(GameContentModel gameContentModel);
    }

    private List<GameContentModel> gameContentModels;
    private LayoutInflater layoutInflater;
    private OnItemClickListener onItemClickListener;
    private ImageQuizViewHolder lastSelectedPicture;

    @Inject
    public ImageQuizAdapter(Context context) {
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
    public ImageQuizViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = this.layoutInflater.inflate(R.layout.fragment_game_image_quiz_rv_item, parent, false);
        return new ImageQuizViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageQuizViewHolder holder, final int position) {
        GameContentModel gameContentModel = this.gameContentModels.get(position);
        Picasso.get().load(gameContentModel.getData()).into(holder.image);
        holder.selected.setTag(gameContentModel.getCorrect());
        holder.selected.setOnClickListener(v -> {
            holder.container.setSelected(true);
            if (lastSelectedPicture != null) {
                lastSelectedPicture.selected.setChecked(false);
                lastSelectedPicture.container.setSelected(false);
            }
            lastSelectedPicture = holder;
            if (ImageQuizAdapter.this.onItemClickListener != null) {
                ImageQuizAdapter.this.onItemClickListener.onImageSelected(gameContentModel);
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
    }

    static class ImageQuizViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.picture_iv)
        ImageView image;
        @BindView(R.id.selected_rb)
        RadioButton selected;
        @BindView(R.id.container_ll)
        LinearLayout container;

        ImageQuizViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
