package com.uninorte.clima

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.uninorte.clima.data.Ciudad
import kotlinx.android.synthetic.main.fragment_main.view.*
import org.json.JSONArray
import org.json.JSONObject


/**
 * A simple [Fragment] subclass.
 */
class MainFragment : Fragment(), OneAdapter.onListInteraction {

    lateinit var navController: NavController
    private var adapter : OneAdapter? = null
    private val cityList = mutableListOf<Ciudad>()
    lateinit var model : MyViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        model = ViewModelProvider(this).get(MyViewModel::class.java)
        val view = inflater.inflate(R.layout.fragment_main, container, false)

        VolleySingleton.getInstance(this.context!!).addToRequestQueue(getObjectRequest())

        adapter = OneAdapter(cityList, this)

        view.list.layoutManager = LinearLayoutManager(context)
        view.list.adapter = adapter
        return view
    }

    override fun onListButtonInteraction(item: Ciudad?) {
        val bundle = bundleOf("nombre" to item!!.nombre)
        view!!.findNavController().navigate(R.id.action_mainFragment_to_ciudadFragment, bundle)
    }

    fun getStringRequest() : StringRequest {


        val url = "https://api.openweathermap.org/data/2.5/weather?q=Barranquilla&appid=dfa991917a43bdba207c97a185748990"

        val stringRequest = StringRequest(
            Request.Method.GET, url,
            Response.Listener <String> { response ->
                Log.d("ww","ff")
                Log.d("ww",response.toString())

                //textViewNombre = response.toString()
            },
            Response.ErrorListener{
                Log.d("ww","errorrrrrrrr")
                //textView.text = "error"
            }
        )

        return stringRequest
    }

    fun getObjectRequest() : JsonObjectRequest {
        val url = "https://api.openweathermap.org/data/2.5/group?id=3689147,3688689,3674962,3687925,3687238,3685533,3680656,3667905,3667849,3688928&units=metric%20&appid=dfa991917a43bdba207c97a185748990"
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
        if (cityList.isNotEmpty()){
            cityList.clear()
        }
        for(num in 0..9) {
            val jsonArrayResult : JSONArray = response.getJSONArray("list")
            val useObject = jsonArrayResult.getJSONObject(num)
            val nombre = useObject.getString("name")
            val useObject2 = useObject.getJSONObject("main")
            var temp: Int = useObject2.getInt("temp")
            cityList.add(Ciudad(nombre, (temp-273).toString()+"°C"))
        }
        model.getCities().value = cityList
        adapter!!.updateData()
    }
}
