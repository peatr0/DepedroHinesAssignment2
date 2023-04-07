package depedro.hines.n01455125;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;


public class HinesDownload extends Fragment {

    private ListView listView;
    String[] list;  //= getResources().getStringArray(R.array.concepts);
    String[] definition; // = getResources().getStringArray(R.array.Definitions);

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        list = getResources().getStringArray(R.array.list);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_list_item_1,list);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view= inflater.inflate(R.layout.hines_download, container, false);

        return view;
    }
}