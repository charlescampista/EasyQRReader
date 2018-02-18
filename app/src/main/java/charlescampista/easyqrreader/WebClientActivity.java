package charlescampista.easyqrreader;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class WebClientActivity extends AppCompatActivity {

    private WebView wvWebClient;
    private String url;
    // This Inner class is used to make the page be opened inside our aplication as a client browser
    private class AppWebClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            return false;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_client);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        receiveParameters();
        wvWebClient = (WebView) findViewById(R.id.wvWebClient);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();*/

                openBrowserOutside(url);
                finish();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        wvWebClient.getSettings().setJavaScriptEnabled(true);
        wvWebClient.getSettings().setLoadWithOverviewMode(true);
        wvWebClient.getSettings().setUseWideViewPort(true);
        wvWebClient.getSettings().setBuiltInZoomControls(false);
        wvWebClient.getSettings().setPluginState(WebSettings.PluginState.ON);
        wvWebClient.setWebViewClient(new AppWebClient());
        wvWebClient.loadUrl(url);
    }

    public void receiveParameters(){
        Intent intent = getIntent();
        if(intent != null){
            Bundle params = intent.getExtras();
            if(params != null){
                url = params.getString("readedData");
            }
        }
    }

    public void openBrowserOutside(String url) {
        Intent i = new Intent(Intent.ACTION_VIEW,
                Uri.parse(url));
        startActivity(i);
    }


}
