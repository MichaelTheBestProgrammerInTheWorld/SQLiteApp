package com.example.maichel.sqliteapp;

import android.database.Cursor;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    DatabaseHelper mydb;
    EditText editName, editSurname, editMarks;
    Button btnAddData, btnViewAll;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mydb = new DatabaseHelper(this);

        editName = (EditText) findViewById(R.id.editText_name);
        editSurname = (EditText) findViewById(R.id.editText_surname);
        editMarks = (EditText) findViewById(R.id.editText_marks);
        btnAddData = (Button) findViewById(R.id.button_add);
        btnViewAll = (Button) findViewById(R.id.button_viewAll);

        AddData();
        ViewAll();
    }

    public void AddData()  {
        btnAddData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = editName.toString();
                String surname = editSurname.toString();
                String marks = editMarks.toString();
                boolean isInserted = mydb.insertData(name, surname, marks);
                if(isInserted == true)
                    Toast.makeText(MainActivity.this, "Data is inserted successfully", Toast.LENGTH_LONG).show();
                else
                    Toast.makeText(MainActivity.this, " Error occured, Data is not inserted", Toast.LENGTH_LONG).show();
            }
        });
    }

    public void ViewAll ()  {
        btnViewAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Cursor res = mydb.getAllData();
                if (res.getCount() == 0){
                    //show message
                    showMessage("Error", "No Data Found");
                    return;
                }

                StringBuffer buffer = new StringBuffer();
                while (res.moveToNext()) {
                    buffer.append("ID :" + res.getString(0) + "\n");
                    buffer.append("NAME :" + res.getString(1) + "\n");
                    buffer.append("SURNAME :" + res.getString(2) + "\n");
                    buffer.append("MARKS :" + res.getString(3) + "\n \n");
                }

                // show message
                showMessage("Data", buffer.toString());
            }
        });
    }

    public void showMessage (String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }
}
