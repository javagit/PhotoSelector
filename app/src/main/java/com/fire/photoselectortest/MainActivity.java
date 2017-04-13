package com.fire.photoselectortest;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.fire.photoselector.activity.PhotoSelectorActivity;
import com.fire.photoselector.models.PhotoSelectorSetting;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_SELECT_PHOTO = 100;
    private static final String TAG = "MainActivity";
    private ArrayList<String> result = new ArrayList<>();
    private Button btSelectPhoto;
    private RecyclerView rvList;
    private PhotoRecyclerViewAdapter photoRecyclerViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btSelectPhoto = (Button) findViewById(R.id.bt_select_photo);
        rvList = (RecyclerView) findViewById(R.id.rv_list);
        final TextView tvSelectSum = (TextView) findViewById(R.id.tv_select_sum);
        final TextView tvColumnCount = (TextView) findViewById(R.id.tv_column_count);
        rvList.setLayoutManager(new GridLayoutManager(this, 3));
        photoRecyclerViewAdapter = new PhotoRecyclerViewAdapter(this, result, false);
        rvList.setAdapter(photoRecyclerViewAdapter);
        btSelectPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!TextUtils.isEmpty(tvSelectSum.getText().toString().trim()) && !TextUtils.isEmpty(tvColumnCount.getText().toString().trim())) {
                    selectPhotos(Integer.parseInt(tvSelectSum.getText().toString().trim()), Integer.parseInt(tvColumnCount.getText().toString().trim()));
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQUEST_SELECT_PHOTO:
                if (resultCode == RESULT_OK) {
                    result = data.getStringArrayListExtra(PhotoSelectorSetting.LAST_MODIFIED_LIST);
                    boolean isSelectedFullImage = data.getBooleanExtra(PhotoSelectorSetting.SELECTED_FULL_IMAGE, false);
                    photoRecyclerViewAdapter.setList(result, isSelectedFullImage);
                }
                break;
        }
    }

    private void selectPhotos(int sum, int columnCount) {
        PhotoSelectorSetting.MAX_PHOTO_SUM = sum;
        PhotoSelectorSetting.COLUMN_COUNT = columnCount;
        Intent intent = new Intent(MainActivity.this, PhotoSelectorActivity.class);
        intent.putExtra(PhotoSelectorSetting.LAST_MODIFIED_LIST, result);
        startActivityForResult(intent, REQUEST_SELECT_PHOTO);
    }
}
