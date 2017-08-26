/*
       Licensed to the Apache Software Foundation (ASF) under one
       or more contributor license agreements.  See the NOTICE file
       distributed with this work for additional information
       regarding copyright ownership.  The ASF licenses this file
       to you under the Apache License, Version 2.0 (the
       "License"); you may not use this file except in compliance
       with the License.  You may obtain a copy of the License at
         http://www.apache.org/licenses/LICENSE-2.0
       Unless required by applicable law or agreed to in writing,
       software distributed under the License is distributed on an
       "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
       KIND, either express or implied.  See the License for the
       specific language governing permissions and limitations
       under the License.
*/
package br.com.calangodev.aacdecoder;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.PluginResult;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import android.view.View;
import android.util.Log;
import com.spoledge.aacdecoder.MultiPlayer;
import android.app.Activity;

import java.lang.Throwable;

import br.com.calangodev.aacdecoder.PlayerCallbackService;
import android.media.*;
import android.content.Context;


public class AacDecoder extends CordovaPlugin {

    public static final String TAG = "Aac Decoder Plugin";
    private MultiPlayer multiPlayer;
    private PlayerCallbackService playerCallback;

    private Context context;
    private AudioManager manager;

    public AacDecoder(){}

    @Override
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
        if(this.cordova.getActivity().isFinishing()) return true;
        if (action.equals("mediaPlayer")) {
            //Log.v(TAG, "ESTAMOS AQUI em MEdia player: URL: " + args.getString(0));
            startMediaPlayer(args.getString(0));
        } else if (action.equals("stopMediaPlayer")) {
            stop();
        }  else if (action.equals("readyState")) {
            // verificar se tem um buffer maior que 2
            if (multiPlayer == null){
                callbackContext.error(0);
            } else {
                callbackContext.success();
            }
        } else if (action.equals("diminuiVolume")) {
            context = cordova.getActivity().getApplicationContext();
            manager = (AudioManager)context.getSystemService(Context.AUDIO_SERVICE);
            //manager.adjustStreamVolume(AudioManager.STREAM_MUSIC, AudioManager.ADJUST_LOWER, AudioManager.FLAG_SHOW_UI);
            int sb2value;
            sb2value = manager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
            manager.setStreamVolume(AudioManager.STREAM_MUSIC, (sb2value/2)+3, AudioManager.FLAG_SHOW_UI);
        } else {
            return false;
        }
        callbackContext.success();
        return true;
    }

    private void startMediaPlayer(String url){
        stop();

        try {
            java.net.URL.setURLStreamHandlerFactory( new java.net.URLStreamHandlerFactory(){
                public java.net.URLStreamHandler createURLStreamHandler( String protocol ) {
                    Log.d( TAG, "Asking for stream handler for protocol: '" + protocol + "'" );
                    if ("icy".equals( protocol )) return new com.spoledge.aacdecoder.IcyURLStreamHandler();
                    return null;
                }
            });
        } catch (Throwable t){
            Log.w(TAG, "Cannot set the ICY URLStreamHandler - maybe already set ? - " + t);
        }

        try {
            //player.playAsync(url);    
            multiPlayer = new MultiPlayer();
            multiPlayer.playAsync(url);
        } catch (IOException e) {
            if ("Error response: 401 Service Unavailable".equals(e.getMessage()) {
                callbackContext.error(0);
                throw new IOException("Erro: Serviço não disponível");                
            } else {
                throw e;
            }
        }

        // try {
        //     multiPlayer = new MultiPlayer();
        //     multiPlayer.playAsync(url);
        // } catch (Throwable t){
        //     Log.w(TAG, "Cannot play url - " + t);
        // }
    }

    private void stop(){
        if (multiPlayer != null) {
            multiPlayer.stop();
            multiPlayer = null;
        }
    }

    private void startService(){
        Activity context = cordova.getActivity();
    }

}