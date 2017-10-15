package com.example.neeraj.buddyhub;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import static com.example.neeraj.buddyhub.Property_Description.totalItems;

/**
 * Created by ankitgarg on 14/10/17.
 */

public class ImageFragment extends Fragment {
    private Image_Fragment_Object itemData;
    private int page;
    ImageView imageView;

    public static ImageFragment newInstance() {
        ImageFragment f = new ImageFragment();
        return f;
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_house_image, container, false);
        TextView textView=(TextView)rootView.findViewById(R.id.countii);
        textView.setText(String.valueOf(getArguments().getInt("page_position"))+"/"+totalItems);
        // Get the arguments that was supplied when
        // the fragment was instantiated in the
        // CustomPagerAdapter

         imageView=(ImageView)rootView.findViewById(R.id.large_image);

        //((TextView) rootView.findViewById(R.id.image_name)).setText("Page " + args.getInt("page_position"));
        setImageInViewPager();
        return rootView;
    }
    public void setImageList(Image_Fragment_Object val) {
        this.itemData = val;
    }
    public void setImageInViewPager(){
        imageView.setImageResource(itemData.getImageId());
    }


}
