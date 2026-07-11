package com.example.giftregister;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.giftregister.adapter.GiftAdapter;
import com.example.giftregister.database.GiftDAO;
import com.example.giftregister.model.Gift;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private RecyclerView rvGifts;
    private GiftAdapter adapter;
    private GiftDAO giftDAO;
    private FloatingActionButton fabAdd;
    private EditText etSearch;
    private MaterialButton btnSearch;
    private TextView tvTotalAmount;
    private LinearLayout llEmptyState;
    private List<Gift> giftList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();
        initDatabase();
        loadAllGifts();
        setupListeners();
    }

    private void initViews() {
        rvGifts = findViewById(R.id.rv_gifts);
        fabAdd = findViewById(R.id.fab_add);
        etSearch = findViewById(R.id.et_search);
        btnSearch = findViewById(R.id.btn_search);
        tvTotalAmount = findViewById(R.id.tv_total_amount);
        llEmptyState = findViewById(R.id.ll_empty_state);

        rvGifts.setLayoutManager(new LinearLayoutManager(this));
    }

    private void initDatabase() {
        giftDAO = new GiftDAO(this);
    }

    private void loadAllGifts() {
        giftList = giftDAO.getAllGifts();
        updateUI();
    }

    private void updateUI() {
        if (giftList != null && giftList.size() > 0) {
            llEmptyState.setVisibility(View.GONE);
            rvGifts.setVisibility(View.VISIBLE);
            adapter = new GiftAdapter(this, giftList);
            setupAdapterListeners();
            rvGifts.setAdapter(adapter);
        } else {
            llEmptyState.setVisibility(View.VISIBLE);
            rvGifts.setVisibility(View.GONE);
        }
        updateTotalAmount();
    }

    private void setupAdapterListeners() {
        adapter.setOnItemClickListener(gift -> {
            // Edit gift
            Intent intent = new Intent(MainActivity.this, AddEditGiftActivity.class);
            intent.putExtra("gift", gift);
            startActivity(intent);
        });

        adapter.setOnItemLongClickListener(gift -> {
            // Delete gift
            showDeleteConfirmDialog(gift);
        });
    }

    private void setupListeners() {
        fabAdd.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, AddEditGiftActivity.class);
            startActivity(intent);
        });

        btnSearch.setOnClickListener(v -> {
            String keyword = etSearch.getText().toString().trim();
            if (keyword.isEmpty()) {
                loadAllGifts();
            } else {
                searchGifts(keyword);
            }
        });
    }

    private void searchGifts(String keyword) {
        giftList = giftDAO.searchGifts(keyword);
        updateUI();
    }

    private void showDeleteConfirmDialog(Gift gift) {
        new AlertDialog.Builder(this)
                .setTitle("删除确认")
                .setMessage("确定要删除此记录吗？")
                .setPositiveButton("删除", (dialog, which) -> {
                    deleteGift(gift);
                })
                .setNegativeButton("取消", null)
                .show();
    }

    private void deleteGift(Gift gift) {
        int result = giftDAO.delete(gift.getId());
        if (result > 0) {
            Toast.makeText(this, "删除成功", Toast.LENGTH_SHORT).show();
            loadAllGifts();
        } else {
            Toast.makeText(this, "删除失败", Toast.LENGTH_SHORT).show();
        }
    }

    private void updateTotalAmount() {
        double total = giftDAO.getTotalAmount();
        tvTotalAmount.setText(String.format("¥%.2f", total));
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadAllGifts();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (giftDAO != null) {
            giftDAO.close();
        }
    }
}
