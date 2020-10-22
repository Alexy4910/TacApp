package projet.master.weatherapp.adapter.viewHolder;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import projet.master.weatherapp.R;
import projet.master.weatherapp.listener.FirstPageViewHolderListener;
import projet.master.weatherapp.listener.GoToDetailViewHolderListener;
import projet.master.weatherapp.model.Ville;

public class RecyclerViewFirstPageViewHolder extends RecyclerView.ViewHolder {


    @BindView(R.id.ville_name_recycler_view)
    public TextView name_ville;

    private Context context;
    private FirstPageViewHolderListener firstPageViewHolderListener;

    private Ville ville;

    public RecyclerViewFirstPageViewHolder(View itemView, Context context, FirstPageViewHolderListener firstPageViewHolderListener) {
        super(itemView);
        this.context = context;
        this.firstPageViewHolderListener = firstPageViewHolderListener;
        ButterKnife.bind(this, itemView);
    }

    public void bindTo(Ville ville) {
        this.ville = ville;
        name_ville.setText(ville.getName());
    }

    @OnClick(R.id.recycler_view_li_container)
    public void onClickCell() {
        firstPageViewHolderListener.onVilleClicked(ville);
    }
}
