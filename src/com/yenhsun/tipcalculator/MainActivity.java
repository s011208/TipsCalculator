
package com.yenhsun.tipcalculator;

import java.text.DecimalFormat;

import com.google.ads.AdRequest;
import com.google.ads.AdSize;
import com.google.ads.AdView;

import android.os.Bundle;
import android.app.Activity;
import android.content.SharedPreferences;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends Activity {

    private EditText mEditBA, mEditTP, mEditTA, mEditNoP, mEditEPC;

    private Button mBtnTPup, mBtnTPdown, mBtnTAup, mBtnTAdown, mBtnNoPUp, mBtnNoPdown;

    private Button m0, m1, m2, m3, m4, m5, m6, m7, m8, m9, mDot, mBack;

    private AdView adView;

    private SharedPreferences mSharf;

    private static final String SHAREF_FILE_NAME = "sharf_file";

    private static final String SHAREF_KEY_TP = "tp";

    private static final String SHAREF_KEY_NoP = "nop";

    private static final int DEFAULT_TP = 10;

    private static final int DEFAULT_NOP = 1;

    private static DecimalFormat FORMATTER = new DecimalFormat("#.##");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    private void init() {
        mSharf = getSharedPreferences(SHAREF_FILE_NAME, 0);

        mEditBA = (EditText)findViewById(R.id.edit_bill_amount);
//        mEditBA.setText(Integer.toString(100));

        mEditTP = (EditText)findViewById(R.id.edit_tip_percentage);
        mEditTP.setText(Float.toString(mSharf.getFloat(SHAREF_KEY_TP, DEFAULT_TP)));

        mEditTA = (EditText)findViewById(R.id.edit_tip_amount);
        mEditTA.setText(Float.toString(0));

        mEditNoP = (EditText)findViewById(R.id.edit_number_of_people);
        mEditNoP.setText(Float.toString(mSharf.getFloat(SHAREF_KEY_NoP, DEFAULT_NOP)));

        mEditEPC = (EditText)findViewById(R.id.edit_each_person_cost);
        mEditEPC.setText(Float.toString(0));
        addTextWatcher();

        mBtnTPup = (Button)findViewById(R.id.btn_tp_up);
        mBtnTPup.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                mEditTP.setText(changeValue(true, mEditTP));
            }
        });
        mBtnTPdown = (Button)findViewById(R.id.btn_tp_down);
        mBtnTPdown.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                mEditTP.setText(changeValue(false, mEditTP));
            }
        });
        mBtnTAup = (Button)findViewById(R.id.btn_ta_up);
        mBtnTAup.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                mEditTA.setText(changeValue(true, mEditTA));
            }
        });
        mBtnTAdown = (Button)findViewById(R.id.btn_ta_down);
        mBtnTAdown.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                mEditTA.setText(changeValue(false, mEditTA));
            }
        });
        mBtnNoPUp = (Button)findViewById(R.id.btn_nop_up);
        mBtnNoPUp.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                mEditNoP.setText(changeValue(true, mEditNoP));
            }
        });
        mBtnNoPdown = (Button)findViewById(R.id.btn_nop_down);
        mBtnNoPdown.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                mEditNoP.setText(changeValue(false, mEditNoP));
            }
        });
        initCalculator();
        initAdView();
    }

    private void initCalculator() {
        m0 = (Button)findViewById(R.id.calculat_btn_0);
        m1 = (Button)findViewById(R.id.calculat_btn_1);
        m2 = (Button)findViewById(R.id.calculat_btn_2);
        m3 = (Button)findViewById(R.id.calculat_btn_3);
        m4 = (Button)findViewById(R.id.calculat_btn_4);
        m5 = (Button)findViewById(R.id.calculat_btn_5);
        m6 = (Button)findViewById(R.id.calculat_btn_6);
        m7 = (Button)findViewById(R.id.calculat_btn_7);
        m8 = (Button)findViewById(R.id.calculat_btn_8);
        m9 = (Button)findViewById(R.id.calculat_btn_9);
        mDot = (Button)findViewById(R.id.calculat_btn_dot);
        mBack = (Button)findViewById(R.id.calculat_btn_back);
        m0.setOnClickListener(mCalculatorOnClickListener);
        m1.setOnClickListener(mCalculatorOnClickListener);
        m2.setOnClickListener(mCalculatorOnClickListener);
        m3.setOnClickListener(mCalculatorOnClickListener);
        m4.setOnClickListener(mCalculatorOnClickListener);
        m5.setOnClickListener(mCalculatorOnClickListener);
        m6.setOnClickListener(mCalculatorOnClickListener);
        m7.setOnClickListener(mCalculatorOnClickListener);
        m8.setOnClickListener(mCalculatorOnClickListener);
        m9.setOnClickListener(mCalculatorOnClickListener);
        mDot.setOnClickListener(mCalculatorOnClickListener);
        mBack.setOnClickListener(mCalculatorOnClickListener);
    }

    private void initAdView() {
        adView = new AdView(this, AdSize.BANNER, "a152d3b8966b65b");

        LinearLayout layout = (LinearLayout)findViewById(R.id.adview);

        layout.addView(adView);
        new Thread(new Runnable() {

            @Override
            public void run() {
                while (true) {
                    // TODO Auto-generated method stub
                    MainActivity.this.runOnUiThread(new Runnable() {

                        @Override
                        public void run() {
                            // TODO Auto-generated method stub
                            adView.loadAd(new AdRequest());
                        }
                    });

                    try {
                        Thread.sleep(6000);
                    } catch (InterruptedException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
            }
        }).start();

    }

    private OnClickListener mCalculatorOnClickListener = new OnClickListener() {

        @Override
        public void onClick(View v) {
            int id = v.getId();
            String baTxt = mEditBA.getText().toString();
            switch (id) {
                case R.id.calculat_btn_0:
                    mEditBA.setText(baTxt + "0");
                    break;
                case R.id.calculat_btn_1:
                    mEditBA.setText(baTxt + "1");
                    break;
                case R.id.calculat_btn_2:
                    mEditBA.setText(baTxt + "2");
                    break;
                case R.id.calculat_btn_3:
                    mEditBA.setText(baTxt + "3");
                    break;
                case R.id.calculat_btn_4:
                    mEditBA.setText(baTxt + "4");
                    break;
                case R.id.calculat_btn_5:
                    mEditBA.setText(baTxt + "5");
                    break;
                case R.id.calculat_btn_6:
                    mEditBA.setText(baTxt + "6");
                    break;
                case R.id.calculat_btn_7:
                    mEditBA.setText(baTxt + "7");
                    break;
                case R.id.calculat_btn_8:
                    mEditBA.setText(baTxt + "8");
                    break;
                case R.id.calculat_btn_9:
                    mEditBA.setText(baTxt + "9");
                    break;
                case R.id.calculat_btn_dot:
                    mEditBA.setText(baTxt + ".");
                    break;
                case R.id.calculat_btn_back:
                    if (baTxt.length() > 0)
                        mEditBA.setText(baTxt.substring(0, baTxt.length() - 1));
                    break;
            }
        }
    };

    private String changeValue(boolean add, EditText edit) {
        String rtn = "0";
        float v = convertToFloat(edit);
        if (add)
            ++v;
        else
            --v;
        if (v >= 0)
            rtn = getDisplayString(v);
        else
            rtn = "0";
        return rtn;
    }

    private void saveTP(float value) {
        mSharf.edit().putFloat(SHAREF_KEY_TP, value).commit();
    }

    private void saveNoP(float value) {
        mSharf.edit().putFloat(SHAREF_KEY_NoP, value).commit();
    }

    private TextWatcher TBA = new TextWatcher() {

        @Override
        public void afterTextChanged(Editable s) {
            // TODO Auto-generated method stub
            calculateByBA();
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            // TODO Auto-generated method stub

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            // TODO Auto-generated method stub

        }
    };

    private TextWatcher TTA = new TextWatcher() {

        @Override
        public void afterTextChanged(Editable s) {
            // TODO Auto-generated method stub
            calculateByTA();
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            // TODO Auto-generated method stub

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            // TODO Auto-generated method stub

        }
    };

    private TextWatcher TTP = new TextWatcher() {

        @Override
        public void afterTextChanged(Editable s) {
            // TODO Auto-generated method stub
            calculateByTP();
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            // TODO Auto-generated method stub

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            // TODO Auto-generated method stub

        }
    };

    private TextWatcher TNoP = new TextWatcher() {

        @Override
        public void afterTextChanged(Editable s) {
            // TODO Auto-generated method stub
            calculateByNOP();
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            // TODO Auto-generated method stub

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            // TODO Auto-generated method stub

        }
    };

    private void addTextWatcher() {
        mEditBA.addTextChangedListener(TBA);
        mEditTP.addTextChangedListener(TTP);
        mEditTA.addTextChangedListener(TTA);
        mEditNoP.addTextChangedListener(TNoP);
    }

    private void removeTextWatcher() {
        mEditBA.removeTextChangedListener(TBA);
        mEditTP.removeTextChangedListener(TTP);
        mEditTA.removeTextChangedListener(TTA);
        mEditNoP.removeTextChangedListener(TNoP);
    }

    private static float convertToFloat(EditText ed) {
        float rtn = 0;
        try {
            rtn = Float.valueOf(ed.getText().toString());
        } catch (Exception e) {
        }
        return rtn;
    }

    private void calculateByBA() {
        removeTextWatcher();
        float BA = convertToFloat(mEditBA);
        float TP = convertToFloat(mEditTP) / 100;
        float TA = BA * TP;
        float NoP = convertToFloat(mEditNoP);
        float EPC = (BA + TA) / NoP;
        // mEditBA.setText(Float.toString(BA));
        // mEditTP.setText(Float.toString(TP));
        mEditTA.setText(getDisplayString(TA));
        // mEditNoP.setText(Float.toString(NoP));
        mEditEPC.setText(getDisplayString(EPC));
        addTextWatcher();
    }

    private void calculateByTP() {
        removeTextWatcher();
        float BA = convertToFloat(mEditBA);
        float TP = convertToFloat(mEditTP);
        float TA = (TP / 100) * BA;
        float NoP = convertToFloat(mEditNoP);
        float EPC = (BA + TA) / NoP;
        saveTP(TP);
        // mEditBA.setText(Float.toString(BA));
        // mEditTP.setText(Float.toString(TP));
        mEditTA.setText(getDisplayString(TA));
        // mEditNoP.setText(Float.toString(NoP));
        mEditEPC.setText(getDisplayString(EPC));
        addTextWatcher();
    }

    private void calculateByNOP() {
        removeTextWatcher();
        float BA = convertToFloat(mEditBA);
        float TA = convertToFloat(mEditTA);
        // float TP = (TA / BA) * 100;
        float NoP = convertToFloat(mEditNoP);
        float EPC = (BA + TA) / NoP;
        saveNoP(NoP);
        // mEditBA.setText(Float.toString(BA));
        // mEditTP.setText(Float.toString(TP));
        // mEditTA.setText(Float.toString(TA));
        // mEditNoP.setText(Float.toString(NoP));
        mEditEPC.setText(getDisplayString(EPC));
        addTextWatcher();
    }

    private void calculateByTA() {
        removeTextWatcher();
        float BA = convertToFloat(mEditBA);
        float TA = convertToFloat(mEditTA);
        float TP = (TA / BA) * 100;
        float NoP = convertToFloat(mEditNoP);
        float EPC = (BA + TA) / NoP;
        // mEditBA.setText(Float.toString(BA));
        mEditTP.setText(Float.toString(TP));
        // mEditTA.setText(Float.toString(TA));
        // mEditNoP.setText(Float.toString(NoP));
        mEditEPC.setText(getDisplayString(EPC));
        addTextWatcher();
    }

    private static final String getDisplayString(float f) {
        return FORMATTER.format(f);
    }
}
