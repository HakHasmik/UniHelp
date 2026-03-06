package hasmik.hakobyan.unihelp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class TestAdapter extends RecyclerView.Adapter<TestAdapter.ViewHolder> {

    private List<TestItem> testItems;
    private OnItemClickListener mOnItemClickListener;
    Context context;


    private final LayoutInflater inflater;

    public TestAdapter(Context context, List<TestItem> testItems, OnItemClickListener onItemClickListener) {
        this.context = context;
        this.testItems = testItems;
        this.inflater = LayoutInflater.from(context);
        this.mOnItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = inflater.inflate(R.layout.test_re_item, parent, false);

        return new ViewHolder(view, mOnItemClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull TestAdapter.ViewHolder holder, int position) {
        TestItem item = testItems.get(position);

        Set<String> likedIds = FavoritesManager.getLikedIds(context);
        item.setLiked(likedIds.contains(String.valueOf(item.getId())));
        holder.image.setImageResource(item.getImage());
        holder.header.setText(item.getHeader());
        holder.text.setText(item.getMain_text());

        holder.likedIcon.setImageResource(item.isLiked() ? R.drawable.liked_filled : R.drawable.liked);

        holder.likedIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int actualPosition = holder.getAdapterPosition();

                if (actualPosition == RecyclerView.NO_POSITION) return;
                boolean newStatus = !item.isLiked();
                item.setLiked(newStatus);
                FavoritesManager.saveLike(context, item.getId(), newStatus);

                holder.likedIcon.setImageResource(newStatus ? R.drawable.liked_filled : R.drawable.liked);

                if (context instanceof LikedTestsActivity && !newStatus) {
                    testItems.remove(actualPosition);
                    notifyItemRemoved(actualPosition);
                    notifyItemRangeChanged(actualPosition, testItems.size());
                } else {
                    holder.likedIcon.setImageResource(newStatus ? R.drawable.liked_filled : R.drawable.liked);
                }

            }
        });
    }


    @Override
    public int getItemCount() {
        return testItems.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView header, text;
        ImageView image, likedIcon;
        OnItemClickListener onItemClickListener;

        ViewHolder(View view, OnItemClickListener onItemClickListener) {
            super(view);
            header = view.findViewById(R.id.header);
            text = view.findViewById(R.id.main_text);
            image = view.findViewById(R.id.image);
            likedIcon = view.findViewById(R.id.liked_icon);


            this.onItemClickListener = onItemClickListener;
            view.setOnClickListener(this);
        }


        @Override
        public void onClick(View v) {
            onItemClickListener.onItemClick(getAdapterPosition());
        }
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }
}
