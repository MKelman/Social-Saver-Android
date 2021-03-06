package edu.gatech.bobsbuilders.socialsaver;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;


class FriendsAdapter extends BaseAdapter {
    private Context context;
    private LayoutInflater inflater;
    float num;
    private ImageLoader imageLoader;
    private List<UserListings> UserListingslist = null;
    private String userid;
    String usermessage;
    String totalrates;
    String currentrate;
    private String objectid;
    String reportcount, rating;

    public FriendsAdapter() {

    }

    /**
     * FriendsAdapter constructor that is overloaded
     *
     * @param context the original class its coming from
     * @param UserListingslist is the list of listings
     */
    public FriendsAdapter(Context context,List<UserListings> UserListingslist) { //this class wanted a void? weird
        this.context = context;
        this.UserListingslist = UserListingslist;
        inflater = LayoutInflater.from(context);
       // ArrayList<UserListings> arraylist = new ArrayList<>();
       // arraylist.addAll(UserListingslist);
        imageLoader = new ImageLoader(context);
    }

    /**
     * view holder to instantiate new textviews
     *
     */
    public class ViewHolder {
        TextView name;
        TextView email;
        TextView rating;
        TextView reportCount;
        ImageView userimage;
    }

    @Override
    public int getCount() {
        return UserListingslist.size();
    }

    @Override
    public Object getItem(int position) {
        return UserListingslist.get(position);
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
            view = inflater.inflate(R.layout.listview_item, null);
            // Locate the TextViews in listview_item.xml
            holder.email = (TextView) view.findViewById(R.id.priceWANTEDTVSET);
            holder.name = (TextView) view.findViewById(R.id.itemWANTEDTVSET);
            holder.rating = (TextView) view.findViewById(R.id.foundTVSET);
            holder.reportCount=(TextView)view.findViewById(R.id.reporttitle);
            holder.userimage = (ImageView) view.findViewById(R.id.ivuserprofile);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        // Set the results into TextViews
        holder.email.setText(UserListingslist.get(position).getEmail());
        holder.name.setText(UserListingslist.get(position).getName());
        holder.rating.setText(UserListingslist.get(position).getCurrentRate());
        holder.reportCount.setText(UserListingslist.get(position).getTotalSales());

        // Set the results into ImageView
        imageLoader.DisplayImage(UserListingslist.get(position).getUserImage(),holder.userimage);
        // Listen for ListView Item Click

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                //DEFIANTLY GET OBJECTID SO WE COULD REMOVE THAT FRIENDSHIP ON DELETE BUTTON!

                // Send single item click data to SingleTutorView Class
                Intent intent = new Intent(context, SingleFriendView.class);
                // Pass all data name
                intent.putExtra("NAME",
                        (UserListingslist.get(position).getName()));
                intent.putExtra("EMAIL",
                        (UserListingslist.get(position).getEmail()));
                // Pass all data title
                intent.putExtra("CURRENTRATE",
                        (UserListingslist.get(position).getCurrentRate()));
                // Pass all data price
                intent.putExtra("TOTALSALES",
                        (UserListingslist.get(position).getTotalSales()));
                // Pass all data price
                intent.putExtra("USERIMAGE",
                        (UserListingslist.get(position).getUserImage()));

                intent.putExtra("OBJECTID",
                        (UserListingslist.get(position).getObjectID()));

                objectid = UserListingslist.get(position).getObjectID();
                userid = UserListingslist.get(position).getEmail();

                /*
                List<ParseUser> ob = null;
                ParseQuery<ParseUser> query = ParseUser.getQuery();
                query.whereEqualTo("username", userid);

                try {
                    ob = query.find();
                } catch (com.parse.ParseException e) {
                    e.printStackTrace();
                }
                for (ParseObject Userlist : ob) {
                    totalrates= Userlist.get("totalrates").toString();
                    currentrate=Userlist.get("rating").toString();
                }

                intent.putExtra("TOTALRATES",totalrates);
                intent.putExtra("CURRENTRATE",currentrate);
                 */

                context.startActivity(intent);

            }
        });
        return view;
    }

}
