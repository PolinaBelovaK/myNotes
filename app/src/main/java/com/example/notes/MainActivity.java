package com.example.notes;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.PopupMenu;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import Adapter.NotesListAdapter;
import Database.RoomDB;
import Models.Notes;

public class MainActivity extends AppCompatActivity implements PopupMenu.OnMenuItemClickListener{

    RecyclerView recyclerView;
    FloatingActionButton fab;
    NotesListAdapter notesListAdapter;
    RoomDB database;
    List<Notes> notes = new ArrayList<>();
    SearchView searchView_home;
    Notes selectedNote;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        searchView_home = findViewById(R.id.searchView_home); //search field
        recyclerView = findViewById(R.id.recycler_home); //List with all notes
        fab = findViewById(R.id.fab_add); //add btn
        database = RoomDB.getInstance(this);//get database
        notes = database.iDao().getAll();//"get all" command from interface (Select all from table)
        updateRecycle(notes);

        //Add new note:
        fab.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, NotesTakenActivity.class);
            startActivityForResult(intent, 101);
        });

        //Search field logic
        searchView_home.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filter(newText);
                return true;
            }
        });
    }

    //This command is needed to search for a note by title, text
    private void filter(String newText) {
        List<Notes> filteredList = new ArrayList<>();
        for (Notes singleNote: notes) {
            if (singleNote.getTitle().toLowerCase().contains(newText.toLowerCase()) || singleNote.getNotes().toLowerCase().contains(newText.toLowerCase())){
                filteredList.add(singleNote);
            }
            notesListAdapter.filteredList(filteredList);
        }
    }

    //main screen logic. If RESULT_OK and everything is displayed correctly, display a list of notes(look Adapter pack)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data){
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 101) {
            if (resultCode == Activity.RESULT_OK){
                Notes new_notes = (Notes) data.getSerializableExtra("note");
                database.iDao().insert(new_notes);
                notes.clear();
                notes.addAll(database.iDao().getAll());
                notesListAdapter.notifyDataSetChanged();
            }
        }

        if (requestCode == 102) {
            if (resultCode == Activity.RESULT_OK) {
                Notes new_notes = (Notes) data.getSerializableExtra("note");
                database.iDao().update(new_notes.getID(), new_notes.getTitle(), new_notes.getNotes());
                notes.clear();
                notes.addAll(database.iDao().getAll());
                notesListAdapter.notifyDataSetChanged();
            }
        }
    }

    //Add new note on screen
    private void updateRecycle(List<Notes> notes) {
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL));
        notesListAdapter = new NotesListAdapter(MainActivity.this, notes, ncl);
        recyclerView.setAdapter(notesListAdapter);

    }

    //interface INotesClickListener realization.
    //If a normal short click on a note is made -  this note will be open,
    //If long click - show popup menu
    private final INotesClickListener ncl = new INotesClickListener() {
        @Override
        public void onClick(Notes notes) {
            Intent intent = new Intent(MainActivity.this, NotesTakenActivity.class);
            intent.putExtra("old_notes", notes);
            startActivityForResult(intent, 102);
        }

        @Override
        public void onLongClick(Notes notes, CardView cardView) {
            selectedNote = new Notes();
            selectedNote = notes;
            showPopUp(cardView);

        }
    };

    //popup menu logic. Here only two commands: pin|unpin and delete.
     private void showPopUp(CardView cardView) {
        PopupMenu popupMenu = new PopupMenu(this, cardView);
        popupMenu.setOnMenuItemClickListener(this);
        popupMenu.inflate(R.menu.popup_menu);
        popupMenu.show();
    }

    //Commands in popup menu
    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()){
            case R.id.pin:
                if (selectedNote.isPinned()) {
                    database.iDao().pin(selectedNote.getID(), false);
                    Toast.makeText(MainActivity.this, "Откреплено", Toast.LENGTH_SHORT).show();
                }else{
                    database.iDao().pin(selectedNote.getID(),true);
                    Toast.makeText(MainActivity.this, "Закреплено", Toast.LENGTH_SHORT).show();
                }
                notes.clear();
                notes.addAll(database.iDao().getAll());
                notesListAdapter.notifyDataSetChanged();
                return true;

            case R.id.delete:
                database.iDao().delete(selectedNote);
                notes.remove(selectedNote);
                notesListAdapter.notifyDataSetChanged();
                Toast.makeText(MainActivity.this, "Запись удалена", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return false;
        }
    }
}