package test.cl.opencore.cardviewjson2.adapters;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import test.cl.opencore.cardviewjson2.R;
import test.cl.opencore.cardviewjson2.to.ItemObject;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewHolders> {
    private List<ItemObject> itemList;
    private Context context;
    public RecyclerViewAdapter(Context context, List<ItemObject> itemList) {
        this.itemList = itemList;
        this.context = context;
    }
    @Override
    public RecyclerViewHolders onCreateViewHolder(ViewGroup parent, int viewType) {
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, null);
        RecyclerViewHolders rcv = new RecyclerViewHolders(layoutView);
        return rcv;
    }
    @Override
    public void onBindViewHolder(RecyclerViewHolders holder, int position) {
        holder.songTitle.setText("Nombre Producto: " + itemList.get(position).getNombre());
        holder.songYear.setText("Tipo Producto: " + itemList.get(position).getTipo());
        holder.songAuthor.setText("Id Producto: " + itemList.get(position).getId());
    }
    @Override
    public int getItemCount() {
        return this.itemList.size();
    }
}