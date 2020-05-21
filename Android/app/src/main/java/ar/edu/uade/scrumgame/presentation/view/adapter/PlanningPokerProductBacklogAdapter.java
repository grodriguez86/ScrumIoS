package ar.edu.uade.scrumgame.presentation.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;
import ar.edu.uade.scrumgame.R;
import ar.edu.uade.scrumgame.presentation.models.GameContentModel;
import butterknife.BindView;
import butterknife.ButterKnife;

import javax.inject.Inject;
import java.util.Collection;
import java.util.Collections;
import java.util.List;


public class PlanningPokerProductBacklogAdapter extends RecyclerView.Adapter<PlanningPokerProductBacklogAdapter.ProductBacklogViewHolder> {
    private List<GameContentModel> gameContentModels;
    private LayoutInflater layoutInflater;

    @Inject
    public PlanningPokerProductBacklogAdapter(Context context) {
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
    public ProductBacklogViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = this.layoutInflater.inflate(R.layout.fragment_game_planning_poker_intro_rv_item, parent, false);
        return new ProductBacklogViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductBacklogViewHolder holder, final int position) {
        GameContentModel gameContentModel = this.gameContentModels.get(position);
        holder.name.setText(gameContentModel.getData());
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void setGameContentModels(Collection<GameContentModel> gameContentModelCollection) {
        this.gameContentModels = (List<GameContentModel>) gameContentModelCollection;
        this.notifyDataSetChanged();
    }

    static class ProductBacklogViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.name_tv)
        AppCompatTextView name;

        ProductBacklogViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
