package com.ylg.mykotlinstudy

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class RecyclerAdapter(private val context: Context) :
    RecyclerView.Adapter<RecyclerAdapter.ViewHolder>() {
    var TAG = RecyclerAdapter::class.java.name
    private val inflater: LayoutInflater
    private val activitys: List<String>
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val view =
            inflater.inflate(R.layout.item_recycler_layout, parent, false)
        //view.setBackgroundColor(Color.RED);
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        try {
            //Class clazz = getClassLoader().loadClass("com.ylg.androidbasic.TestActivity");
            val clazz = Class.forName(activitys[position])
            //Log.i(TAG, "Title = " + clazz.getDeclaredField("title1").get(clazz.newInstance()));
            //val javaclass = clazz.newInstance().javaClass
            //holder.btn.text = clazz.getDeclaredField("title1").toString()
            //holder.btn.text =  javaclass.getField("title1").get(javaclass.newInstance()).toString()
            holder.btn.text = clazz.getMethod("getTitle1").invoke(clazz.newInstance()).toString()
                //.invoke(clazz.newInstance(),null).toString()

        } catch (e: Exception) {
            Log.i(
                TAG,
                "RecyclerAdapter onBindViewHolder {position=" + position + ", " + activitys[position] + "} error: " + e.message
            )
        }
        holder.itemView.setOnClickListener {
            try {
                //Class clazz = getClassLoader().loadClass("com.ylg.androidbasic.TestActivity");
                val clazz = Class.forName(activitys[position])
                //Activity clazz1 =  (Activity)clazz.newInstance();
                //Log.i(TAG, "Title = " + clazz.getDeclaredField("title1").get(clazz.newInstance()));
                val intent = Intent(context, clazz)
                context.startActivity(intent)
            } catch (e: Exception) {
                Log.i(
                    TAG, "RecyclerAdapter startActivity {position=" + position + ", "
                            + activitys[position] + "} error: " + e.message
                )
            }
        }
    }

    override fun getItemCount(): Int {
        return activitys.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tv: TextView? = null
        var btn: Button

        init {
            btn = itemView as Button
        }
    }

    init {
        inflater = LayoutInflater.from(context)
        activitys = (context.applicationContext as MyApplication).activitys
    }
}