package br.com.calangodev.aacdecoder;

import com.spoledge.aacdecoder.PlayerCallback;
import android.app.Service;
import android.util.Log;
import android.media.AudioTrack;
import android.os.Handler;
import android.os.IBinder;
import android.content.Intent;

import java.lang.Override;


public class PlayerCallbackService extends Service implements PlayerCallback {

    public static final String TAG = "Aac Decoder Plugin";
    private Handler uiHandler;

    @Override
    public IBinder onBind(Intent intent) {
        // TODO Auto-generated method stub
        Log.i(TAG, "OnBind" + intent);
        return null;
    }

    @Override
    public void onCreate(){
        super.onCreate();
        keepAwake();
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        sleepWell();
    }

    public void keepAwake(){
        Log.w(TAG, "KEEPAWAKE FUNCTION");
    }

    public void sleepWell(){
        Log.w(TAG, "SLEEPWELL");
    }

    //////////
    // PlayerCallback Methods
    /////////
    private boolean playerStarted;

    public void playerStarted() {
        Log.v(TAG, "TESTEEEEEE");
        uiHandler.post( new Runnable() {
            public void run() {
                playerStarted = true;
            }
        });
    }

    /**
     * This method is called periodically by PCMFeed.
     *
     * @param isPlaying false means that the PCM data are being buffered,
     *          but the audio is not playing yet
     *
     * @param audioBufferSizeMs the buffered audio data expressed in milliseconds of playing
     * @param audioBufferCapacityMs the total capacity of audio buffer expressed in milliseconds of playing
     */
    public void playerPCMFeedBuffer( final boolean isPlaying,
                                     final int audioBufferSizeMs, final int audioBufferCapacityMs ) {
        Log.v(TAG, "TESTEEEEEE");

        uiHandler.post( new Runnable() {
            public void run() {
                //progress.setProgress( audioBufferSizeMs * progress.getMax() / audioBufferCapacityMs );
                //if (isPlaying) txtStatus.setText( R.string.text_playing );
            }
        });
    }


    public void playerStopped( final int perf ) {
        Log.v(TAG, "TESTEEEEEE");
        uiHandler.post( new Runnable() {
            public void run() {
                playerStarted = false;
            }
        });
    }


    public void playerException( final Throwable t) {
        Log.v(TAG, "TESTEEEEEE");
        uiHandler.post( new Runnable() {
            public void run() {
                if (playerStarted) playerStopped( 0 );
            }
        });
    }


    public void playerMetadata( final String key, final String value ) {
        Log.v(TAG, "TESTEEEEEE");

    }


    public void playerAudioTrackCreated( AudioTrack atrack ) {
        Log.v(TAG, "TESTEEEEEE");
    }

}