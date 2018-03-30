package com.example.exp.sleep;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class MainActivity extends AppCompatActivity {

    private static  OrderDBHelper ordersDBHelper ;
    private int boxSel;  //本来想用boolean  但是写入不行
    public CheckBox cb_Remember;//=(CheckBox)findViewById(R.id.cb_remember);
    public EditText et_UserName;//=(EditText)findViewById(R.id.et_UserName);
    public EditText et_Password;//=(EditText)findViewById(R.id.et_Password);
    //保存密码
    public void SavePassword(){

        FileOutputStream out = null;
        BufferedWriter writer = null;
        try{
            out = openFileOutput("sets",Context.MODE_PRIVATE);
            writer = new BufferedWriter(new OutputStreamWriter(out));
            writer.write(boxSel);
            if(boxSel==1){
                writer.newLine();
                //writer.write(et_UserName.toString().length());
                writer.write(et_UserName.getText().toString());//暂且采用明文存储，之后可以改为密文
                writer.newLine();
                //writer.write(et_Password.toString().length());
                writer.write(et_Password.getText().toString());
            }

        }catch (IOException e){
            e.printStackTrace();
        }finally {
            try{
                if(writer !=null){
                    writer.close();
                }
            }catch (IOException e){
                e.printStackTrace();
            }
        }
    }
    //读取密码
    public void LoadPassword(){

        FileInputStream in = null;
        BufferedReader reader = null;
        int Sel=0;
        try{
            in = openFileInput("sets");
            reader = new BufferedReader(new InputStreamReader(in));
            Sel = reader.read();
            if(Sel==1){
                reader.read();
                cb_Remember.setChecked(true);
                String str=reader.readLine();
                et_UserName.setText(str);
                str=reader.readLine();
                et_Password.setText(str);
                boxSel=1;

            }
            else {
                cb_Remember.setChecked(false);
                boxSel=0;
            }

        }catch (IOException e){
            e.printStackTrace();
        }finally {
            if(reader != null){
                try{
                    reader.close();

                }catch (IOException e){
                    e.printStackTrace();
                }
            }
        }
    }

    public void Login(View view){
        //待添加：用户认证(数据库)

        SQLiteDatabase db = ordersDBHelper.getReadableDatabase();
        String[] username={""};
        EditText et_UserName=(EditText)findViewById(R.id.et_UserName);
        EditText et_Password=(EditText)findViewById(R.id.et_Password);
        username[0]=et_UserName.getText().toString();
        Cursor cursor=db.query(OrderDBHelper.TABLE_NAME,new String[]{"Password"},"UserName = ?",username,null,null,null);

        int count=cursor.getCount();
        if(count==0){
            Log.i("login", "No User");
            new AlertDialog.Builder(this).setTitle("Failed").setMessage("cannot find the user").setPositiveButton("ok", null).show();
            return;
        }
        cursor.moveToFirst();
        String str=cursor.getString(0);
        if(et_Password.getText().toString().equals(str)){
            Log.i("login", "Login: succeed");
            new AlertDialog.Builder(this).setTitle("Succeed").setMessage("Login succeed").setPositiveButton("ok", null).show();
            SavePassword();
            startActivity(new Intent(this,HomePageActivity.class));
            return;
         }
        else {
            Log.i("login", "Login: fail");
            new AlertDialog.Builder(this).setTitle("Failed").setMessage("Password wrong").setPositiveButton("ok", null).show();
            return;
        }


    }

    public void Register(View view){

        setContentView(R.layout.activity_register);

    }
    public void Register2(View view){
        //SQLiteDatabase db = ordersDBHelper.getReadableDatabase();

        EditText et_UserName2=(EditText)findViewById(R.id.et_UserName2);
        EditText et_Password2=(EditText)findViewById(R.id.et_Password2);
        String[] username={""};
        username[0]=et_UserName2.getText().toString();
        /*Cursor cursor=db.query(OrderDBHelper.TABLE_NAME,new String[]{"COUNT(UserName)"},"UserName= ?",username,null,null,null);
        if(cursor.getCount()>0){
            Log.i("register", "Register: fail");
            return;
        }*/
        SQLiteDatabase db=ordersDBHelper.getWritableDatabase();
        try{

        db.beginTransaction();
        ContentValues contentValues=new ContentValues();
        contentValues.put("UserName",et_UserName2.getText().toString());
        contentValues.put("Password",et_Password2.getText().toString());
        //db.insert(OrderDBHelper.TABLE_NAME,null, contentValues);
        db.insertOrThrow(OrderDBHelper.TABLE_NAME, null, contentValues);

        db.setTransactionSuccessful();
            Log.i("register", "Register2: succeed,user:"+et_UserName2.getText().toString()+"  \tpassword:"+et_Password2.getText().toString());
            setContentView(R.layout.activity_login);
            et_UserName.setText(et_UserName2.getText().toString());
            et_Password.setText(et_Password2.getText().toString());
            new AlertDialog.Builder(this).setTitle("Succeed").setMessage("Register is ok").setPositiveButton("ok", null).show();
        }catch (SQLiteConstraintException e){
            Log.i("register", "Register2: fail");
            new AlertDialog.Builder(this).setTitle("failed").setMessage("Maybe the username is used").setPositiveButton("ok", null).show();
        }finally {
            db.endTransaction();
        }

    }
    public void Back2(View view){
        setContentView(R.layout.activity_login);
    }
    public void RememberPassword(View view){

        if(cb_Remember.isChecked()){
            cb_Remember.setChecked(false);
            boxSel=0;
        }
        else{
            cb_Remember.setChecked(true);
            boxSel=1;
        }

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //应先加载欢迎界面，进行数据处理
        setContentView(R.layout.activity_login);
        cb_Remember=(CheckBox)findViewById(R.id.cb_Remember);
        et_UserName=(EditText)findViewById(R.id.et_UserName);
        et_Password=(EditText)findViewById(R.id.et_Password);
        ordersDBHelper = new OrderDBHelper(this);
        LoadPassword();



    }


}


class OrderDBHelper extends SQLiteOpenHelper {
    private static final int DB_VERSION = 1;
    private static final String DB_NAME = "SleepTime.db";
    public static final String TABLE_NAME = "User";

    public OrderDBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        // create table Orders(Id integer primary key, CustomName text, OrderPrice integer, Country text);
        String sql = "create table if not exists " + TABLE_NAME + " (UserName text primary key, Password text)";
        sqLiteDatabase.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        String sql = "DROP TABLE IF EXISTS " + TABLE_NAME;
        sqLiteDatabase.execSQL(sql);
        onCreate(sqLiteDatabase);
    }
}