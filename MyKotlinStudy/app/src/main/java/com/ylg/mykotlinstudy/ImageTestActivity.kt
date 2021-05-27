package com.ylg.mykotlinstudy

import android.R.attr.bitmap
import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.graphics.Matrix
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity


//import com.ylg.mykotlinstudy.R.drawable.ic_launcher_foreground as ic_launcher_foreground1

class ImageTestActivity : AppCompatActivity() {
    var title1 = "ImageTestActivity"

   @SuppressLint("ResourceType")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_image_test)
        title = title1

        var ig2 = findViewById<ImageView>(R.id.ig2)

       val vectorDrawable: Drawable = resources.getDrawable(R.drawable.ic_launcher_foreground)
       var bit = Bitmap.createBitmap(
           vectorDrawable.intrinsicWidth,
           vectorDrawable.intrinsicHeight, Bitmap.Config.ARGB_8888
       )

        //var bit = BitmapDrawable(resources.openRawResource(R.drawable.ic_launcher_foreground)).bitmap
        Log.i("resizeImage", "resources: " + (resources == null) + " ,ig2: " + (ig2 == null)
        + " ,bit: " + (bit == null));
        /*ig2.setImageDrawable(
            resizeImage(
                BitmapFactory.decodeResource(
                    resources,
                    R.drawable.ic_launcher_background
                ), 18, 10
            )
        )*/
    }



    private fun resizeImage(bitmap: Bitmap, w: Int, h: Int): Drawable {
        // load the origial Bitmap
        var tag = "resizeImage"

        var BitmapOrg = bitmap;

        var width = BitmapOrg.getWidth();
        var height = BitmapOrg.getHeight();
        var newWidth = w;
        var newHeight = h;

        Log.i(tag, "width: " + width.toString());
        Log.i(tag, "height: " + height.toString());

        Log.i(tag, "newWidth: " + newWidth.toString());
        Log.i(tag, "newHeight: " + newHeight.toString());

        // calculate the scale
        var scaleWidth = newWidth.toFloat() / width;
        var scaleHeight = newHeight.toFloat() / height;

        Log.i(tag, "scaleWidth: " + scaleWidth.toString());
        Log.i(tag, "scaleHeight: " + scaleHeight.toString());

        // create a matrix for the manipulation
        var matrix: Matrix = Matrix();
        // resize the Bitmap
        matrix.postScale(scaleWidth, scaleHeight);
        // if you want to rotate the Bitmap
        // matrix.postRotate(45);

        // recreate the new Bitmap
        var resizedBitmap = Bitmap.createBitmap(
            BitmapOrg, 0, 0, width,
            height, matrix, true
        );

        // make a Drawable from Bitmap to allow to set the Bitmap
        // to the ImageView, ImageButton or what ever
        return BitmapDrawable(resizedBitmap);
    }
}