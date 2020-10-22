package projet.master.weatherapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import projet.master.weatherapp.R;
import projet.master.weatherapp.adapter.viewHolder.RecyclerViewFirstPageViewHolder;
import projet.master.weatherapp.adapter.viewHolder.RecyclerViewHomePageViewHolder;
import projet.master.weatherapp.listener.FirstPageViewHolderListener;
import projet.master.weatherapp.listener.GoToDetailViewHolderListener;
import projet.master.weatherapp.model.Ville;

public class RecyclerViewFirstLunchAdapter extends RecyclerView.Adapter<RecyclerViewFirstPageViewHolder> {

    private Context context;
    private LayoutInflater layoutInflater;
    private ArrayList<Ville> villes;
    private FirstPageViewHolderListener firstPageViewHolderListener;

    public RecyclerViewFirstLunchAdapter(Context context , FirstPageViewHolderListener firstPageViewHolderListener) {
        this.context = context;
        this.layoutInflater = LayoutInflater.from(context);
        this.firstPageViewHolderListener = firstPageViewHolderListener;
    }

    @NonNull
    @Override
    public RecyclerViewFirstPageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new RecyclerViewFirstPageViewHolder(layoutInflater.inflate(R.layout.recycler_view_listitem, parent, false), context, firstPageViewHolderListener);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewFirstPageViewHolder holder, int position) {
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
