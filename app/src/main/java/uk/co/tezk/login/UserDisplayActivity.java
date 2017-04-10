package uk.co.tezk.login;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmCollection;

import static uk.co.tezk.login.Constants.KEY_EMAIL;

public class UserDisplayActivity extends AppCompatActivity {

    ListView lvUsers;
    Realm realm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_display);

        lvUsers = (ListView)findViewById(R.id.view_user_listview);

        realm = Realm.getDefaultInstance();

        RealmCollection <UserRealm> users = realm.where(UserRealm.class).findAll();

        ArrayList<String> userArray = new ArrayList();
        for (UserRealm eachUser : users) {
            userArray.add(eachUser.getName());
        }

        ListAdapter adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,userArray);

        lvUsers.setAdapter(adapter);

        getSupportActionBar().setTitle("Welcome "+getIntent().getExtras().getString(KEY_EMAIL));
    }
}
