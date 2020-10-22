package projet.master.weatherapp.adapter.viewHolder;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import projet.master.weatherapp.R;
import projet.master.weatherapp.listener.GoToDetailViewHolderListener;
import projet.master.weatherapp.model.Ville;

public class RecyclerViewHomePageViewHolder extends RecyclerView.ViewHolder {


    @BindView(R.id.ville_name_recycler_view)
    public TextView name_ville;
    @BindView(R.id.temperature)
    public TextView temp;

    private Context context;
    private GoToDetailViewHolderListener goToDetailViewHolderListener;

    private Ville ville;

    public RecyclerViewHomePageViewHolder(View itemView, Context context, GoToDetailViewHolderListener goToDetailViewHolderListener) {
        super(itemView);
        this.context = context;
        this.goToDetailViewHolderListener = goToDetailViewHolderListener;
        ButterKnife.bind(this, itemView);
    }

    public void bindTo(Ville ville) {
        this.ville = ville;
        name_ville.setText(ville.getName());
        if (ville.getTimezone() != 0){
            temp.setText(ville.getTimezone() + "");
        }
    }

    @OnClick(R.id.recycler_view_li_container)
    public void onClickCell() {
        goToDetailViewHolderListener.onVilleClicked(ville);
    }
}
