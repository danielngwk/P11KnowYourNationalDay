package p11.android.myapplicationdev.com.p11_knowyournationalday;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ListView lv;
    ArrayList<String> aLs = new ArrayList<String>();
    ArrayAdapter<String> aa;

    SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lv = (ListView) findViewById(R.id.lv);
        aLs.add("Singapore National Day is on 9 Aug");
        aLs.add("Singapore is 53 years old");
        String theme = "We are Singapore";
        aLs.add("Theme is " + theme);
        aa = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, aLs);
        lv.setAdapter(aa);

    }

    @Override
    protected void onResume() {
        super.onResume();


        prefs = PreferenceManager.getDefaultSharedPreferences(this);
        Boolean isCode = prefs.getBoolean("isCode", false);

        if (isCode == true) {

        } else {

            LayoutInflater inflater = (LayoutInflater)
                    getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            LinearLayout passPhrase =
                    (LinearLayout) inflater.inflate(R.layout.passphrase, null);
            final EditText etCode = (EditText) passPhrase
                    .findViewById(R.id.editTextPassPhrase);

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Please Enter")
                    .setView(passPhrase)
                    .setNegativeButton("No Access code", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finish();

                        }
                    })
                    .setPositiveButton("Done", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int id) {

                            if (etCode.getText().toString().trim().equalsIgnoreCase("738964")) {
                                prefs = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
                                SharedPreferences.Editor prefEdit = prefs.edit();
                                prefEdit.putBoolean("isCode", true);
                                prefEdit.commit();


                            } else {
                                finish();
                            }
                        }
                    });
            AlertDialog alertDialog = builder.create();
            alertDialog.setCancelable(false);

            alertDialog.show();
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.send) {


            final String[] list = new String[]{"Email", "SMS"};

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Select the way to enrich your friend")
                    .setItems(list, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            if (which == 0) {
                                String msg = "";
                                for (int i = 0; i < aLs.size(); i++) {
                                    msg += aLs.get(i) + "\n";
                                }
                                Intent email = new Intent(Intent.ACTION_SEND);
                                email.putExtra(Intent.EXTRA_EMAIL,
                                        new String[]{"jason_lim@rp.edu.sg"});
                                email.putExtra(Intent.EXTRA_SUBJECT,
                                        "Know Your National Day");
                                email.putExtra(Intent.EXTRA_TEXT,
                                        msg);

                                email.setType("message/rfc822");

                                startActivity(Intent.createChooser(email,
                                        "Choose an Email client :"));
                                Snackbar.make(getWindow().getDecorView().getRootView(), "Email has been send", Snackbar.LENGTH_SHORT).show();

                            } else {
                                String msg = "";
                                for (int i = 0; i < aLs.size(); i++) {
                                    msg += aLs.get(i) + "\n";
                                }

                                Uri uri = Uri.parse("smsto:" + null);
                                Intent intent = new Intent(Intent.ACTION_SENDTO, uri);
                                intent.putExtra("sms_body", msg);
                                startActivity(intent);
                                Snackbar.make(getWindow().getDecorView().getRootView(), "SMS has been send", Snackbar.LENGTH_SHORT).show();
                            }
                        }
                    });
            AlertDialog alertDialog = builder.create();
            alertDialog.show();

        } else if (item.getItemId() == R.id.quiz) {
        } else if (item.getItemId() == R.id.quit)

        {

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Quit?")
                    .setPositiveButton("QUIT", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            finish();
                        }
                    })
                    .setNegativeButton("NOT REALLY", null);
            AlertDialog alertDialog = builder.create();
            alertDialog.show();


        }
        return super.

                onOptionsItemSelected(item);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.option, menu);
        return true;
    }

    @Override
    protected void onPause() {
        super.onPause();

        prefs = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor prefEdit = prefs.edit();
        prefEdit.putBoolean("isCode", true);
        prefEdit.commit();


    }
}
