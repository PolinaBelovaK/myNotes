package Adapter;

import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.notes.INotesClickListener;
import com.example.notes.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import Models.Notes;

public class NotesListAdapter extends RecyclerView.Adapter<NotesViewHolder> {

    Context context;
    List<Notes>list;
    INotesClickListener listener;

    //get all the necessary objects
    public NotesListAdapter(Context context, List<Notes> list, INotesClickListener listener){
        this.context = context; //context
        this.list = list;//list with all notes
        this.listener = listener;//click listener
    }

    @NonNull
    @Override
    public NotesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new NotesViewHolder(LayoutInflater.from(context).inflate(R.layout.notes_list, parent, false));
    }

    //note holder logic (on main screen)
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onBindViewHolder(@NonNull NotesViewHolder holder, int position) {
        holder.textView_title.setText(list.get(position).getTitle());
        holder.textView_title.setSelected(true);
        holder.textView_notes.setText(list.get(position).getNotes());
        holder.textView_date.setText(list.get(position).getDate());
        holder.textView_date.setSelected(true);
        if (list.get(position).isPinned()){
            holder.image_view_pin.setImageResource(R.drawable.ic_pin);
        }else{
            holder.image_view_pin.setImageResource(0);
        }
        int color_code = getRandomColor();
        holder.note_container.setCardBackgroundColor(holder.itemView.getResources().getColor(color_code, null));
        holder.note_container.setOnClickListener(view -> listener.onClick(list.get(holder.getAdapterPosition())));
        holder.note_container.setOnLongClickListener(view -> {
            listener.onLongClick(list.get(holder.getAdapterPosition()), holder.note_container);
            return true;
            });
        }

        //method for random color on card w note
   private int getRandomColor(){
        List<Integer> colorCode = new ArrayList<>();
        colorCode.add(R.color.lavender);
        colorCode.add(R.color.beach);
        colorCode.add(R.color.greeny);
        colorCode.add(R.color.light_blue);
        Random random = new Random();
        int random_color = random.nextInt(colorCode.size());
        return colorCode.get(random_color);
    }

    //get count from list w notes
    @Override
    public int getItemCount() {
        return list.size();
    }

    //logic filter from main
    public void filteredList(List<Notes>filteredList) {
        list = filteredList;
        notifyDataSetChanged();
    }
}

//all received objects
class NotesViewHolder extends RecyclerView.ViewHolder {

    CardView note_container;
    TextView textView_title;
    ImageView image_view_pin;
    TextView textView_notes;
    TextView textView_date;

    public NotesViewHolder(@NonNull View itemView) {
        super(itemView);

        note_container = itemView.findViewById(R.id.note_container);
        textView_title = itemView.findViewById(R.id.textView_title);
        image_view_pin = itemView.findViewById(R.id.image_view_pin);
        textView_notes = itemView.findViewById(R.id.textView_notes);
        textView_date = itemView.findViewById(R.id.textView_date);
    }
}
