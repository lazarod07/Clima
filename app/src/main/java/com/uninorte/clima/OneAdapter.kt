package com.uninorte.clima

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.uninorte.clima.data.Ciudad
import com.uninorte.clima.databinding.RowBinding

class OneAdapter(private val mValue: List<Ciudad>, private val mListener: onListInteraction) : RecyclerView.Adapter<OneAdapter.ViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): OneAdapter.ViewHolder {
        var binder: RowBinding
        binder = DataBindingUtil.inflate(LayoutInflater.from(parent.context),R.layout.row,parent, false)
        return ViewHolder(binder)
        //val view = LayoutInflater.from(parent.context).inflate(R.layout.row, parent,  false)
        //return ViewHolder(view)
    }

    override fun getItemCount(): Int = mValue.size

    override fun onBindViewHolder(holder: OneAdapter.ViewHolder, position: Int) {
        val item = mValue[position]
        holder.mView.ciudad = item
        holder.mView.executePendingBindings()
        holder.mView.cardView.setOnClickListener{
            mListener?.onListButtonInteraction(item)
        }
    }

    public fun updateData(){
        notifyDataSetChanged()
    }

    inner class ViewHolder(val mView: RowBinding): RecyclerView.ViewHolder(mView.root){

    }

    interface onListInteraction{
        fun onListButtonInteraction(item : Ciudad?)
    }


}
