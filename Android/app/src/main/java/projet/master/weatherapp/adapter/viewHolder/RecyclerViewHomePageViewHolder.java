package projet.master.weatherapp.adapter.viewHolder;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import projet.master.weatherapp.R;
import projet.master.weatherapp.model.Ville;

public class RecyclerViewHomePageViewHolder extends RecyclerView.ViewHolder {


    @BindView(R.id.ville_name_recycler_view)
    public TextView name_ville;

    private Context context;

    public RecyclerViewHomePageViewHolder(View itemView, Context context/*, InventaireListener inventaireListener*/) {
        super(itemView);
        this.context = context;
        //this.inventaireListener = inventaireListener;
        ButterKnife.bind(this, itemView);
    }

    public void bindTo(Ville ville) {
        name_ville.setText(ville.getName());
    }

//    @OnClick(R.id.inventaire_li_container)
//    public void onClickCell() {
//        inventaireListener.onClickCellule(inventaireResponse);
//    }
}
