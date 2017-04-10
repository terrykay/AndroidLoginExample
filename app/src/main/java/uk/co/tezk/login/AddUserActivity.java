package uk.co.tezk.login;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.icu.util.Calendar;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.codetroopers.betterpickers.calendardatepicker.CalendarDatePickerDialogFragment;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

import io.realm.Realm;

import static uk.co.tezk.login.Constants.*;


public class AddUserActivity extends AppCompatActivity {



    private ImageView ivUser;
    private EditText etName;
    private EditText etNiNo;
    private EditText etPassport;
    private EditText etPassword;
    private EditText etConfirmPassword;
    private RadioButton rbMale;
    private RadioButton rbFemale;
    private Spinner spCountry;
    private Button btnDob;

    private int mDobYear;
    private int mDobMonthOfYear;
    private int mDobDayOfMonth;

    CalendarDatePickerDialogFragment cdpFragment;
    private Drawable mDefaultUserImage;
    private String mUserImageUrl;

    private Realm realm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_user);

        ivUser = (ImageView)findViewById(R.id.add_imageview);
        mDefaultUserImage = ivUser.getDrawable();
        Log.i("login","user image : "+mDefaultUserImage);
        etName = (EditText)findViewById(R.id.add_name_edittext);
        etNiNo = (EditText)findViewById(R.id.add_national_insurance_edittext);
        etPassport = (EditText)findViewById(R.id.add_passport_edittext);
        etPassword = (EditText)findViewById(R.id.add_password_edittext);
        etConfirmPassword = (EditText)findViewById(R.id.add_confirm_password_edittext);
        rbMale = (RadioButton)findViewById(R.id.add_male_radiobutton);
        rbFemale = (RadioButton)findViewById(R.id.add_female_radiobutton);
        spCountry = (Spinner)findViewById(R.id.add_country_spinner);
        btnDob = (Button)findViewById(R.id.add_dob_button);

        // Setup Calendar button
        cdpFragment = new CalendarDatePickerDialogFragment()
                .setFirstDayOfWeek(Calendar.SUNDAY)
                .setThemeDark();
        cdpFragment.setOnDateSetListener(new CalendarDatePickerDialogFragment.OnDateSetListener() {
                                     @Override
                                     public void onDateSet(CalendarDatePickerDialogFragment dialog, int year, int monthOfYear, int dayOfMonth) {
                                         btnDob.setText(getString(R.string.calendar_date_picker_result_values, year, monthOfYear + 1, dayOfMonth));
                                         mDobYear = year;
                                         mDobMonthOfYear = monthOfYear;
                                         mDobDayOfMonth = dayOfMonth;
                                     }
        });

        // Setup imageView to handle clicks
        ivUser.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_PICK, Uri.parse("content://media/internal/images/media"));
                startActivityForResult(i, RESULT_IMAGE_PICKED);
                }
        });

        setCountries();

        getSupportActionBar().setTitle("Please enter details "+getIntent().getExtras().getString(KEY_EMAIL));

        if (savedInstanceState!=null) {
            UserParcel userParcel = savedInstanceState.getParcelable(KEY_USER_PARCEL);
            if (userParcel != null) {
                // onCreate has been called with a parcel created in onSavedInstanceState, restore values
                setValues(userParcel);
            }
        }
   }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // How to read Images taken from http://programmerguru.com/android-tutorial/how-to-pick-image-from-gallery/
        if (requestCode == RESULT_IMAGE_PICKED && resultCode == RESULT_OK && data != null) {
            Uri selectedImage = data.getData();

            ivUser.setImageURI(selectedImage);
            mUserImageUrl = selectedImage.toString();
        }
    }

    private void setCountries() {
        // Read in countries from Locale, add if not already in, then sort and use to populate Country spinner
        Locale locales[] = Locale.getAvailableLocales();
        List<String> mCountries = new ArrayList<String>();
        for (Locale eachLocale : locales) {
            if (eachLocale.getDisplayCountry().length()>1)
                if (!mCountries.contains(eachLocale.getDisplayCountry()))
                    mCountries.add(eachLocale.getDisplayCountry());
        }
        Collections.sort(mCountries);
        ArrayAdapter <String>countryArrayAdapter = new ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, mCountries);
        spCountry.setAdapter(countryArrayAdapter);
    }

    private void clearFields() {
        ivUser.setImageDrawable(mDefaultUserImage);
        etName.setText("");
        etNiNo.setText("");
        etPassport.setText("");
        etPassword.setText("");
        etConfirmPassword.setText("");
        rbMale.setChecked(false);
        rbFemale.setChecked(false);
        spCountry.setSelection(0);
        btnDob.setText(getString(R.string.dob_button));
        mUserImageUrl = null;

        etName.requestFocus();
    }

    public void handleDobButton(View v) {
        // Show the Date better picker fragment, values entered are saved in the picker handler
        cdpFragment.show(getSupportFragmentManager(), FRAG_TAG_DATE_PICKER);
    }

    public void handleCreateButton(View v) {
        if (!etPassword.getText().toString().equals(etConfirmPassword.getText().toString())) {
            Toast toast = Toast.makeText(this, getString(R.string.passwords_dont_match_message), Toast.LENGTH_LONG);
            toast.show();
            return;
        }
        if (persistUser())
            clearFields();
    }

    private boolean persistUser() {
        final UserRealm newUser = new UserRealm();
        newUser.setName(etName.getText().toString());
        newUser.setNiNo(etNiNo.getText().toString());
        newUser.setPassport(etPassport.getText().toString());
        newUser.setPassword(etPassword.getText().toString());
        newUser.setCountry(spCountry.getSelectedItem().toString());
        newUser.setGenderMale(rbMale.isChecked());
        newUser.setYearOfDob(mDobYear);
        newUser.setMonthOfDob(mDobMonthOfYear);
        newUser.setYearOfDob(mDobYear);
        newUser.setImageUrl(mUserImageUrl);

        if (realm == null)
            realm = Realm.getDefaultInstance();

        if (realm.where(UserRealm.class).equalTo("niNo", newUser.getNiNo()).count() > 0) {
            Toast toast = Toast.makeText(this, getString(R.string.user_exists_message), Toast.LENGTH_LONG);
            toast.show();
            return false;
        }

        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.copyToRealm(newUser);
            }
        });

        Toast toast = Toast.makeText(this, getString(R.string.user_saved_message), Toast.LENGTH_LONG);
        toast.show();
        return true;
    }

    public void handleCancelButton(View v) {
        // Clear settings
        clearFields();
    }

//    public UserParcel(String name, String niNo, String passport, String country, String password, String confirmPassword, Boolean male, int yearDob, int monthDob, int dayDob) {


        @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        UserParcel userParcel = new UserParcel(
                etName.getText().toString(),
                etNiNo.getText().toString(),
                etPassport.getText().toString(),
                spCountry.getSelectedItem().toString(),
                etPassword.getText().toString(),
                etConfirmPassword.getText().toString(),
                rbMale.isChecked(),
                mDobYear,
                mDobMonthOfYear,
                mDobYear,
                mUserImageUrl
        );
        outState.putParcelable(KEY_USER_PARCEL, userParcel);
    }

    private void setValues(UserParcel userParcel) {
        etName.setText(userParcel.getName());
        etNiNo.setText(userParcel.getNiNo());
        etPassport.setText(userParcel.getPassport());
        etPassword.setText(userParcel.getPassword());
        etConfirmPassword.setText(userParcel.getConfirmPassword());
        rbMale.setChecked(userParcel.getMale());
        rbFemale.setChecked(!userParcel.getMale());
        mDobYear = userParcel.getYearDob();
        mDobMonthOfYear = userParcel.getMonthDob();
        mDobDayOfMonth = userParcel.getDayDob();
        btnDob.setText(getString(R.string.calendar_date_picker_result_values, mDobYear, mDobMonthOfYear + 1, mDobDayOfMonth));
    }
}
