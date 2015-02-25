package edu.gatech.bobsbuilders.socialsaver;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.parse.ParseGeoPoint;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class DealsAdapter extends BaseAdapter {
    Context context;
    LayoutInflater inflater;
    ImageLoader imageLoader;
    private List<DealListings> DealListingslist = null;
    private ArrayList<DealListings> arraylist;
    String userid,objectid;
    String objectID, email, item, found, foundLocation;
    Date saleEndDate;
    Number maxPrice;
    ParseGeoPoint point;

    public DealsAdapter() {

    }

    /**
     * FriendsAdapter constructor that is overloaded
     *
     * @param context the original class its coming from
     * @param DealListingsList is the list of listings
     * @return A new instance of fragment HomeScreen.
     */
    public DealsAdapter(Context context,List<DealListings> DealListingslist) { //this class wanted a void? weird
        this.context = context;
        this.DealListingslist = DealListingslist;
        inflater = LayoutInflater.from(context);
        this.arraylist = new ArrayList<DealListings>();
        this.arraylist.addAll(DealListingslist);
        imageLoader = new ImageLoader(context);
    }

    /**
     * view holder to instantiate new textviews
     *
     */
    public class ViewHolder {
        TextView itemTV;
        TextView maxPriceTV;
        TextView foundTV;
        TextView foundLocationTV;
    }

    @Override
    public int getCount() {
        return DealListingslist.size();
    }

    @Override
    public Object getItem(int position) {
        return DealListingslist.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    /**
     * getView
     *
     * @param position the original class its coming from
     * @param view is the list of listings
     * @param parent is the list of listings
     * @return A new view of fragment HomeScreen.
     */
    public View getView(final int position, View view, ViewGroup parent) {

        final ViewHolder holder;
        if (view == null) {
            holder = new ViewHolder();
            view = inflater.inflate(R.layout.activity_deals_adapter, null);
            // Locate the TextViews in listview_item.xml
            holder.itemTV = (TextView) view.findViewById(R.id.itemWANTEDTVSET);
            holder.maxPriceTV = (TextView) view.findViewById(R.id.priceWANTEDTVSET);
            holder.foundTV = (TextView) view.findViewById(R.id.foundTVSET);
            holder.foundLocationTV=(TextView)view.findViewById(R.id.foundLocationTVSET);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        // Set the results into TextViews
        holder.itemTV.setText(DealListingslist.get(position).getItem());
        holder.foundLocationTV.setText(DealListingslist.get(position).getFoundLocation());

        holder.maxPriceTV.setText(DealListingslist.get(position).getMaxPrice().toString()); /*ISSUE????*/
        holder.foundTV.setText(DealListingslist.get(position).getFound());

        // Listen for ListView Item Click

        return view;
    }

}