package edu.gatech.bobsbuilders.socialsaver;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.parse.ParseGeoPoint;

import java.util.Date;
import java.util.List;


class DealsAdapter extends BaseAdapter {
    private Context context;
    private LayoutInflater inflater;
    private ImageLoader imageLoader;
    private List<DealListings> DealListingslist = null;
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
     * @param DealListingslist is the list of listings
     */
    public DealsAdapter(Context context,List<DealListings> DealListingslist) { //this class wanted a void? weird
        this.context = context;
        this.DealListingslist = DealListingslist;
        inflater = LayoutInflater.from(context);
        //ArrayList<DealListings> arraylist = new ArrayList<>();
        //arraylist.addAll(DealListingslist);
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
    @SuppressLint("InflateParams")
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
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                //DEFIANTLY GET OBJECTID SO WE COULD REMOVE THAT FRIENDSHIP ON DELETE BUTTON!

                // Send single item click data to SingleTutorView Class
                Intent intent = new Intent(context, MapView.class);
                // Pass all data name
                ParseGeoPoint point = DealListingslist.get(position).getGeoPoint();
                String foundAt =  DealListingslist.get(position).getFound();
                String foundLocation = DealListingslist.get(position).getFoundLocation();

                GetGeoPoint setPoint = new GetGeoPoint();
                setPoint.SetLat(point.getLatitude());
                setPoint.SetLong(point.getLongitude());
                intent.putExtra("LAT", point.getLatitude());
                intent.putExtra("LONG", point.getLongitude());
                intent.putExtra("FOUND",foundAt);
                intent.putExtra("FOUNDLOCATION",foundLocation);

                context.startActivity(intent);

            }
        });

        return view;
    }

}