package ar.edu.uade.scrumgame.presentation.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;
import ar.edu.uade.scrumgame.R;
import ar.edu.uade.scrumgame.presentation.models.ProductBacklogItemModel;
import butterknife.BindView;
import butterknife.ButterKnife;

import javax.inject.Inject;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class PlanningPokerFinalProductBacklogAdapter extends RecyclerView.Adapter<PlanningPokerFinalProductBacklogAdapter.ProductBacklogViewHolder> {
    private List<ProductBacklogItemModel> productBacklogItemModels;
    private LayoutInflater layoutInflater;
    private Context context;

    @Inject
    public PlanningPokerFinalProductBacklogAdapter(Context context) {
        this.layoutInflater =
                (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.productBacklogItemModels = Collections.emptyList();
        this.context = context;
    }

    @Override
    public int getItemCount() {
        return (this.productBacklogItemModels != null) ? this.productBacklogItemModels.size() : 0;
    }

    @NonNull
    @Override
    public ProductBacklogViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = this.layoutInflater.inflate(R.layout.fragment_game_planning_poker_sprint_backlog_rv_item, parent, false);
        return new ProductBacklogViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductBacklogViewHolder holder, final int position) {
        ProductBacklogItemModel productBacklogItemModel = this.productBacklogItemModels.get(position);
        holder.name.setText(productBacklogItemModel.getName());
        holder.points.setText(String.format(this.context.getString(R.string.story_points), productBacklogItemModel.getEstimatedStoryPoints()));
        holder.isPriority.setVisibility(productBacklogItemModel.getPriority() ? View.VISIBLE : View.INVISIBLE);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void setGameContentModels(Collection<ProductBacklogItemModel> productBacklogItemModelCollection) {
        this.productBacklogItemModels = (List<ProductBacklogItemModel>) productBacklogItemModelCollection;
        this.notifyDataSetChanged();
    }

    static class ProductBacklogViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.item_tv)
        AppCompatTextView name;
        @BindView(R.id.item_points_tv)
        AppCompatTextView points;
        @BindView(R.id.priority_ll)
        LinearLayout isPriority;

        ProductBacklogViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
