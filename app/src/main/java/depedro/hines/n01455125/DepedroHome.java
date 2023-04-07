package depedro.hines.n01455125;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import java.util.Random;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;


public class DepedroHome extends Fragment {
    TextView textView;
    RadioGroup radioGroup;
    RadioButton radioButton1,radioButton2,radioButton3;
    Button button;
    Random random;
    int Random;
    int minRandom=-50;
    int maxRandom=50;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view= inflater.inflate(R.layout.depedro_home, container, false);
        button=(view).findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Random r=new Random();
                if (radioButton1.isChecked())
                {

                   Random = r.nextInt(maxRandom-minRandom+1)+minRandom;
                   textView.setText(String.valueOf(random));
                } else if (radioButton2.isChecked())
                {
                    String generatedString=random.ints(minRandom,maxRandom+1).limit(10).collect(StringBuilder::new,StringBuilder::appendCodePoint,StringBuilder::append).toString();
                    textView.setText(String.valueOf(generatedString));
                }
            }
        });

        return view;
    }
}