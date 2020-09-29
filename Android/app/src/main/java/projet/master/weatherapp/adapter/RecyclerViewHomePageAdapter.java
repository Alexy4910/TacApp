package projet.master.weatherapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import projet.master.weatherapp.R;
import projet.master.weatherapp.adapter.viewHolder.RecyclerViewHomePageViewHolder;
import projet.master.weatherapp.listener.GotoDetailViewHolder;
import projet.master.weatherapp.model.Ville;

public class RecyclerViewHomePageAdapter extends RecyclerView.Adapter<RecyclerViewHomePageViewHolder> {

    private Context context;
    private LayoutInflater layoutInflater;
    private ArrayList<Ville> villes;
    private GotoDetailViewHolder gotoDetailViewHolder;

    public RecyclerViewHomePageAdapter(Context context , GotoDetailViewHolder gotoDetailViewHolder) {
        this.context = context;
        this.layoutInflater = LayoutInflater.from(context);
        this.gotoDetailViewHolder = gotoDetailViewHolder;
    }

    @NonNull
    @Override
    public RecyclerViewHomePageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new RecyclerViewHomePageViewHolder(layoutInflater.inflate(R.layout.recycler_view_listitem, parent, false), context, gotoDetailViewHolder);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHomePageViewHolder holder, int position) {
        holder.bindTo(villes.get(position));
    }

    @Override
    public int getItemCount() {
        if (villes == null) {
            return 0;
        }
        return villes.size();
    }

    public void setVilles(ArrayList<Ville> villes) {
        this.villes = villes;
    }
}
