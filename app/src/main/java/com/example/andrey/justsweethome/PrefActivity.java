package com.example.andrey.justsweethome;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.preference.PreferenceFragment;

public class PrefActivity extends PreferenceActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB) {
            onCreatePreferenceActivity();
        } else {
            onCreatePreferenceFragment();
        }

    }

    /**
     * code for Android < 3 (i.e. API lvl < 11)
     */
    @SuppressWarnings("deprecation")
    private void onCreatePreferenceActivity() {
        addPreferencesFromResource(R.xml.pref);
    }

    /**
     * code for Android >= 3 (i.e. API lvl >= 11)
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    private void onCreatePreferenceFragment() {
        getFragmentManager().beginTransaction()
                .replace(android.R.id.content, new SettingsFr())
                .commit();
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public static class SettingsFr extends PreferenceFragment
    {
        @Override
        public void onCreate(final Bundle savedInstanceState)
        {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.pref);
        }
    }

}
