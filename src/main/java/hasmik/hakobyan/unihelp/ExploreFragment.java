package hasmik.hakobyan.unihelp;

import static com.google.firebase.auth.AuthKt.getAuth;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.ComponentActivity;
import androidx.activity.EdgeToEdge;
import androidx.cardview.widget.CardView;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ExploreFragment extends Fragment implements TestAdapter.OnItemClickListener{

    List<TestItem> testItemList;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter testAdapter;
    private RecyclerView.LayoutManager layoutManager;
    TextView welcome_name,long_text;
    private CardView favorites_card;

    public ExploreFragment() {
        // Required empty public constructor
    }

    private FirebaseAuth mAuth;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_explore, container, false);

        testItemList = new ArrayList<>();
        fillTestList();
        recyclerView = view.findViewById(R.id.re_test);

        RecyclerView.LayoutManager layoutManager =
                new LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);
        testAdapter = new TestAdapter(requireContext(), testItemList, this);
        recyclerView.setAdapter(testAdapter);
        welcome_name = view.findViewById(R.id.welcome_name);
        long_text = view.findViewById(R.id.long_text);
        favorites_card = view.findViewById(R.id.card2);

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            String name = user.getDisplayName();
            welcome_name.setText(name);
        } else {
            welcome_name.setText("");
        }
        favorites_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), LikedTestsActivity.class);

                intent.putExtra("all_items_list", (ArrayList<TestItem>) testItemList);
                startActivity(intent);
            }
        });
        return view;
    }

    private void fillTestList() {
        TestItem t1 = new TestItem(1,"Take a test and know if living abroad is for you.", "Abroad life",R.drawable.study_abroad,R.drawable.liked);
        TestItem t2 = new TestItem(2,"Not sure which path is right for you? We can help you!", "Future Career",R.drawable.future_career,R.drawable.liked);
        TestItem t3 = new TestItem(3,"Find the type of country that fits your lifestyle.", "Your country",R.drawable.your_country,R.drawable.liked);
        TestItem t4 = new TestItem(4,"Help us understand your lifestyle.", "Your preferences",R.drawable.your_preferences,R.drawable.liked);

        testItemList.add(t1);
        testItemList.add(t2);
        testItemList.add(t3);
        testItemList.add(t4);

    }

    @Override
    public void onItemClick(int position) {
        testItemList.get(position);
        Intent intent = new Intent(getContext(), TestActivity.class);
        startActivity(intent);
    }
    @Override
    public void onResume() {
        super.onResume();
        if (testAdapter != null) {
            // Это заставит адаптер заново вызвать FavoritesManager.getLikedIds
            // и перерисовать сердечки
            testAdapter.notifyDataSetChanged();
        }
    }
}