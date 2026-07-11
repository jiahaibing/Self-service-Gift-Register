package com.example.giftregister;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.giftregister.database.GiftDAO;
import com.example.giftregister.model.Gift;
import com.google.android.material.button.MaterialButton;

public class GiftDetailActivity extends AppCompatActivity {
    private TextView tvDetailGiftFrom;
    private TextView tvDetailGiftItem;
    private TextView tvDetailGiftAmount;
    private TextView tvDetailGiftDate;
    private TextView tvDetailGiftNotes;
    private MaterialButton btnDetailEdit;
    private MaterialButton btnDetailDelete;
    private GiftDAO giftDAO;
    private Gift currentGift;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gift_detail);

        initViews();
        initDatabase();
        loadGiftData();
        setupListeners();
    }

    private void initViews() {
        tvDetailGiftFrom = findViewById(R.id.tv_detail_gift_from);
        tvDetailGiftItem = findViewById(R.id.tv_detail_gift_item);
        tvDetailGiftAmount = findViewById(R.id.tv_detail_gift_amount);
        tvDetailGiftDate = findViewById(R.id.tv_detail_gift_date);
        tvDetailGiftNotes = findViewById(R.id.tv_detail_gift_notes);
        btnDetailEdit = findViewById(R.id.btn_detail_edit);
        btnDetailDelete = findViewById(R.id.btn_detail_delete);
    }

    private void initDatabase() {
        giftDAO = new GiftDAO(this);
    }

    private void loadGiftData() {
        currentGift = (Gift) getIntent().getSerializableExtra("gift");
        if (currentGift != null) {
            tvDetailGiftFrom.setText(currentGift.getGiftFrom());
            tvDetailGiftItem.setText(currentGift.getGiftItem());
            tvDetailGiftAmount.setText(String.format("¥%.2f", currentGift.getAmount()));
            tvDetailGiftDate.setText(currentGift.getDate());
            tvDetailGiftNotes.setText(currentGift.getNotes() == null || currentGift.getNotes().isEmpty() 
                    ? "无" : currentGift.getNotes());
        }
    }

    private void setupListeners() {
        btnDetailEdit.setOnClickListener(v -> editGift());
        btnDetailDelete.setOnClickListener(v -> showDeleteConfirmDialog());
    }

    private void editGift() {
        Intent intent = new Intent(this, AddEditGiftActivity.class);
        intent.putExtra("gift", currentGift);
        startActivity(intent);
    }

    private void showDeleteConfirmDialog() {
        new AlertDialog.Builder(this)
                .setTitle("删除确认")
                .setMessage("确定要删除此记录吗？")
                .setPositiveButton("删除", (dialog, which) -> {
                    deleteGift();
                })
                .setNegativeButton("取消", null)
                .show();
    }

    private void deleteGift() {
        int result = giftDAO.delete(currentGift.getId());
        if (result > 0) {
            Toast.makeText(this, "删除成功", Toast.LENGTH_SHORT).show();
            finish();
        } else {
            Toast.makeText(this, "删除失败", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (giftDAO != null) {
            giftDAO.close();
        }
    }
}
