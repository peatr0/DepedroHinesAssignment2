package depedro.hines.n01455125;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;



public class DBScreen extends Fragment {

    FirebaseDatabase db;
    DatabaseReference reference;
    private TextView mTextView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.db_screen, container, false);

        // Initialize Firebase database and reference
        reference = FirebaseDatabase.getInstance().getReference("random_data");

        // Initialize TextView
        mTextView = rootView.findViewById(R.id.modfield_txt);

        Button button = rootView.findViewById(R.id.database_btn);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Read data from Firebase database
                reference.addListenerForSingleValueEvent(new ValueEventListener() {

                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()) {
                            // Data retrieved successfully
                            StringBuilder dataBuilder = new StringBuilder();
                            for (DataSnapshot childSnapshot : snapshot.getChildren()) {
                                Object childData = childSnapshot.getValue();
                                if (childData instanceof String) {
                                    dataBuilder.append((String) childData).append("\n");
                                } else if (childData instanceof Long) {
                                    dataBuilder.append(childData.toString()).append("\n");
                                }
                            }
                            String data = dataBuilder.toString();
                            mTextView.setText(data);
                            mTextView.setTextColor(Color.BLACK);
                            mTextView.setTypeface(null, Typeface.BOLD);
                        } else {
                            // Data does not exist or an error occurred
                            String error = "Error: Data not found";
                            mTextView.setText(error);
                            mTextView.setTextColor(Color.RED);
                            mTextView.setTypeface(null, Typeface.BOLD_ITALIC);
                        }
                    }



                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        // Data retrieval was cancelled
                        String errorMessage = "Error: " + error.getMessage();
                        mTextView.setText(errorMessage);
                        mTextView.setTextColor(Color.RED);
                        mTextView.setTypeface(null, Typeface.BOLD_ITALIC);
                    }
                });
            }
        });

        return rootView;
    }
}