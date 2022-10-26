package com.example.notes;

import androidx.cardview.widget.CardView;

import Models.Notes;

/**
 * Click on notes logic
 */
public interface INotesClickListener {
    void onClick(Notes notes);
    void onLongClick(Notes notes, CardView cardView);
}
