package uk.co.tezk.login;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import io.realm.Realm;
import io.realm.exceptions.RealmMigrationNeededException;

import static uk.co.tezk.login.Constants.KEY_EMAIL;
import static uk.co.tezk.login.Constants.KEY_MANAGER;
import static uk.co.tezk.login.Constants.KEY_PASSWORD;

public class MainActivity extends AppCompatActivity {
    private TextView tvEmail;
    private TextView tvPassword;
    private CheckBox cbRemember;
    private Spinner spUserType;

    private Realm realm;

    private boolean isManagerSelected() {
        return spUserType.getSelectedItem().toString().equals(getString(R.string.manager));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvEmail = (TextView) findViewById(R.id.emailEditText);
        tvPassword = (TextView) findViewById(R.id.passwordEditText);
        cbRemember = (CheckBox) findViewById(R.id.rememberCheckBox);
        spUserType = (Spinner) findViewById(R.id.userTypeSpinner);

        try {
            realm = Realm.getDefaultInstance();
        } catch (RealmMigrationNeededException e) {
            Log.w("login", "failed to init Realm : "+e.getMessage());

            Toast toast = Toast.makeText(getApplicationContext(), "Error initialising Realm!", Toast.LENGTH_LONG);
            toast.show();
            try {
                Thread.sleep(5000);
            } catch (InterruptedException se) {
                se.printStackTrace();
            }
            finish();
        }

        SharedPreferences prefs = getPreferences(MODE_PRIVATE);

        String anEmail = prefs.getString(KEY_EMAIL, null);
        String aPassword = prefs.getString(KEY_PASSWORD, "");
        Boolean isManager = prefs.getBoolean(KEY_MANAGER, false);
        if (anEmail!=null && anEmail.length()>0) {
            // Pre-set
            tvEmail.setText(anEmail);
            tvPassword.setText(aPassword);
            int index = ((ArrayAdapter)spUserType.getAdapter()).getPosition(getText(R.string.manager));
            if (!isManager) {
                index = ((ArrayAdapter)spUserType.getAdapter()).getPosition(getText(R.string.user));
            }
            spUserType.setSelection(index);

            // sharedPreferences were stored, so we can assume "Remember me" was checked
            cbRemember.setChecked(true);
        }
    }

    public void handleSignUpButton(View v) {
        Intent mI = null;

        if (isManagerSelected()) {
            mI = new Intent(this, UserDisplayActivity.class);
        } else {
            mI = new Intent(this, AddUserActivity.class);
        }

        SharedPreferences prefs = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        if (!cbRemember.isChecked()) {
            //Log.i("login", "Do not remember");
            editor.clear();
        } else {
            //Log.i("login", "Remember");
            editor.putString(KEY_EMAIL, tvEmail.getText().toString());
            editor.putString(KEY_PASSWORD, tvPassword.getText().toString());
            editor.putBoolean(KEY_MANAGER, isManagerSelected());
        }
        editor.commit();

        mI.putExtra(KEY_EMAIL, tvEmail.getText().toString());

        startActivity(mI);
        finish();
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i("mylogin", "onPause");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i("mylogin", "onResume");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i("mylogin", "onStop");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i("mylogin", "onStart");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (realm!=null)
            realm.close();
    }
}