package ar.edu.uade.scrumgame.presentation.view.adapter;

import android.content.ClipData;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import javax.inject.Inject;

import ar.edu.uade.scrumgame.R;
import ar.edu.uade.scrumgame.presentation.models.GameContentModel;
import ar.edu.uade.scrumgame.presentation.view.utils.DragToListListener;
import butterknife.BindView;
import butterknife.ButterKnife;


public class DragDropAdapter extends RecyclerView.Adapter<DragDropAdapter.DragDropViewHolder> {
    private List<GameContentModel> gameContentModels;
    private LayoutInflater layoutInflater;

    @Inject
    public DragDropAdapter(Context context) {
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
        View view = this.layoutInflater.inflate(R.layout.fragment_game_drag_drop_rv_item, parent, false);
        return new DragDropViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DragDropViewHolder holder, final int position) {
        GameContentModel gameContentModel = this.gameContentModels.get(position);
        holder.optionLinearLayout.setTag(position);
        holder.optionLinearLayout.setOnLongClickListener(view -> {
            ClipData data = ClipData.newPlainText("", "");
            View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(view);
            view.startDrag(data, shadowBuilder, view, 0);
            view.setVisibility(View.INVISIBLE);
            return true;
        });
        holder.optionLinearLayout.setOnDragListener(new DragToListListener());
        holder.optionTextView.setText(gameContentModel.getData());
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    public void removeItem(int position) {
        this.gameContentModels.remove(position);
        this.notifyDataSetChanged();
    }


    public void setGameContentModels(Collection<GameContentModel> gameContentModelCollection) {
        this.validateGameModelCollection(gameContentModelCollection);
        this.gameContentModels = (List<GameContentModel>) gameContentModelCollection;
        this.notifyDataSetChanged();
    }

    public GameContentModel getGameContentModelAt(int position) {
        return this.gameContentModels.get(position);
    }

    public void addItem(GameContentModel gameContentModel) {
        this.gameContentModels.add(gameContentModel);
        this.notifyDataSetChanged();
    }

    public View.OnDragListener getDragListenerInstance() {
        return new DragToListListener();
    }

    public List<GameContentModel> getItems() {
        return this.gameContentModels;
    }

    private void validateGameModelCollection(Collection<GameContentModel> gameContentModelCollection) {
        if (gameContentModelCollection == null) {
            throw new IllegalArgumentException("The list cannot be null");
        }
    }

    static class DragDropViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.option_ll)
        LinearLayout optionLinearLayout;
        @BindView(R.id.option_tv)
        TextView optionTextView;

        DragDropViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
