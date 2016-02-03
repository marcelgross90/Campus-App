package de.fhws.campusapp.fragment;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import de.fhws.campusapp.R;


public class WebViewFragment extends Fragment {

    private static final String TITLE = "title";
    private static final String URL = "url";

    private View loadingView;
    private WebView webView;
    private ProgressBar progressBar;
    private Bundle webViewBundle;

    public static WebViewFragment newInstance( String title, String url ) {
        Bundle args = new Bundle();
        args.putString( TITLE, title );
        args.putString( URL, url );

        WebViewFragment fragment = new WebViewFragment();
        fragment.setArguments( args );

        return fragment;
    }

    @Override
    public void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
        setRetainInstance( true );
    }

    @SuppressLint( "SetJavaScriptEnabled" )
    @Override
    public View onCreateView( LayoutInflater inflater, ViewGroup container,
                              Bundle savedInstanceState ) {
        Activity activity;
        View view;

        if( (activity = getActivity()) == null ||
                (view = inflater.inflate(
                        R.layout.fragment_web_view, container, false )) == null ||
                (webView = (WebView) view.findViewById( R.id.web_view )) == null ||
                (loadingView = view.findViewById( R.id.loading )) == null  ||
                (progressBar = (ProgressBar)view.findViewById( R.id.progressBar ))  == null )
            return null;

        activity.setTitle( getArguments().getString( TITLE ) );

        progressBar.getIndeterminateDrawable().setColorFilter( ContextCompat.getColor( getActivity(), R.color.colorPrimary ), android.graphics.PorterDuff.Mode.MULTIPLY );


        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled( true );
        webView.setOnKeyListener( new View.OnKeyListener() {
            public boolean onKey( View v, int keyCode, KeyEvent event ) {
                if( (keyCode == KeyEvent.KEYCODE_BACK) && webView.canGoBack() ) {
                    webView.goBack();
                    return true;
                }
                return false;
            }
        } );

        webView.setWebViewClient( new CampusAppWebViewClient() );

        String url = getArguments().getString( URL );

        if( url != null && webViewBundle == null)
            webView.loadUrl( url );
        else
            webView.restoreState( webViewBundle );

        return view;
    }

    @Override
    public void onPause() {
        super.onPause();
        webViewBundle = new Bundle();
        webView.saveState( webViewBundle );
    }

    private class CampusAppWebViewClient extends WebViewClient {

        @Override
        public void onPageStarted( WebView view, String url, Bitmap favicon ) {
            loadingView.setVisibility( View.VISIBLE );
        }

        @Override
        public void onPageFinished( WebView view, String url ) {
            loadingView.setVisibility( View.GONE );
        }
    }
}
