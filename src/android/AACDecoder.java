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


public class AacDecoder extends CordovaPlugin {

    public static final String TAG = "Aac Decoder Plugin";
    private MultiPlayer multiPlayer;

    public AacDecoder(){}

    @Override
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
        if(this.cordova.getActivity().isFinishing()) return true;
        /*(if (action.equals("mediaPlayer")) {
            Log.v(TAG,"ESTAMOS NA ACTION mediaPlayer");
            return true;
        }
        else {
            return false;
        }*/
        if (action.equals("mediaPlayer")) {
            Log.v(TAG, "ESTAMOS AQUI em MEdia player: URL: " + args.getString(0));
//            try{
//                multiPlayer = new MultiPlayer();
//                multiPlayer.playAsync(args.getString(0));
//            } catch (Throwable t){
//                Log.w(TAG, "Cannot play url - " + t );
//            }
            startMediaPlayer(args.getString(0));

        } else if (action.equals("stopMediaPlayer")) {
            stop();
        }  else if (action.equals("readyState")) {
            // verificar se tem um buffer maior que 2

            //if (multiPlayer == null){
            //    callbackContext.error(0);
            //} else {
            //    callbackContext.success();
            //}
        } else {
            return false;
        }
        callbackContext.success();
        return true;
    }

    private void startMediaPlayer(String url){
        stop();
        try {
            multiPlayer = new MultiPlayer();
            multiPlayer.playAsync(url);
        } catch (Throwable t){
            Log.w(TAG, "Cannot play url - " + t);
        }
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