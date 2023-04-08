package depedro.hines.n01455125;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.GlideException;


public class HinesDownload extends Fragment {

    private ListView listView;
    String[] urls;

    String[] list;// getResources().getStringArray(R.array.list);
    ImageView imageView;
    ProgressBar progressBar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view= inflater.inflate(R.layout.hines_download, container, false);
        imageView=(view).findViewById(R.id.imageView1);
        progressBar = (ProgressBar) view.findViewById(R.id.progressBar1);
        listView = view.findViewById(R.id.listView1);
        urls=getResources().getStringArray(R.array.urls);
        list = getResources().getStringArray(R.array.list);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_list_item_1,list);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                listView.setSelector(android.R.color.holo_blue_dark);
                // String selectedOption = list[position];
                String imageUrl = urls[position];
                downloadImage(imageUrl);
            }
        });

        return view;
    }

    private void downloadImage(String imageUrl) {
        progressBar.setVisibility(View.VISIBLE);

        Glide.with(this)
                .load(imageUrl)
                .listener(new com.bumptech.glide.request.RequestListener<android.graphics.drawable.Drawable>() {
                    @Override
                    public boolean onLoadFailed(@androidx.annotation.Nullable GlideException e, Object model, com.bumptech.glide.request.target.Target<android.graphics.drawable.Drawable> target, boolean isFirstResource) {
                        progressBar.setVisibility(View.GONE);
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(android.graphics.drawable.Drawable resource, Object model, com.bumptech.glide.request.target.Target<android.graphics.drawable.Drawable> target, com.bumptech.glide.load.DataSource dataSource, boolean isFirstResource) {
                        progressBar.setVisibility(View.GONE);
                        return false;
                    }
                })
                .into(imageView);
    }
}
