/*
Name: Siddhi Degaonkar
Student id: 801129943
*/
package com.example.finalexam801129943;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;


public class MainActivity extends AppCompatActivity implements AdapterView.OnItemLongClickListener {
    RecyclerView recyclerView;
    ArrayList<MeetingDomain> meetingDomains;
    ImageView AddMeeting;
    MainActivityAdapter messagesAdapter;
    FirebaseFirestore db;

    ArrayList<String> keyDoc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.rv_meeting);
        AddMeeting = findViewById(R.id.button_add);

        db = FirebaseFirestore.getInstance();
        db.collection("Meetings").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                meetingDomains = new ArrayList<>();
                keyDoc = new ArrayList<>();
                for(QueryDocumentSnapshot queryDocumentSnapshot:task.getResult()){
                    keyDoc.add(queryDocumentSnapshot.getId());
                    MeetingDomain meetingDomain = new MeetingDomain(queryDocumentSnapshot.getData().get("title").toString(),queryDocumentSnapshot.getData().get("place").toString(),queryDocumentSnapshot.getData().get("date").toString(),queryDocumentSnapshot.getData().get("time").toString(),queryDocumentSnapshot.getData().get("uuid").toString());
                    meetingDomains.add(meetingDomain);
                }
                Collections.sort(meetingDomains,new CustomComparator());
                LinearLayoutManager llm = new LinearLayoutManager(MainActivity.this);
                recyclerView.setLayoutManager(llm);
                messagesAdapter = new MainActivityAdapter(meetingDomains,MainActivity.this);
                messagesAdapter.setOnItemLongClickListener(MainActivity.this);
                recyclerView.setAdapter(messagesAdapter);
                DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),
                        llm.getOrientation());
                recyclerView.addItemDecoration(dividerItemDecoration);

            }
        });



        AddMeeting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,ScheduleMeetingActivity.class);
                startActivityForResult(intent,2);

            }
        });


    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("Meetings").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                meetingDomains = new ArrayList<>();
                for(QueryDocumentSnapshot queryDocumentSnapshot:task.getResult()){
                    MeetingDomain meetingDomain = new MeetingDomain(queryDocumentSnapshot.getData().get("title").toString(),queryDocumentSnapshot.getData().get("place").toString(),queryDocumentSnapshot.getData().get("date").toString(),queryDocumentSnapshot.getData().get("time").toString(),queryDocumentSnapshot.getData().get("uuid").toString());
                    meetingDomains.add(meetingDomain);
                }
                Collections.sort(meetingDomains,new CustomComparator());
                LinearLayoutManager llm = new LinearLayoutManager(MainActivity.this);
                recyclerView.setLayoutManager(llm);
                messagesAdapter = new MainActivityAdapter(meetingDomains,MainActivity.this);
                recyclerView.setAdapter(messagesAdapter);

            }
        });
        messagesAdapter.notifyDataSetChanged();
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);

// 2. Chain together various setter methods to set the dialog characteristics
        builder.setMessage("Do you want to delete?")
                .setTitle("Delete Meeting");

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                deleteMeeting(position);
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User cancelled the dialog
                dialog.dismiss();

            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();

        return true;
    }

    private void deleteMeeting(int position){
        db.collection("Meetings").document(keyDoc.get(position))
                .delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d("demo", "DocumentSnapshot successfully deleted!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("demo", "Error deleting document", e);
                    }
                });
        messagesAdapter.getMeetingDomains().remove(position);
        keyDoc.remove(position);

        messagesAdapter.notifyDataSetChanged();
    }
}


class CustomComparator implements Comparator<MeetingDomain> {
    @Override
    public int compare(MeetingDomain o1, MeetingDomain o2) {
        return o1.getDate().compareTo(o2.getDate());
    }
}
