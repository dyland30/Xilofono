package com.qbit.xilofono;

import java.util.HashMap;


import android.os.Bundle;
import android.os.SystemClock;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.media.AudioManager;
import android.media.SoundPool;
import android.media.SoundPool.OnLoadCompleteListener;

public class MainActivity extends Activity implements OnClickListener{
	private SoundPool soundPool;
	private boolean loaded = false;
	private HashMap<String, Integer> mapaSonidos;
	private String ultimaCancion="DO_100,RE_100,MI_100,FA_100,SOL_100,LA_100,SI_100,DO1_100";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		//cargar
		
		try{
			Button btnDo = (Button) findViewById(R.id.btnDo);
			btnDo.setOnClickListener(this);
			
			Button btnRe = (Button) findViewById(R.id.btnRe);
			btnRe.setOnClickListener(this);
			
			Button btnMi = (Button) findViewById(R.id.btnMi);
			btnMi.setOnClickListener(this);
			
			Button btnFa = (Button) findViewById(R.id.btnFa);
			btnFa.setOnClickListener(this);
			
			Button btnSol = (Button) findViewById(R.id.btnSol);
			btnSol.setOnClickListener(this);
			
			Button btnLa = (Button) findViewById(R.id.btnLa);
			btnLa.setOnClickListener(this);
			
			Button btnSi = (Button) findViewById(R.id.btnSi);
			btnSi.setOnClickListener(this);
			
			Button btnDo1 = (Button) findViewById(R.id.btnDo1);
			btnDo1.setOnClickListener(this);
			
			
			this.setVolumeControlStream(AudioManager.STREAM_MUSIC);
			
			soundPool = new SoundPool(10, AudioManager.STREAM_MUSIC, 0);
			soundPool.setOnLoadCompleteListener(new OnLoadCompleteListener() {
	            @Override
	            public void onLoadComplete(SoundPool soundPool, int sampleId,
	                    int status) {
	                loaded = true;
	            }
	        });
	        
			
			// cargar los sonidos
			mapaSonidos = new HashMap<String, Integer>();
			loaded = false;
			mapaSonidos.put("DO", soundPool.load(this,R.raw.dom,1));
			//mostrarMensaje("hashmap cargado");
			loaded = false;
			mapaSonidos.put("RE", soundPool.load(this,R.raw.re,1));
			loaded = false;
			mapaSonidos.put("MI", soundPool.load(this,R.raw.mi,1));
			loaded = false;
			mapaSonidos.put("FA", soundPool.load(this,R.raw.fa,1));
			loaded = false;
			mapaSonidos.put("SOL", soundPool.load(this,R.raw.sol,1));
			loaded = false;
			mapaSonidos.put("LA", soundPool.load(this,R.raw.la,1));
			loaded = false;
			mapaSonidos.put("SI", soundPool.load(this,R.raw.si,1));
			loaded = false;
			mapaSonidos.put("DO1", soundPool.load(this,R.raw.do1,1));
		
			
		}catch(Exception ex){
			
		
			mostrarMensaje(ex.getMessage());
			
		}
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		
		
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    // Handle item selection
	    switch (item.getItemId()) {
	        case R.id.action_tocarEscala:
	        	reproducirEscala();
	            return true;
	        case R.id.action_reproducir:
	        	reproducirUltimaCancion();
	            return true;
	        default:
	            return false;
	    }
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		String nota = "";		
		switch (v.getId()) {
		case R.id.btnDo:
			nota = "DO";
			break;
		case R.id.btnRe:
			nota = "RE";
			break;
		case R.id.btnMi:
			nota = "MI";
			break;
		case R.id.btnFa:
			nota = "FA";
			break;
		case R.id.btnSol:
			nota = "SOL";
			break;
		case R.id.btnLa:
			nota = "LA";
			break;
		case R.id.btnSi:
			nota = "SI";
			break;
		case R.id.btnDo1:
			nota = "DO1";
			break;
		default:
			nota = "";
			break;
		}
		
		reproducir(nota);
		
	}
	
	public void reproducirUltimaCancion(){
		
		ReproducirCadena(ultimaCancion);
		
	}
	
	public void reproducirEscala(){
		String escala  ="DO_200,RE_200,MI_200,FA_200,SOL_200,LA_200,SI_200,DO1_200";
		
		ReproducirCadena(escala);
		
	}
	public void ReproducirCadena(String cadena){
		
		String[] notas= cadena.split(",");
		for(String not : notas){
			String[] comp = not.split("_");
			int tiempo = Integer.parseInt(comp[1]);
			
			reproducir(comp[0]);
			Dormir(tiempo);
			
			
		}
		
	}
	public void Dormir(int tiempo){
		
		SystemClock.sleep(tiempo);
		
	}
	public void reproducir(String nota){
		 AudioManager audioManager = (AudioManager) getSystemService(AUDIO_SERVICE);
         float actualVolume = (float) audioManager
                 .getStreamVolume(AudioManager.STREAM_MUSIC);
         float maxVolume = (float) audioManager
                 .getStreamMaxVolume(AudioManager.STREAM_MUSIC);
         float volume = actualVolume / maxVolume;
         // Is the sound loaded already?
         if (loaded) {
             soundPool.play(mapaSonidos.get(nota), volume, volume, 1, 0, 1f);
         }
	}
public void mostrarMensaje(String mensaje){
		
		AlertDialog errorDialog = new AlertDialog.Builder(MainActivity.this).create();
		errorDialog.setTitle(getResources().getString(R.string.str_tituloError));
		errorDialog.setMessage(mensaje);
		errorDialog.setIcon(android.R.drawable.ic_dialog_alert);
		errorDialog.setButton(getResources().getString(R.string.str_aceptar), new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				dialog.cancel();
			}
		});
		errorDialog.show();
	}

}
