package com.example.chatbot

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.chatbot.databinding.ActivityMainBinding
import com.google.gson.Gson
import kotlinx.coroutines.*
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaType
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException


class MainActivity : AppCompatActivity() {
    val JSON= "application/json; charset=utf-8".toMediaType()
    var client=OkHttpClient()
    private lateinit var binding:ActivityMainBinding
    private lateinit var messageList: ArrayList<Message>
    private lateinit var messageAdapter: MessageAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding=ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        messageList= arrayListOf()

        val send=binding.send
        val message=binding.text
        val recyclerView=binding.recyclerView

        messageAdapter=MessageAdapter(messageList)
        recyclerView.adapter=messageAdapter
        val llm=LinearLayoutManager(this)
        llm.stackFromEnd=true
        recyclerView.layoutManager=llm


        send.setOnClickListener {
            val question=message.text.toString().trim()
            if(question!=""){
                addToChat(question,false)
                callAPI(question)
            }

        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun addToChat(message:String, sentByBot:Boolean) {
       runOnUiThread {
           messageList.add(Message(message,sentByBot))
           messageAdapter.notifyDataSetChanged()
           binding.recyclerView.smoothScrollToPosition(messageAdapter.itemCount)
           binding.text.text.clear()
       }
    }

    fun addResponse(response: String){
        messageList.removeAt(messageList.size - 1)
        if(response!=""){
            addToChat(response.toString(),true)
        }
    }

      fun callAPI(question:String){
          messageList.add(Message("Typing...",true))
          val jsonBody=JSONObject()
          try{
              jsonBody.put("model","text-davinci-003")
              jsonBody.put("prompt",question);
              jsonBody.put("max_tokens",4000);
              jsonBody.put("temperature",0);
          }catch (e:JSONException){
              e.printStackTrace()
          }
          val body=RequestBody.create(JSON,jsonBody.toString())
          var request=Request.Builder().url("https://api.openai.com/v1/completions/")
              .header("Authorization","Bearer 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")
              .post(body)
              .build()

          client.newCall(request).enqueue(object :Callback{
              override fun onFailure(call: Call, e: IOException) {
                  addResponse("Failed to load response due to "+e.message)
              }

              override fun onResponse(call: Call, response: Response) {
                  Log.i("Mayank",response.isSuccessful.toString())
                  if(response.isSuccessful){
                      try{
                          val responseData=Gson().fromJson(response.body!!.charStream(),ResponseData::class.java)

                          addResponse(responseData.choices[0].text)
                      }catch (e:JSONException){
                          e.printStackTrace()
                      }
                  }
                  else{
                      Log.i("Mayank",response.body.toString())
                      addResponse("Failed to load response due to "+ response.body.toString())
                  }
              }

          })


    }




}