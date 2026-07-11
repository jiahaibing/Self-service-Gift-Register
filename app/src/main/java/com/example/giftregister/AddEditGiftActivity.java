package com.example.giftregister;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.giftregister.database.GiftDAO;
import com.example.giftregister.model.Gift;
import com.google.android.material.button.MaterialButton;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class AddEditGiftActivity extends AppCompatActivity {
    private EditText etGiftFrom;
    private EditText etGiftItem;
    private EditText etGiftAmount;
    private EditText etGiftDate;
    private EditText etGiftNotes;
    private MaterialButton btnSave;
    private MaterialButton btnCancel;
    private GiftDAO giftDAO;
    private Gift currentGift;
    private Calendar selectedDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_gift);

        initViews();
        initDatabase();
        loadGiftData();
        setupDatePicker();
        setupListeners();
    }

    private void initViews() {
        etGiftFrom = findViewById(R.id.et_gift_from);
        etGiftItem = findViewById(R.id.et_gift_item);
        etGiftAmount = findViewById(R.id.et_gift_amount);
        etGiftDate = findViewById(R.id.et_gift_date);
        etGiftNotes = findViewById(R.id.et_gift_notes);
        btnSave = findViewById(R.id.btn_save);
        btnCancel = findViewById(R.id.btn_cancel);
    }

    private void initDatabase() {
        giftDAO = new GiftDAO(this);
    }

    private void loadGiftData() {
        currentGift = (Gift) getIntent().getSerializableExtra("gift");
        if (currentGift != null) {
            // Edit mode
            etGiftFrom.setText(currentGift.getGiftFrom());
            etGiftItem.setText(currentGift.getGiftItem());
            etGiftAmount.setText(String.valueOf(currentGift.getAmount()));
            etGiftDate.setText(currentGift.getDate());
            etGiftNotes.setText(currentGift.getNotes());
        } else {
            // Add mode - set today's date
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
            etGiftDate.setText(sdf.format(System.currentTimeMillis()));
        }
    }

    private void setupDatePicker() {
        selectedDate = Calendar.getInstance();
        etGiftDate.setOnClickListener(v -> showDatePicker());
    }

    private void showDatePicker() {
        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                (view, year, month, dayOfMonth) -> {
                    selectedDate.set(year, month, dayOfMonth);
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
                    etGiftDate.setText(sdf.format(selectedDate.getTime()));
                },
                selectedDate.get(Calendar.YEAR),
                selectedDate.get(Calendar.MONTH),
                selectedDate.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
    }

    private void setupListeners() {
        btnSave.setOnClickListener(v -> saveGift());
        btnCancel.setOnClickListener(v -> finish());
    }

    private void saveGift() {
        if (!validateInput()) {
            Toast.makeText(this, getString(R.string.input_error), Toast.LENGTH_SHORT).show();
            return;
        }

        String giftFrom = etGiftFrom.getText().toString().trim();
        String giftItem = etGiftItem.getText().toString().trim();
        double amount = Double.parseDouble(etGiftAmount.getText().toString().trim());
        String date = etGiftDate.getText().toString().trim();
        String notes = etGiftNotes.getText().toString().trim();

        if (currentGift != null) {
            // Update existing gift
            currentGift.setGiftFrom(giftFrom);
            currentGift.setGiftItem(giftItem);
            currentGift.setAmount(amount);
            currentGift.setDate(date);
            currentGift.setNotes(notes);
            
            int result = giftDAO.update(currentGift);
            if (result > 0) {
                Toast.makeText(this, "更新成功", Toast.LENGTH_SHORT).show();
                finish();
            } else {
                Toast.makeText(this, "更新失败", Toast.LENGTH_SHORT).show();
            }
        } else {
            // Add new gift
            Gift newGift = new Gift(giftFrom, giftItem, amount, date, notes);
            long result = giftDAO.insert(newGift);
            if (result > 0) {
                Toast.makeText(this, getString(R.string.save_success), Toast.LENGTH_SHORT).show();
                finish();
            } else {
                Toast.makeText(this, "保存失败", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private boolean validateInput() {
        if (etGiftFrom.getText().toString().trim().isEmpty()) {
            return false;
        }
        if (etGiftItem.getText().toString().trim().isEmpty()) {
            return false;
        }
        if (etGiftAmount.getText().toString().trim().isEmpty()) {
            return false;
        }
        if (etGiftDate.getText().toString().trim().isEmpty()) {
            return false;
        }
        try {
            Double.parseDouble(etGiftAmount.getText().toString().trim());
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (giftDAO != null) {
            giftDAO.close();
        }
    }
}
