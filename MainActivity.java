package com.corolla.smartcontrol;

import android.app.Activity;
import android.os.Bundle;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
import android.app.AlertDialog;

public class MainActivity extends Activity {
    private int bg = Color.rgb(13, 27, 42);
    private int panel = Color.rgb(28, 47, 65);
    private int accent = Color.rgb(90, 190, 230);
    private TextView status;

    @Override public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        buildUi();
    }

    private int dp(float v) {
        return (int)(v * getResources().getDisplayMetrics().density + 0.5f);
    }

    private TextView text(String s, int size, int color, boolean bold) {
        TextView t = new TextView(this);
        t.setText(s);
        t.setTextSize(size);
        t.setTextColor(color);
        if (bold) t.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
        return t;
    }

    private void buildUi() {
        ScrollView scroll = new ScrollView(this);
        scroll.setBackgroundColor(bg);
        LinearLayout root = new LinearLayout(this);
        root.setOrientation(LinearLayout.VERTICAL);
        root.setPadding(dp(20), dp(26), dp(20), dp(24));
        scroll.addView(root);

        TextView title = text("🚘 Corolla Smart Control", 25, Color.WHITE, true);
        root.addView(title);
        TextView subtitle = text("Toyota Corolla 2017 • Key ignition • Automatic", 14,
                Color.rgb(190, 210, 225), false);
        LinearLayout.LayoutParams subP = new LinearLayout.LayoutParams(-1, -2);
        subP.topMargin = dp(7);
        root.addView(subtitle, subP);

        status = text("DEMO MODE • Car hardware not connected", 13,
                Color.rgb(255, 210, 120), true);
        status.setPadding(dp(12), dp(12), dp(12), dp(12));
        status.setBackgroundColor(panel);
        LinearLayout.LayoutParams statP = new LinearLayout.LayoutParams(-1, -2);
        statP.topMargin = dp(20);
        statP.bottomMargin = dp(18);
        root.addView(status, statP);

        TextView info = text("Choose a feature", 18, Color.WHITE, true);
        root.addView(info);
        String[][] items = {
                {"❄️  AC Control", "AC ON / OFF"},
                {"💡  Lights", "Lights ON / OFF"},
                {"🔒  Door Locks", "Lock / Unlock"},
                {"🔑  Engine", "Remote start / stop"},
                {"📢  Horn", "Sound horn"},
                {"🪟  Windows", "Open / Close"}
        };

        for (String[] item : items) {
            LinearLayout card = new LinearLayout(this);
            card.setOrientation(LinearLayout.VERTICAL);
            card.setPadding(dp(15), dp(14), dp(15), dp(14));
            card.setBackgroundColor(panel);
            LinearLayout.LayoutParams cp = new LinearLayout.LayoutParams(-1, -2);
            cp.topMargin = dp(10);
            root.addView(card, cp);

            TextView name = text(item[0], 18, Color.WHITE, true);
            card.addView(name);
            TextView desc = text(item[1], 13, Color.rgb(190, 210, 225), false);
            LinearLayout.LayoutParams dp2 = new LinearLayout.LayoutParams(-1, -2);
            dp2.topMargin = dp(5);
            card.addView(desc, dp2);

            Button button = new Button(this);
            button.setText(item[0].contains("Engine") ? "REMOTE START (DEMO)" :
                    item[0].contains("Door") ? "LOCK / UNLOCK (DEMO)" :
                    item[0].contains("Windows") ? "OPEN / CLOSE (DEMO)" :
                    item[0].contains("Horn") ? "HORN (DEMO)" :
                    item[0].contains("Lights") ? "TOGGLE LIGHTS (DEMO)" : "TOGGLE AC (DEMO)");
            button.setAllCaps(false);
            button.setTextColor(Color.WHITE);
            button.setBackgroundTintList(android.content.res.ColorStateList.valueOf(accent));
            LinearLayout.LayoutParams bp = new LinearLayout.LayoutParams(-1, -2);
            bp.topMargin = dp(10);
            card.addView(button, bp);
            button.setOnClickListener(v -> showDemoNotice(item[0]));
        }

        TextView footer = text(
                "Important: This APK is a visual demo only. It does not connect to your car or operate AC, engine, locks, lights, horn, or windows. Vehicle-specific compatible hardware and secure integration are required.",
                13, Color.rgb(210, 220, 230), false);
        footer.setPadding(dp(2), dp(20), dp(2), dp(10));
        root.addView(footer);

        setContentView(scroll);
    }

    private void showDemoNotice(String feature) {
        new AlertDialog.Builder(this)
                .setTitle(feature)
                .setMessage("Demo button only — no command was sent to the car. Real control needs compatible vehicle hardware and integration.")
                .setPositiveButton("OK", (d, w) -> {})
                .show();
    }
}
