package com.example.costumermanagement;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Filter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CostumerAdapter extends RecyclerView.Adapter<CostumerAdapter.ViewHolder> {
    private ArrayList<Costumer> mCostumerData;
    private ArrayList<Costumer> mCostumerDataAll;
    private Context nContext;
    private int lastPos = -1;

    CostumerAdapter(Context context, ArrayList<Costumer> itemsData){
        this.mCostumerData = itemsData;
        this.mCostumerDataAll = itemsData;
        this.nContext = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(nContext).inflate(R.layout.list_item, parent, false));
    }

    @Override
    public void onBindViewHolder( CostumerAdapter.ViewHolder holder, int position) {
        Costumer currentCustomer = mCostumerData.get(position);
        holder.bindTo(currentCustomer);

        if (holder.getAdapterPosition() > lastPos){
            lastPos = holder.getAdapterPosition();
        }
    }

    @Override
    public int getItemCount() {
        return mCostumerData.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder{
        private TextView mNameText;
        private TextView mStatusText;
        private TextView mPaymentMethodText;

        public ViewHolder(View itemView) {
            super(itemView);

            mNameText = itemView.findViewById(R.id.itemName);
            mStatusText = itemView.findViewById(R.id.itemStatus);
            mPaymentMethodText = itemView.findViewById(R.id.itemPaymentMethod);

            itemView.findViewById(R.id.itemUpdate).setOnClickListener(v -> {
                ((ListCostumers) nContext).updateRecord(
                        mNameText.getText().toString(),
                        mStatusText.getText().toString(),
                        mPaymentMethodText.getText().toString());
            });
            itemView.findViewById(R.id.itemDelete).setOnClickListener(v -> {
                ((ListCostumers) nContext).delete(
                        mNameText.getText().toString(),
                        mStatusText.getText().toString(),
                        mPaymentMethodText.getText().toString());
            });
        }

        public void bindTo(Costumer currentCustomer) {
            mNameText.setText(currentCustomer.getName());
            mStatusText.setText(currentCustomer.getStatus());
            mPaymentMethodText.setText(currentCustomer.getPaymentMethod());
        }
    };

};

