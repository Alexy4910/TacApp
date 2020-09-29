package projet.master.weatherapp.adapter.viewHolder;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import projet.master.weatherapp.R;
import projet.master.weatherapp.listener.GotoDetailViewHolder;
import projet.master.weatherapp.model.Ville;

public class RecyclerViewHomePageViewHolder extends RecyclerView.ViewHolder {


    @BindView(R.id.ville_name_recycler_view)
    public TextView name_ville;

    private Context context;
    private GotoDetailViewHolder gotoDetailViewHolder;

    private Ville ville;

    public RecyclerViewHomePageViewHolder(View itemView, Context context, GotoDetailViewHolder gotoDetailViewHolder) {
        super(itemView);
        this.context = context;
        this.gotoDetailViewHolder = gotoDetailViewHolder;
        ButterKnife.bind(this, itemView);
    }

    public void bindTo(Ville ville) {
        this.ville = ville;
        name_ville.setText(ville.getName());
    }

    @OnClick(R.id.recycler_view_li_container)
    public void onClickCell() {
        gotoDetailViewHolder.onVilleClicked(ville);
    }
}
