package com.example.chatbot

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class MessageAdapter(var messageList: List<Message>): RecyclerView.Adapter<MessageAdapter.MyViewHolder>() {

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
         var leftChatView: LinearLayout
         var rightChatView: LinearLayout
         var leftTv: TextView
         var rightTv: TextView

        init {
            leftChatView = itemView.findViewById(R.id.left_chat_view)
            rightChatView = itemView.findViewById(R.id.right_chat_view)
            leftTv = itemView.findViewById(R.id.left_chat_text)
            rightTv = itemView.findViewById(R.id.right_chat_text)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view=LayoutInflater.from(parent.context).inflate(R.layout.chat_item,parent,false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return messageList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val messageL=messageList[position]
        if(!messageL.SENT_BY_BOT){
            holder.leftChatView.visibility=View.GONE
            holder.rightChatView.visibility=View.VISIBLE
            holder.rightTv.text=messageL.message
        }
        else{
            holder.rightChatView.visibility=View.GONE
            holder.leftChatView.visibility=View.VISIBLE
            holder.leftTv.text=messageL.message
        }
    }
}