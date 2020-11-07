package com.example.app;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.widget.ProgressBar;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.app.dagger.DaggerApiComponent;

/**
 * @author WhenYoung
 * CreateAt Time 2020/10/14  12:23
 */
public class BaseActivity extends AppCompatActivity {
    ProgressDialog pb;

    @Override
    public String[] databaseList() {
        return super.databaseList();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pb = new ProgressDialog(this);
    }


    public void showPb(){
        pb.show();
    }

    public void dismissPb(){
        if(pb.isShowing()){
            pb.dismiss();
        }
    }

}
