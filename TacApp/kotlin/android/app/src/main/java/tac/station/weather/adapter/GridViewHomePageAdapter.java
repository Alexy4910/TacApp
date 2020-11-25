package tac.station.weather.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import tac.station.weather.R;
import tac.station.weather.model.Ville;

public class GridViewHomePageAdapter extends BaseAdapter {

    private Context context;
    private List<Ville> villes;
    private RecyclerViewAdapter.RecyclerViewAdapterListener recyclerViewAdapterListener;

    public GridViewHomePageAdapter(Context context, List<Ville> villes, RecyclerViewAdapter.RecyclerViewAdapterListener recyclerViewAdapterListener){
        this.context = context;
        this.villes = villes;
        this.recyclerViewAdapterListener = recyclerViewAdapterListener;
    }

    @Override
    public int getCount() {
        if (villes == null) return 0;
        return villes.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null){
            final LayoutInflater layoutInflater = LayoutInflater.from(context);
            view = layoutInflater.inflate(R.layout.grid_listitem, null);
        }

        // 3
        final ImageView imageView = (ImageView)view.findViewById(R.id.grid_image);
        final TextView nameTextView = (TextView)view.findViewById(R.id.name_ville);
        final TextView tempTextView = (TextView)view.findViewById(R.id.temperature);
        final TextView descView = (TextView)view.findViewById(R.id.desc);

        Bitmap bitmap = BitmapFactory.decodeByteArray(villes.get(i).getIcon(), 0, villes.get(i).getIcon().length);
        imageView.setImageBitmap(bitmap);
        nameTextView.setText(villes.get(i).getName());
        tempTextView.setText(villes.get(i).getTemperature());
        descView.setText(villes.get(i).getDescription());

        view.setOnClickListener(e -> {
            this.recyclerViewAdapterListener.onGoDetailVilleGrid(villes.get(i));
        });
        
        return view;
    }

    public void setVilles(List<Ville> villes) {
        this.villes = villes;
    }
}
