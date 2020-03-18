package com.uninorte.clima

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.uninorte.clima.data.Ciudad
import com.uninorte.clima.databinding.Row2Binding
import com.uninorte.clima.databinding.RowBinding

class TwoAdapter(private val mValue: List<Ciudad>, private val mListener: onListInteraction) : RecyclerView.Adapter<TwoAdapter.ViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): TwoAdapter.ViewHolder {
        var binder: Row2Binding
        binder = DataBindingUtil.inflate(LayoutInflater.from(parent.context),R.layout.row2,parent, false)
        return ViewHolder(binder)
        //val view = LayoutInflater.from(parent.context).inflate(R.layout.row, parent,  false)
        //return ViewHolder(view)
    }

    override fun getItemCount(): Int = mValue.size

    override fun onBindViewHolder(holder: TwoAdapter.ViewHolder, position: Int) {
        val item = mValue[position]
        holder.mView.dia = item
        holder.mView.executePendingBindings()
        //holder.mView.cardView.setOnClickListener{
            //mListener?.onListButtonInteraction(item)
        //}
    }

    public fun updateData(){
        notifyDataSetChanged()
    }

    inner class ViewHolder(val mView: Row2Binding): RecyclerView.ViewHolder(mView.root){

    }

    interface onListInteraction{
        fun onListButtonInteraction(item : Ciudad?)
    }


}