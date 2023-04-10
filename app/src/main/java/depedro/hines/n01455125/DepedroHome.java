/*
Name:Depedro Hines
Section: Mobile programming CENG 258-ONB
Student#:n01455125
 */
package depedro.hines.n01455125;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import java.util.Random;
import java.util.stream.Collectors;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import depedro.hines.n01455125.databinding.ActivityMainBinding;


public class DepedroHome extends Fragment {
    FirebaseDatabase db;
    DatabaseReference reference;
    ActivityMainBinding binding;
    TextView textView;
    RadioGroup radioGroup;
    RadioButton radioButton1,radioButton2,radioButton3;
    Button button;
    Random random;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Initialize Firebase database and reference
        db = FirebaseDatabase.getInstance();
        reference = db.getReference("random_data");
        reference= db.getReference("storeddata");

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view= inflater.inflate(R.layout.depedro_home, container, false);
        textView=(view).findViewById(R.id.textView);
        radioButton1=(view).findViewById(R.id.RandomNmbr);
        radioButton2=(view).findViewById(R.id.Randomtxt);
        button=(view).findViewById(R.id.button);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Random r = new Random();
                if (radioButton1.isChecked()) {
                    int minRandom = -50;
                    int maxRandom = 50;
                    int randomNumber = r.nextInt(maxRandom - minRandom + 1) + minRandom;
                    textView.setText(String.valueOf(randomNumber));

                    // Send data to Firebase database
                    reference.child("number").setValue(randomNumber);
                    String name = getString(R.string.name);
                    String studentid = getString(R.string.student);
                    String fullName = name + " " + studentid;
                    reference.child("string").setValue(fullName);


                } else if (radioButton2.isChecked()) {
                    int min = 97; // 'a' in ASCII
                    int max = 122; // 'z' in ASCII

                    String generatedString = r.ints(min, max + 1)
                            .limit(10)
                            .mapToObj(i -> String.valueOf((char) i))
                            .collect(Collectors.joining());

                    textView.setText(generatedString);
                    String name = getString(R.string.name);
                    String studentid = getString(R.string.student);
                    String fullName = name + " " + studentid;
                    reference.child("storeddata").setValue(fullName);
                    reference.child("string").setValue(generatedString);
                } if (radioButton3 != null && radioButton3.isChecked()) {
                    String initial=getString(R.string.initial);
                    String name = getString(R.string.name);
                    String studentid = getString(R.string.student);
                    String fullName = name + " " + studentid;
                    reference.child("storeddata").setValue(fullName);
                    textView.setText(initial);
                }


            }
        });

        return view;
    }
}
