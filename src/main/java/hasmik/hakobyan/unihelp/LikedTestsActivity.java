package hasmik.hakobyan.unihelp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class LikedTestsActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    TestAdapter adapter;
    List<TestItem> allItems;
    TextView empty;

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_liked_tests);
        empty = findViewById(R.id.empty);

        recyclerView = findViewById(R.id.favorite_tests);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));

        allItems = (List<TestItem>) getIntent().getSerializableExtra("all_items_list");

        updateList();


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void updateList() {
        Set<String> likedIds = FavoritesManager.getLikedIds(this);
        List<TestItem> favoriteItems = new ArrayList<>();

        if (allItems != null) {
            for (TestItem item : allItems) {
                if (likedIds.contains(String.valueOf(item.getId()))) {
                    favoriteItems.add(item);
                }
            }
        }
        adapter = new TestAdapter(this, favoriteItems, new TestAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                TestItem item = favoriteItems.get(position);
                Intent intent = new Intent(LikedTestsActivity.this, TestActivity.class);
                intent.putExtra("item_data", item);
                startActivity(intent);
            }
        });


        recyclerView.setAdapter(adapter);

        if (favoriteItems.isEmpty()) {
            empty.setText("Nothing here yet.");
        } else {
            recyclerView.setVisibility(View.VISIBLE);
        }
    }
}