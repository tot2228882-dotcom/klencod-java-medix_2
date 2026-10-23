package com.example.medix; // تأكد من تغيير هذا لاسم الباكيج الخاص بمشروعك

import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.text.InputType;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

public class MainActivity extends AppCompatActivity {

    // الألوان
    private final int COLOR_PRIMARY = Color.parseColor("#0D47A1");
    private final int COLOR_BACKGROUND = Color.parseColor("#F5F7FA");
    private final int COLOR_WHITE = Color.WHITE;
    private final int COLOR_TEXT_DARK = Color.parseColor("#1E1E1E");
    private final int COLOR_TEXT_LIGHT = Color.parseColor("#757575");
    private final int COLOR_ERROR = Color.parseColor("#D32F2F");
    private final int COLOR_ERROR_BG = Color.parseColor("#FFEBEE");
    private final int COLOR_SUCCESS = Color.parseColor("#388E3C");
    private final int COLOR_SUCCESS_BG = Color.parseColor("#E8F5E9");

    // العناصر
    private Spinner spinnerTestType;
    private EditText etResult;
    private CardView cardResult;
    private TextView tvTestNameResult, tvValueResult, tvRangeResult, tvStatus;
    private LinearLayout layoutStatus;
    private ImageView ivStatusIcon;

    private String[] testNames = {"الهيموغلوبين (HB)", "السكر (Glucose)", "الكوليسترول (Cholesterol)"};
    private double[][] normalRanges = {
            {12.0, 16.0},
            {70.0, 100.0},
            {0.0, 200.0}
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        // إخفاء شريط العنوان الافتراضي
        if (getSupportActionBar() != null) getSupportActionBar().hide();

        // بناء الواجهة برمجياً
        ScrollView scrollView = new ScrollView(this);
        scrollView.setBackgroundColor(COLOR_BACKGROUND);
        scrollView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

        LinearLayout mainLayout = new LinearLayout(this);
        mainLayout.setOrientation(LinearLayout.VERTICAL);
        mainLayout.setPadding(40, 40, 40, 40);
        scrollView.addView(mainLayout);

        // --- الهيدر (Header) ---
        ImageView logo = new ImageView(this);
        logo.setImageResource(android.R.drawable.ic_menu_myplaces);
        logo.setColorFilter(COLOR_PRIMARY);
        LinearLayout.LayoutParams logoParams = new LinearLayout.LayoutParams(150, 150);
        logoParams.gravity = Gravity.CENTER_HORIZONTAL;
        logoParams.topMargin = 30;
        logo.setLayoutParams(logoParams);
        mainLayout.addView(logo);

        TextView title = new TextView(this);
        title.setText("Medix");
        title.setTextColor(COLOR_PRIMARY);
        title.setTextSize(32);
        title.setTypeface(null, Typeface.BOLD);
        title.setGravity(Gravity.CENTER);
        mainLayout.addView(title);

        TextView subtitle = new TextView(this);
        subtitle.setText("قارئ التحاليل الطبية الذكي");
        subtitle.setTextColor(COLOR_TEXT_LIGHT);
        subtitle.setTextSize(16);
        subtitle.setGravity(Gravity.CENTER);
        LinearLayout.LayoutParams subParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        subParams.gravity = Gravity.CENTER_HORIZONTAL;
        subParams.bottomMargin = 50;
        subtitle.setLayoutParams(subParams);
        mainLayout.addView(subtitle);

        // --- بطاقة الإدخال (Input Card) ---
        CardView inputCard = new CardView(this);
        LinearLayout.LayoutParams cardParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        cardParams.bottomMargin = 40;
        inputCard.setLayoutParams(cardParams);
        inputCard.setRadius(30);
        inputCard.setCardElevation(15);
        inputCard.setCardBackgroundColor(COLOR_WHITE);

        LinearLayout inputLayout = new LinearLayout(this);
        inputLayout.setOrientation(LinearLayout.VERTICAL);
        inputLayout.setPadding(40, 40, 40, 40);
        inputCard.addView(inputLayout);
        mainLayout.addView(inputCard);

        TextView inputTitle = new TextView(this);
        inputTitle.setText("بيانات التحليل");
        inputTitle.setTextSize(18);
        inputTitle.setTypeface(null, Typeface.BOLD);
        inputTitle.setTextColor(COLOR_TEXT_DARK);
        inputTitle.setPadding(0, 0, 0, 30);
        inputLayout.addView(inputTitle);

        // Spinner
        TextView labelSpinner = new TextView(this);
        labelSpinner.setText("نوع التحليل:");
        labelSpinner.setTextColor(COLOR_TEXT_LIGHT);
        inputLayout.addView(labelSpinner);

        spinnerTestType = new Spinner(this);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, testNames);
        spinnerTestType.setAdapter(adapter);
        LinearLayout.LayoutParams spinnerParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 120);
        spinnerParams.bottomMargin = 30;
        spinnerTestType.setLayoutParams(spinnerParams);
        spinnerTestType.setBackground(createRoundedBackground(Color.parseColor("#F0F2F5"), 20, Color.parseColor("#E0E0E0")));
        inputLayout.addView(spinnerTestType);

        // EditText
        TextView labelResult = new TextView(this);
        labelResult.setText("النتيجة:");
        labelResult.setTextColor(COLOR_TEXT_LIGHT);
        inputLayout.addView(labelResult);

        etResult = new EditText(this);
        etResult.setHint("أدخل القيمة هنا...");
        etResult.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
        etResult.setTextColor(COLOR_TEXT_DARK);
        etResult.setHintTextColor(COLOR_TEXT_LIGHT);
        LinearLayout.LayoutParams etParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 130);
        etParams.bottomMargin = 40;
        etResult.setLayoutParams(etParams);
        etResult.setPadding(30, 0, 30, 0);
        etResult.setBackground(createRoundedBackground(Color.parseColor("#F0F2F5"), 20, Color.parseColor("#E0E0E0")));
        inputLayout.addView(etResult);

        // Button
        Button btnAnalyze = new Button(this);
        btnAnalyze.setText("تحليل النتيجة");
        btnAnalyze.setTextSize(16);
        btnAnalyze.setTypeface(null, Typeface.BOLD);
        btnAnalyze.setTextColor(Color.WHITE);
        btnAnalyze.setBackground(createRoundedBackground(COLOR_PRIMARY, 20, COLOR_PRIMARY));
        LinearLayout.LayoutParams btnParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 140);
        btnAnalyze.setLayoutParams(btnParams);
        inputLayout.addView(btnAnalyze);

        // --- بطاقة النتيجة (Result Card) ---
        cardResult = new CardView(this);
        cardResult.setLayoutParams(cardParams);
        cardResult.setRadius(30);
        cardResult.setCardElevation(15);
        cardResult.setCardBackgroundColor(COLOR_WHITE);
        cardResult.setVisibility(View.GONE); // مخفية في البداية

        LinearLayout resultLayout = new LinearLayout(this);
        resultLayout.setOrientation(LinearLayout.VERTICAL);
        resultLayout.setPadding(40, 40, 40, 40);
        cardResult.addView(resultLayout);
        mainLayout.addView(cardResult);

        TextView resultTitle = new TextView(this);
        resultTitle.setText("تقرير التحليل الطبي");
        resultTitle.setTextSize(18);
        resultTitle.setTypeface(null, Typeface.BOLD);
        resultTitle.setTextColor(COLOR_PRIMARY);
        resultTitle.setPadding(0, 0, 0, 30);
        resultLayout.addView(resultTitle);

        View divider = new View(this);
        divider.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 2));
        divider.setBackgroundColor(Color.parseColor("#EEEEEE"));
        resultLayout.addView(divider);

        tvTestNameResult = createResultTextView();
        tvValueResult = createResultTextView();
        tvRangeResult = createResultTextView();
        tvRangeResult.setTextColor(COLOR_TEXT_LIGHT);
        tvRangeResult.setTextSize(14);

        resultLayout.addView(tvTestNameResult);
        resultLayout.addView(tvValueResult);
        resultLayout.addView(tvRangeResult);

        // Status Box
        layoutStatus = new LinearLayout(this);
        layoutStatus.setOrientation(LinearLayout.HORIZONTAL);
        layoutStatus.setPadding(30, 30, 30, 30);
        layoutStatus.setGravity(Gravity.CENTER_VERTICAL);
        LinearLayout.LayoutParams statusParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        statusParams.topMargin = 30;
        layoutStatus.setLayoutParams(statusParams);
        layoutStatus.setBackground(createRoundedBackground(COLOR_ERROR_BG, 20, COLOR_ERROR_BG));

        ivStatusIcon = new ImageView(this);
        ivStatusIcon.setImageResource(android.R.drawable.ic_dialog_alert);
        ivStatusIcon.setColorFilter(COLOR_ERROR);
        ivStatusIcon.setLayoutParams(new LinearLayout.LayoutParams(50, 50));
        layoutStatus.addView(ivStatusIcon);

        tvStatus = new TextView(this);
        tvStatus.setText("التصنيف: --");
        tvStatus.setTextColor(COLOR_ERROR);
        tvStatus.setTypeface(null, Typeface.BOLD);
        tvStatus.setPadding(20, 0, 0, 0);
        layoutStatus.addView(tvStatus);

        resultLayout.addView(layoutStatus);

        // Footer
        TextView footer = new TextView(this);
        footer.setText("تنبيه: النتائج إرشادية فقط، ويجب تفسيرها حسب مرجع المختبر وبواسطة مختص.");
        footer.setTextSize(12);
        footer.setTextColor(COLOR_TEXT_LIGHT);
        footer.setGravity(Gravity.CENTER);
        footer.setPadding(0, 40, 0, 0);
        mainLayout.addView(footer);

        setContentView(scrollView);

        // --- المنطق البرمجي (Logic) ---
        btnAnalyze.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                analyzeResult();
            }
        });
    }

    private void analyzeResult() {
        String inputStr = etResult.getText().toString().trim();

        if (inputStr.isEmpty()) {
            Toast.makeText(this, "الرجاء إدخال النتيجة أولاً", Toast.LENGTH_SHORT).show();
            return;
        }

        double value;
        try {
            value = Double.parseDouble(inputStr);
        } catch (NumberFormatException e) {
            Toast.makeText(this, "الرجاء إدخال رقم صحيح", Toast.LENGTH_SHORT).show();
            return;
        }

        int selectedIndex = spinnerTestType.getSelectedItemPosition();
        String selectedTest = testNames[selectedIndex];
        double min = normalRanges[selectedIndex][0];
        double max = normalRanges[selectedIndex][1];

        cardResult.setVisibility(View.VISIBLE);
        tvTestNameResult.setText("اسم الفحص: " + selectedTest);
        tvValueResult.setText("النتيجة: " + value);
        tvRangeResult.setText("المدى المرجعي: " + min + " - " + max);

        if (value < min) {
            updateStatus("التصنيف: أقل من المدى الطبيعي", COLOR_ERROR, COLOR_ERROR_BG, android.R.drawable.arrow_down_float);
        } else if (value > max) {
            updateStatus("التصنيف: أعلى من المدى الطبيعي", COLOR_ERROR, COLOR_ERROR_BG, android.R.drawable.ic_dialog_alert);
        } else {
            updateStatus("التصنيف: طبيعي", COLOR_SUCCESS, COLOR_SUCCESS_BG, android.R.drawable.checkbox_on_background);
        }
    }

    private void updateStatus(String text, int textColor, int bgColor, int iconRes) {
        tvStatus.setText(text);
        tvStatus.setTextColor(textColor);
        layoutStatus.setBackground(createRoundedBackground(bgColor, 20, bgColor));
        ivStatusIcon.setImageResource(iconRes);
        ivStatusIcon.setColorFilter(textColor);
    }

    // دالة مساعدة لإنشاء خلفية دائرية برمجياً
    private GradientDrawable createRoundedBackground(int color, int radius, int strokeColor) {
        GradientDrawable shape = new GradientDrawable();
        shape.setShape(GradientDrawable.RECTANGLE);
        shape.setColor(color);
        shape.setCornerRadius(radius);
        if (strokeColor != 0) {
            shape.setStroke(2, strokeColor);
        }
        return shape;
    }

    // دالة مساعدة لإنشاء نصوص النتائج
    private TextView createResultTextView() {
        TextView tv = new TextView(this);
        tv.setTextSize(16);
        tv.setTextColor(COLOR_TEXT_DARK);
        tv.setPadding(0, 10, 0, 10);
        return tv;
    }
}