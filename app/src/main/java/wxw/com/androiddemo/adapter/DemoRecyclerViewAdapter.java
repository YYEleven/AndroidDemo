package wxw.com.androiddemo.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import wxw.com.androiddemo.R;

/**
 * Created by Eleven on 16/3/14.
 */
public class DemoRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewHolder> {

    private List<String> datas;
    private Context context;
    private LayoutInflater inflater;
    public DemoRecyclerViewAdapter(List<String> data,Context context){
        this.datas = data;
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public MyRecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_main,parent,false);
        MyRecyclerViewHolder holder = new MyRecyclerViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyRecyclerViewHolder holder, int position) {
        holder.mTextView.setText(datas.get(position));
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }
}
