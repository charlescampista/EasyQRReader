package charlescampista.easyqrreader;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import charlescampista.classes.QRManagement;

public class HomeScreenActivity extends AppCompatActivity {

    QRManagement qrManagement = null;
    private Activity activity;
    private String readedData;
    Intent intentWebClientActivity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        qrManagement = new QRManagement();
        activity = this;
        intentWebClientActivity = new Intent(this, WebClientActivity.class);




    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        IntentResult result = IntentIntegrator.parseActivityResult(requestCode,resultCode,data);
        if(result != null){
            if(result.getContents() == null){
                //The scanning hasn't worked!
                Toast.makeText(this, "Something goes wrong the result is empty",Toast.LENGTH_LONG).show();
            } else{
                //The scannig has worked successfully!
                readedData = result.getContents();
                if(readedData != null) {
                    //Toast.makeText(this,readedData,Toast.LENGTH_LONG).show();
                    goToWebClientActivity();
                }
            }
        } else{
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    public void scanCode(View view){
        qrManagement.readCode(activity);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_home_screen, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.exit) {
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void goToWebClientActivity(){
        prepareParametersWebClientActivity(readedData);
        startActivity(intentWebClientActivity);
    }


    public void prepareParametersWebClientActivity(String readedData) {
        Bundle bundle = new Bundle();
        bundle.putString("readedData", readedData);
        intentWebClientActivity.putExtras(bundle);
    }
}
