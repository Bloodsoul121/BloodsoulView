package com.example.administrator.bloodsoulview.optionmenu;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.administrator.bloodsoulview.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MenuOptionActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    private Menu mMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_option);
        ButterKnife.bind(this);

        // 替换掉 ActionBar
        setSupportActionBar(mToolbar);

        // 设置主键图标
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.love);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) { // 生命周期在 onResume 之后
        mMenu = menu;
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home: // 主键
                Toast.makeText(this, "Home now", Toast.LENGTH_SHORT).show();
                break;
            case R.id.menu_task:
                Toast.makeText(this, "aaa", Toast.LENGTH_SHORT).show();
                break;
            case R.id.menu_friends:
                Toast.makeText(this, "bbb", Toast.LENGTH_SHORT).show();
                break;
            case R.id.menu_main_add:
                Toast.makeText(this, "ccc", Toast.LENGTH_SHORT).show();
                break;
            case R.id.menu_live:
                Toast.makeText(this, "ddd", Toast.LENGTH_SHORT).show();
                break;
        }
        return true;
    }

    public void clickBtn1(View view) {
        if (mMenu != null) {
            MenuItem item = mMenu.findItem(R.id.menu_task);
            item.setVisible(false);
        }
    }

    public void clickBtn2(View view) {
        if (mMenu != null) {
            MenuItem item = mMenu.findItem(R.id.menu_task);
            item.setVisible(true);
        }
    }
}
