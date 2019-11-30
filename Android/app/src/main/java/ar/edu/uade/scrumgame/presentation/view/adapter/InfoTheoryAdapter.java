package ar.edu.uade.scrumgame.presentation.view.adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import ar.edu.uade.scrumgame.R;
import ar.edu.uade.scrumgame.presentation.models.InfoTheoryModel;
import ar.edu.uade.scrumgame.presentation.models.ViewTypeModel;
import butterknife.BindView;
import butterknife.ButterKnife;


public class InfoTheoryAdapter extends RecyclerView.Adapter<InfoTheoryAdapter.InfoTheoryViewHolder> {

    private List<InfoTheoryModel> infoTheoryCollection;
    private LayoutInflater layoutInflater;

    Context context;

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        context = recyclerView.getContext();
    }

    @Inject
    InfoTheoryAdapter(Context context) {
        this.layoutInflater =
                (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.infoTheoryCollection = Collections.emptyList();
    }

    @Override
    public int getItemCount() {
        return (this.infoTheoryCollection != null) ? this.infoTheoryCollection.size() : 0;
    }

    @NonNull
    @Override
    public InfoTheoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = this.layoutInflater.inflate(R.layout.fragment_info_theory_rv_item, parent, false);
        return new InfoTheoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull InfoTheoryViewHolder holder, final int position) {
        InfoTheoryModel infoTheoryModel = this.infoTheoryCollection.get(position);
        for (ViewTypeModel infoTheoryItem : infoTheoryModel.getInfoTheory()) {
            switch (infoTheoryItem.getType()) {
                case "title":
                    TextView title = new TextView(context);
                    title.setText(infoTheoryItem.getData());
                    title.setTextSize(20);
                    title.setPadding(16,16,16,24);
                    title.setTypeface(title.getTypeface(), Typeface.BOLD);
                    holder.container.addView(title);
                    break;
                case "image":
                    ImageView imageView = new ImageView(context);
                    imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                    imageView.setAdjustViewBounds(true);
                    imageView.setPadding(24,4,16,4);
                    holder.container.addView(imageView);
                    Picasso.get().setIndicatorsEnabled(true);
                    Picasso.get().load(infoTheoryItem.getData()).into(imageView);
                    break;
                case "video":
                    // TODO implementar item de youtube
                    break;
                case "text":
                    TextView text = new TextView(context);
                    text.setText(infoTheoryItem.getData());
                    text.setTextSize(14);
                    text.setPadding(16,4,16,4);
                    holder.container.addView(text);
                    break;
                default:
                    throw new RuntimeException("Invalid info theory content type.");
            }
        }
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void setInfoTheoryCollection(Collection<InfoTheoryModel> infoTheoryCollection) {
        this.validateInfoTheoryCollection(infoTheoryCollection);
        this.infoTheoryCollection = (List<InfoTheoryModel>) infoTheoryCollection;
        this.notifyDataSetChanged();
    }

    private void validateInfoTheoryCollection(Collection<InfoTheoryModel> infoTheoryModelCollection) {
        if (infoTheoryModelCollection == null) {
            throw new IllegalArgumentException("The Info Theory list cannot be null");
        }
    }

    static class InfoTheoryViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.container)
        LinearLayout container;

        InfoTheoryViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
