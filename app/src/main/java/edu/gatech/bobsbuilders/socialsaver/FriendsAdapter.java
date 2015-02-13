package edu.gatech.bobsbuilders.socialsaver;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


public class FriendsAdapter extends BaseAdapter {
    Context context;
    LayoutInflater inflater;
    float num;
    ImageLoader imageLoader;
    private List<UserListings> UserListingslist = null;
    private ArrayList<UserListings> arraylist;
    String userid,usermessage,totalrates, currentrate,objectid;

    public FriendsAdapter() {

    }

    /**
     * FriendsAdapter constructor that is overloaded
     *
     * @param context the original class its coming from
     * @param UserListingsList is the list of listings
     * @return A new instance of fragment HomeScreen.
     */
    public FriendsAdapter(Context context,List<UserListings> UserListingslist) { //this class wanted a void? weird
        this.context = context;
        this.UserListingslist = UserListingslist;
        inflater = LayoutInflater.from(context);
        this.arraylist = new ArrayList<UserListings>();
        this.arraylist.addAll(UserListingslist);
        imageLoader = new ImageLoader(context);
    }

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

    public View getView(final int position, View view, ViewGroup parent) {
        final ViewHolder holder;
        if (view == null) {
            holder = new ViewHolder();
            view = inflater.inflate(R.layout.listview_item, null);
            // Locate the TextViews in listview_item.xml
            holder.email = (TextView) view.findViewById(R.id.emailtitle);
            holder.name = (TextView) view.findViewById(R.id.nametitle);
            holder.rating = (TextView) view.findViewById(R.id.ratingtitle);
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
        holder.reportCount.setText(UserListingslist.get(position).getTotalSales().toString());

        // Set the results into ImageView
        imageLoader.DisplayImage(UserListingslist.get(position).getUserImage(),holder.userimage);
        // Listen for ListView Item Click

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                /*
                // Send single item click data to SingleTutorView Class
                Intent intent = new Intent(context, SingleTutorView.class);
                // Pass all data name
                intent.putExtra("NAME",
                        (UserListingslist.get(position).getName()));
                intent.putExtra("CATAGORY",
                        (UserListingslist.get(position).getCatagory()));
                // Pass all data title
                intent.putExtra("TITLE",
                        (UserListingslist.get(position).getTitle()));
                // Pass all data price
                intent.putExtra("PRICE",
                        (UserListingslist.get(position).getPrice()));

                intent.putExtra("OBJECTID",
                        (UserListingslist.get(position).getId()));


                userid=UserListingslist.get(position).getUserId();

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
                    usermessage=Userlist.get("userwords").toString();
                    if(usermessage.equals("Click edit and tell your future tutors/students more about YOU! Don't forget to click the box by your name to add a photo of yourself")){
                        usermessage="This user has not updated there description yet";
                    }
                }


                intent.putExtra("EMAIL", userid);
                intent.putExtra("USERWORDS",usermessage);
                // Pass all data userimage
                intent.putExtra("USERIMAGE",(UserListingslist.get(position).getUserImage()));

                intent.putExtra("TOTALRATES",totalrates);

                intent.putExtra("CURRENTRATE",currentrate);

                context.startActivity(intent);
                */
            }
        });
        return view;
    }

}
