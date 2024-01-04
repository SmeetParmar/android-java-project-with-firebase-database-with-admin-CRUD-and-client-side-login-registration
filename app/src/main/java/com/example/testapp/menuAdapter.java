package com.example.testapp;

import android.content.Context;
import android.content.Intent;
import android.os.ParcelFileDescriptor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

public class menuAdapter extends FirebaseRecyclerAdapter<menuModel, menuAdapter.myViewHolder> {

    Context context;

    public menuAdapter(@NonNull FirebaseRecyclerOptions<menuModel> options, Context context) {

        super(options);
        this.context = context;
    }

    @Override
    protected void onBindViewHolder(@NonNull myViewHolder holder, int position, @NonNull menuModel model) {
        holder.itemName.setText(model.getName());
        holder.itemDetails.setText(model.getDetails().substring(0,100)+"...");
        holder.itemPrice.setText(model.getPrice());
        Glide.with(holder.itemImage.getContext())
                .load(model.getImage())
                .into(holder.itemImage);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent toIndividualActivity = new Intent(context,individualActivity.class);

                toIndividualActivity.putExtra("itemName",model.getName());
                toIndividualActivity.putExtra("itemPrice",model.getPrice());
                toIndividualActivity.putExtra("itemDetails",model.getDetails());
                toIndividualActivity.putExtra("itemImage",model.getImage());

                toIndividualActivity.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(toIndividualActivity);
            }
        });
    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.menu_item,parent,false);
        return new myViewHolder(view);
    }

    class myViewHolder extends RecyclerView.ViewHolder{

        ImageView itemImage;
        TextView itemName,itemDetails,itemPrice;

        public myViewHolder(@NonNull View itemView) {
            super(itemView);

            itemImage = itemView.findViewById(R.id.image);
            itemName = itemView.findViewById(R.id.itemName);
            itemDetails = itemView.findViewById(R.id.itemDetails);
            itemPrice = itemView.findViewById(R.id.itemPrice);

        }
    }
}
