package design.wendreo.hashisushi.views;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

import design.wendreo.hashisushi.R;

public class ActSplash extends AppCompatActivity {
	
	private TextView txtDelivery;
	private ImageView imgLogoS;
	
	
	private FirebaseAuth auth;
	
	//test coctividade
	public static boolean isOnline ( Context context ) {
		ConnectivityManager cm = ( ConnectivityManager ) context.getSystemService ( Context.CONNECTIVITY_SERVICE );
		NetworkInfo netInfo = cm.getActiveNetworkInfo ( );
		
		if ( netInfo != null && netInfo.isConnected ( ) )
			return true;
		else
			return false;
	}
	
	@Override
	protected void onCreate ( Bundle savedInstanceState ) {
		super.onCreate ( savedInstanceState );
		setContentView ( R.layout.act_splash );
		getSupportActionBar ( ).hide ( );
		//Travæ rotaçãø da tela
		setRequestedOrientation ( ActivityInfo.SCREEN_ORIENTATION_PORTRAIT );
		boolean statuInternet = isOnline ( this );
		
		initComponent ( );
		fontLogo ( );
		
		if ( statuInternet == false ) {
			alertOffline ( );
		} else {
			
			getWindow ( ).setFlags ( WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN );
			new Handler ( ).postDelayed ( new Runnable ( ) {
				@Override
				public void run ( ) {
					// startActivity(new Intent(getBaseContext(),ActLogin.class));
					testUserCurrent ( );
					finish ( );
				}
			}, 5000 );
			
			this.auth = FirebaseAuth.getInstance ( );
		}
	}
	
	//Altera fonte do txtLogo
	private void fontLogo ( ) {
		Typeface font = Typeface.createFromAsset ( getAssets ( ), "RagingRedLotusBB.ttf" );
		txtDelivery.setTypeface ( font );
		imgLogoS.setImageResource ( R.drawable.lghashi );
	}
	
	private void initComponent ( ) {
		txtDelivery = findViewById ( R.id.txtDelivery );
		imgLogoS = findViewById ( R.id.imgLogoS );
	}
	
	//case user login  ok  actpromotion
	public void testUserCurrent ( ) {
		
		if ( auth.getCurrentUser ( ) != null ) {
			Intent it = new Intent ( this, ActPromotion.class );
			startActivity ( it );
		} else {
			
			Intent it = new Intent ( this, ActLogin.class );
			startActivity ( it );
		}
	}
	
	//confimar pedido
	private void alertOffline ( ) {
		AlertDialog.Builder builder = new AlertDialog.Builder ( this );
		builder.setIcon ( R.drawable.signal_wifi_off_black_24dp );
		builder.setTitle ( getString ( R.string.no_connection ) );
		builder.setMessage ( getString ( R.string.check_your_connecntion ) );
		
		builder.setPositiveButton ( "Ok", new DialogInterface.OnClickListener ( ) {
			@Override
			public void onClick ( DialogInterface dialog, int which ) {
				finish ( );
			}
		} );
		builder.create ( );
		builder.show ( );
	}
}
