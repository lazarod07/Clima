package com.uninorte.clima

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.uninorte.clima.data.Ciudad
import kotlinx.android.synthetic.main.fragment_main.view.*
import org.json.JSONArray
import org.json.JSONObject
import java.lang.Math.log

/**
 * A simple [Fragment] subclass.
 */
class CiudadFragment : Fragment() , TwoAdapter.onListInteraction {

    private val days = mutableListOf<Ciudad>()
    lateinit var model : MyViewModel
    private var adapter : TwoAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        model = ViewModelProvider(this).get(MyViewModel::class.java)
        val view = inflater.inflate(R.layout.fragment_ciudad, container, false)

        view.findViewById<TextView>(R.id.textViewNombre).text = arguments!!.getString("nombre")

        VolleySingleton.getInstance(this.context!!).addToRequestQueue(getObjectRequest())

        adapter = TwoAdapter(days,this)
        view.list.layoutManager = LinearLayoutManager(context)
        view.list.adapter = adapter

        return view
        //return inflater.inflate(R.layout.fragment_ciudad, container, false)
    }

    /*override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val n = arguments!!.getString("nombre")
    }*/

    override fun onListButtonInteraction(item: Ciudad?) {
        TODO("Not yet implemented")
    }


    fun getStringRequest() : StringRequest {


        val url = "https://api.openweathermap.org/data/2.5/weather?q=Barranquilla&appid=dfa991917a43bdba207c97a185748990"

        val stringRequest = StringRequest(
            Request.Method.GET, url,
            Response.Listener <String> { response ->
                Log.d("ww","ff")
                Log.d("ww",response.toString())
            },
            Response.ErrorListener{
                Log.d("ww","errorrrrrrrr")
            }
        )
        return stringRequest
    }

    fun getObjectRequest() : JsonObjectRequest {
        val n = arguments!!.getString("nombre")
        val url = "https://api.openweathermap.org/data/2.5/forecast?q=$n&appid=dfa991917a43bdba207c97a185748990"
        val stringRequest = JsonObjectRequest(
            Request.Method.GET, url,null,
            Response.Listener { response ->
                parseObject(response)
                //textViewNombre = response.toString()
            },
            Response.ErrorListener{
                Log.d("rrrr", "esta mal")
            }
        )
        return stringRequest
    }

    fun parseObject(response: JSONObject) {
        if (days.isNotEmpty()){
            days.clear()
        }
        val jsonArrayResult : JSONArray = response.getJSONArray("list")
        for (i in 1..jsonArrayResult.length() step 8){
            val useObject = jsonArrayResult.getJSONObject(i)
            val useObject2 = useObject.getJSONObject("main")
            var temp: Int = useObject2.getInt("temp")
            val dia = useObject.getString("dt_txt")
            val sp = dia.split(" ")
            days.add(Ciudad("Dia: ${sp[0]}",(temp-273).toString()+"°C"))
        }
        model.getCities().value = days
        adapter!!.updateData()
    }
}
