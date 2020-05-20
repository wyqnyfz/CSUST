package com.coolweather.csust;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ClassAdapter extends RecyclerView.Adapter<ClassAdapter.ViewHolder> {

    List<ItemClass> mItemClassList;

    public ClassAdapter(List<ItemClass> itemClassList) {
        mItemClassList = itemClassList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_class, parent,false);
        ViewHolder viewHolder =new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ItemClass p = mItemClassList.get(position);          //获取第pos个信息
        Log.d("----->", p.name+"<-:->"+p.adress);
        if(p.name.length()==0){
            holder.ll.setVisibility(View.INVISIBLE);
        }else {
            holder.ll.setVisibility(View.VISIBLE);
            holder.tv_name.setText(p.name);
            holder.tv_adress.setText(p.adress);
        }

    }

    @Override
    public int getItemCount() {
        return mItemClassList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        LinearLayout ll;
        TextView tv_name;
        TextView tv_adress;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ll = itemView.findViewById(R.id.ll_item_ll);
            tv_name = itemView.findViewById(R.id.tv_itemclass_name);
            tv_adress = itemView.findViewById(R.id.tv_itemclass_adress);
        }
    }
}
