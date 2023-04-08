package depedro.hines.n01455125;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.request.transition.Transition;

public class HinesDownload extends Fragment {

    private ListView listView;
    String[] urls;

    String[] list;// getResources().getStringArray(R.array.list);
    ImageView imageView;
    ProgressBar progressBar;

    private final static int PROGRESS_MAX = 100;
    private final static int PROGRESS_INCREMENT = 10;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.hines_download, container, false);
        imageView=(view).findViewById(R.id.imageView1);
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

        progressBar = view.findViewById(R.id.progressBar1);

        return view;
    }

    private void downloadImage(String imageUrl) {
        progressBar.setVisibility(View.VISIBLE);
        Glide.with(this)
                .load(imageUrl)
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        progressBar.setVisibility(View.GONE);
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                       // progressBar.setVisibility(View.GONE);
                        return false;
                    }
                })
                .into(new CustomTarget<Drawable>() {
                    @Override
                    public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                getActivity().runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        progressBar.setVisibility(View.GONE);
                                        imageView.setImageDrawable(resource);
                                    }
                                });
                            }
                        }, 5000); // 5 second delay
                    }

                    @Override
                    public void onLoadCleared(@Nullable Drawable placeholder) {
                    }
                });
    }

}
