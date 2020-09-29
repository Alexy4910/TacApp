package projet.master.weatherapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import projet.master.weatherapp.R;
import projet.master.weatherapp.model.Ville;

public class GridViewHomePageAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<Ville> villes;

    public GridViewHomePageAdapter(Context context, ArrayList<Ville> villes){
        this.context = context;
        this.villes = villes;
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
        //final ImageView imageView = (ImageView)convertView.findViewById(R.id.imageview_cover_art);
        final TextView nameTextView = (TextView)view.findViewById(R.id.name_ville);
        //final TextView authorTextView = (TextView)convertView.findViewById(R.id.textview_book_author);
        //final ImageView imageViewFavorite = (ImageView)convertView.findViewById(R.id.imageview_favorite);

        // 4
        //imageView.setImageResource(book.getImageResource());
        nameTextView.setText(villes.get(i).getName());
        //authorTextView.setText(mContext.getString(book.getAuthor()));

        return view;
    }
}
