package com.example.chatbot

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.chatbot.databinding.ActivityMainBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MainActivity : AppCompatActivity() {

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
            addToChat(response,true)
        }
    }

      fun callAPI(question:String){
         messageList.add(Message("Typing...",true))

          val requestBody=RequestBody(null, 4000,"text-davinci-003",1,question,"\n",false,0,1)

         val responseData=ServiceBuilder.buildService(ApiService::class.java)
          responseData.sendReq("application/json","Bearer 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",requestBody).enqueue(object: Callback<ResponseData>{
              override fun onResponse(call: Call<ResponseData>, response: Response<ResponseData>) {
                  Log.i("Response",response.isSuccessful.toString())
                  if(response.isSuccessful){
                      addResponse(response.body()!!.choices[0].text)
                  }
              }

              override fun onFailure(call: Call<ResponseData>, t: Throwable) {
                  addResponse("Failed to load chat due to+ ${call.toString()}")
              }

          })




    }




}