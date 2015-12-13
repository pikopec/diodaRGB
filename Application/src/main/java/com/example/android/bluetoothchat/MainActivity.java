/*
* Copyright 2013 The Android Open Source Project
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
*     http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/


package com.example.android.bluetoothchat;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.FragmentTransaction;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ViewAnimator;

import com.example.android.common.activities.SampleActivityBase;
import com.example.android.common.logger.Log;
import com.example.android.common.logger.LogFragment;
import com.example.android.common.logger.LogWrapper;
import com.example.android.common.logger.MessageOnlyLogFilter;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public class MainActivity extends SampleActivityBase {
    private static final int SELECT_PHOTO = 100;
    public static final String TAG = "MainActivity";
    public static byte[] pictureBytes = new byte[5808];
    ImageView imageView;
    private boolean mLogShown;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imageView = (ImageView)findViewById(R.id.imageView);


        if (savedInstanceState == null) {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            BluetoothChatFragment fragment = new BluetoothChatFragment();
            transaction.replace(R.id.sample_content_fragment, fragment);
            transaction.commit();
        }
    }


    public void choosePic(View view) {
        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
        photoPickerIntent.setType("image/*");
        startActivityForResult(photoPickerIntent, SELECT_PHOTO);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent) {
        super.onActivityResult(requestCode, resultCode, imageReturnedIntent);

        switch(requestCode) {
            case SELECT_PHOTO:
                if(resultCode == RESULT_OK){
                    Uri selectedImage = imageReturnedIntent.getData();
                    InputStream imageStream = null;
                    try {
                        imageStream = getContentResolver().openInputStream(selectedImage);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                    Bitmap yourSelectedImage = Bitmap.createScaledBitmap(BitmapFactory.decodeStream(imageStream), 264, 176, false);

                    for (int i=0; i<176; i++){
                        for (int j=0; j<33; j++){
                            String tempString = "";
                            for (int k=0; k<8; k++){
                                int color = yourSelectedImage.getPixel(j * 8 + k, i); //magia: j - numer bajtu, k - numer bitu
                                int green = Color.green(color);
                                int blue = Color.blue(color);
                                int red = Color.red(color);

                                if(green+blue+red<330){
                                    tempString += "0";
                                    yourSelectedImage.setPixel(j * 8 + k, i, Color.BLACK);
                                } else {
                                    tempString += "1";
                                    yourSelectedImage.setPixel(j * 8 + k, i, Color.WHITE);
                                }
                            }
                            byte b = (byte)Integer.parseInt(tempString, 2);
                            Log.d("tag", tempString + " " + b);

                            pictureBytes[i*33 + j] = b;    //magia: przejscie z tablicy 2d na 1d
                        }
                    }




                    imageView.setImageBitmap(yourSelectedImage);

                }
        }
    }

    static String getHex(int [] array, int mode){
        int x=0;
        if (mode==1) x+=4;
        if (array[x]==0 && array[x+1]==0 && array[x+2]==0 && array[x+3]==0) return "0";
        else if (array[x]==0 && array[x+1]==0 && array[x+2]==0 && array[x+3]==1) return "1";
        else if (array[x]==0 && array[x+1]==0 && array[x+2]==1 && array[x+3]==0) return "2";
        else if (array[x]==0 && array[x+1]==0 && array[x+2]==1 && array[x+3]==1) return "3";
        else if (array[x]==0 && array[x+1]==1 && array[x+2]==0 && array[x+3]==0) return "4";
        else if (array[x]==0 && array[x+1]==1 && array[x+2]==0 && array[x+3]==1) return "5";
        else if (array[x]==0 && array[x+1]==1 && array[x+2]==1 && array[x+3]==0) return "6";
        else if (array[x]==0 && array[x+1]==1 && array[x+2]==1 && array[x+3]==1) return "7";
        else if (array[x]==1 && array[x+1]==0 && array[x+2]==0 && array[x+3]==0) return "8";
        else if (array[x]==1 && array[x+1]==0 && array[x+2]==0 && array[x+3]==1) return "9";
        else if (array[x]==1 && array[x+1]==0 && array[x+2]==1 && array[x+3]==0) return "A";
        else if (array[x]==1 && array[x+1]==0 && array[x+2]==1 && array[x+3]==1) return "B";
        else if (array[x]==1 && array[x+1]==1 && array[x+2]==0 && array[x+3]==0) return "C";
        else if (array[x]==1 && array[x+1]==1 && array[x+2]==0 && array[x+3]==1) return "D";
        else if (array[x]==1 && array[x+1]==1 && array[x+2]==1 && array[x+3]==0) return "E";
        else return "F";
    }



}
//public class a {
//    public static void main(String [] args){
//        BufferedImage img = null;
//        int rgb;
//        int red;
//        int green;
//        int blue;
//        try {
//            img = ImageIO.read(new File("pos.bmp"));
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        BufferedImage image = new BufferedImage(264,176, BufferedImage.TYPE_INT_RGB);
//
//        for (int i=0; i<264; i++){
//            for (int j=0; j<176; j++){
//                rgb = img.getRGB(i, j);
//                red = (rgb >> 16 ) & 0x000000FF;
//                green = (rgb >> 8 ) & 0x000000FF;
//                blue = (rgb) & 0x000000FF;
//                if(green+blue+red<330){
//                    image.setRGB(i, j, Color.BLACK.getRGB());
//                } else {
//                    image.setRGB(i, j, Color.WHITE.getRGB());
//                }
//            }
//        }
//
//
//
//        for(int y=0; y<176; y++){
//            for(int x=0; x<264; x+=8){
//                int [] tempTable = new int[8];
//                for (int i=0; i<8; i++){
//                    if (image.getRGB(x+i, y)==Color.BLACK.getRGB()){
//                        tempTable[i] = 0;
//                    } else {
//                        tempTable[i] = 1;
//                    }
//                }
//
//                String hex1 = getHex(0, y, tempTable, 0);
//                String hex2 = getHex(0, y, tempTable, 1);
//                String dwaHexy = hex1 + hex2;
//                System.out.print("(byte)0x" + dwaHexy + ", ");
//            }
//            System.out.println();
//        }
//
//
//    }
