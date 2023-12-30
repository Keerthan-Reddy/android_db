package com.example.android_db;


import androidx.appcompat.app.AppCompatActivity;
import android.app.AlertDialog;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
public class MainActivity extends AppCompatActivity {
    Button Insert,viewAll;
    EditText Rollno,Name,Marks;
    SQLiteDatabase db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main);
        Insert = findViewById(R.id.INSERT);
        viewAll = findViewById(R.id.VIEWALL);
        Rollno = findViewById(R.id.Rollno);
        Name = findViewById(R.id.Name);
        Marks = findViewById(R.id.Marks);
        db = openOrCreateDatabase("StudentDB", Context.MODE_PRIVATE, null);
        db.execSQL("CREATE TABLE IF NOT EXISTS student(rollno varchar,name varchar,marks number)");

        Insert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Rollno.getText().toString().length() == 0 || Name.getText().toString().length()
                        == 0 || Marks.getText().toString().length() == 0) {
                    Toast.makeText(getApplicationContext(), "Please enter details",
                            Toast.LENGTH_LONG).show();
                    return;
                }
                db.execSQL("INSERT INTO STUDENT VALUES('"+Rollno.getText()+"','"+Name.getText()+"','"+Marks.getText()+"');");
                showMessage("Success","SUCCESSFULLY ADDED");
                clearText();
            }
        });


        viewAll.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Cursor c = db.rawQuery("select * from student", null);
                if (c.getCount() == 0) {
                    showMessage("error", "No records inserted");
                    return;
                }
                StringBuffer bf = new StringBuffer();
                while (c.moveToNext()) {
                    bf.append("Rollno:" + c.getString(0) + "\n");
                    bf.append("Name:" + c.getString(1) + "\n");
                    bf.append("Marks:" + c.getString(2) + "\n\n");
                }
                showMessage("Studet details", bf.toString());
            }
        });
    }
    public void showMessage(String title,String message)
    {
        AlertDialog.Builder b=new AlertDialog.Builder(this);
        b.setCancelable(true);
        b.setTitle(title);
        b.setMessage(message);
        b.show();
    }
    private void clearText()
    {
        Rollno.setText("");
        Name.setText("");
        Marks.setText("");
        Rollno.requestFocus();
    }
}
