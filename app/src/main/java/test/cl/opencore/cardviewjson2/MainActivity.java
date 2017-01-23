package test.cl.opencore.cardviewjson2;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import test.cl.opencore.cardviewjson2.adapters.RecyclerViewAdapter;
import test.cl.opencore.cardviewjson2.adapters.SimpleDividerItemDecoration;
import test.cl.opencore.cardviewjson2.to.ItemObject;

public class MainActivity extends AppCompatActivity {
    private final String TAG = "MainActivity";
    private RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;
    private RecyclerViewAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = (RecyclerView)findViewById(R.id.recycler_view);
        recyclerView.addItemDecoration(new SimpleDividerItemDecoration(this));
        layoutManager = new LinearLayoutManager(MainActivity.this);
        recyclerView.setLayoutManager(layoutManager);
        requestJsonObject();
    }
    private void requestJsonObject(){
        RequestQueue queue = Volley.newRequestQueue(this);
        String url ="https://apitestcore.herokuapp.com/api/v1/productos";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Response " + response);
                GsonBuilder builder = new GsonBuilder();
                Gson mGson = new Gson();
                List<ItemObject> posts = new ArrayList<ItemObject>();
               // posts =Arrays.asList(mGson.fromJson(response, ItemObject.class));
                JsonParser parser = new JsonParser();
                JsonElement element = parser.parse(response);
                if (element.isJsonObject()) {
                    Log.d(TAG, "tengo alguna wea  " + element.toString());
                    JsonObject productosJson = element.getAsJsonObject();
                    JsonArray datasets = productosJson.getAsJsonArray("productos");
                    for (int i = 0; i< datasets.size();i++){
                        JsonObject dataset = datasets.get(i).getAsJsonObject();
                        ItemObject nuevo = new ItemObject();
                        nuevo.setId(dataset.get("id").getAsInt());
                        nuevo.setNombre(dataset.get("nombre").getAsString());
                        nuevo.setCodigo(dataset.get("codigo").getAsString());
                        nuevo.setImagen(dataset.get("imagen").getAsString());
                        nuevo.setTipo(dataset.get("tipo").getAsString());
                        posts.add(nuevo);
                    }
                }
                adapter = new RecyclerViewAdapter(MainActivity.this, posts);
                recyclerView.setAdapter(adapter);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(TAG, "Error " + error.getMessage());
            }
        });
        queue.add(stringRequest);
    }
}