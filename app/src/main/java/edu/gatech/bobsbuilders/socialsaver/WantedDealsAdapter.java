package edu.gatech.bobsbuilders.socialsaver;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.parse.ParseGeoPoint;

import java.util.Date;
import java.util.List;


public class WantedDealsAdapter extends BaseAdapter {
    private LayoutInflater inflater;
    private List<DealListings> DealListingslist = null;
    String userid,objectid;
    String objectID, email, item, found, foundLocation;
    Date saleEndDate;
    Number maxPrice;
    ParseGeoPoint point;

    public WantedDealsAdapter() {

    }

    /**
     * FriendsAdapter constructor that is overloaded
     *
     * @param context the original class its coming from
     * @param DealListingslist is the list of listings
     */
    public WantedDealsAdapter(Context context,List<DealListings> DealListingslist) { //this class wanted a void? weird
        //Context context1 = context;
        this.DealListingslist = DealListingslist;
        inflater = LayoutInflater.from(context);
        //ArrayList<DealListings> arraylist = new ArrayList<>();
        //arraylist.addAll(DealListingslist);
        //ImageLoader imageLoader = new ImageLoader(context);
    }

    /**
     * view holder to instantiate new textviews
     *
     */
    public class ViewHolder {
        TextView itemTV;
        TextView maxPriceTV;
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
    @SuppressLint("InflateParams")
    public View getView(final int position, View view, ViewGroup parent) {

        final ViewHolder holder;
        if (view == null) {
            holder = new ViewHolder();
            view = inflater.inflate(R.layout.activity_wanted_deals_adapter, null);
            // Locate the TextViews in listview_item.xml
            holder.itemTV = (TextView) view.findViewById(R.id.itemWANTEDTVSET);
            holder.maxPriceTV = (TextView) view.findViewById(R.id.priceWANTEDTVSET);

            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        // Set the results into TextViews
        holder.itemTV.setText(DealListingslist.get(position).getItem());
        holder.maxPriceTV.setText(DealListingslist.get(position).getMaxPrice().toString()); /*ISSUE????*/

        // Listen for ListView Item Click

        return view;
    }

}