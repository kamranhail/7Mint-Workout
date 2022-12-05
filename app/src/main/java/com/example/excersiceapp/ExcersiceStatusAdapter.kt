package com.example.excersiceapp

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.excersiceapp.databinding.ItemExcerciseStatusBinding


class ExcersiceStatusAdapter (val items : ArrayList<ExerciseModel>) :
RecyclerView.Adapter<ExcersiceStatusAdapter.ViewHolder>()

{
    class ViewHolder (binding: ItemExcerciseStatusBinding):RecyclerView.ViewHolder(binding.root){

     val tvitem=binding.tvItem
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
       return  ViewHolder(ItemExcerciseStatusBinding.inflate(
           LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val model: ExerciseModel=items[position]
        holder.tvitem.text=model.getId().toString()
when{

    model.getIsSelected()->
    {
        holder.tvitem.background=ContextCompat.getDrawable(holder.itemView.context,
        R.drawable.roundbuttonringwhite
            )


    }model.getIsCompleted() ->
{
    holder.tvitem.background=ContextCompat.getDrawable(holder.itemView.context,
        R.drawable.roundbuttongreen)
}else->
{
    holder.tvitem.background=ContextCompat.getDrawable(holder.itemView.context,
        R.drawable.roundbuttonringorange)
}
}

    }

    override fun getItemCount(): Int {
      return items.size
    }
}