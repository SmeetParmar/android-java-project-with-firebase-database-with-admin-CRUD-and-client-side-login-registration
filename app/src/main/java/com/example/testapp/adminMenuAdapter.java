package com.example.testapp;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.ParcelFileDescriptor;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.Firebase;
import com.google.firebase.database.FirebaseDatabase;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;

import java.util.HashMap;
import java.util.Map;

public class adminMenuAdapter extends FirebaseRecyclerAdapter<adminMenuModel, adminMenuAdapter.myViewHolder> {

    Context context;

    public adminMenuAdapter(@NonNull FirebaseRecyclerOptions<adminMenuModel> options, Context context) {

        super(options);
        this.context = context;
    }

    @Override
    protected void onBindViewHolder(@NonNull myViewHolder holder,int position, @NonNull adminMenuModel model) {
        holder.itemName.setText(model.getName());
        holder.itemDetails.setText(model.getDetails().substring(0,100)+"...");
        holder.itemPrice.setText(model.getPrice());
        Glide.with(holder.itemImage.getContext())
                .load(model.getImage())
                .into(holder.itemImage);

        holder.btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                final DialogPlus dialogPlus = DialogPlus.newDialog(holder.itemImage.getContext())
//                        .setContentHolder(new ViewHolder(R.layout.update_form))
//                        .setExpanded(true,1300)
//                        .create();
//
//                View newView = dialogPlus.getHolderView();
//
//                EditText name = newView.findViewById(R.id.formItemName);
//                EditText price = newView.findViewById(R.id.formItemPrice);
//                EditText details = newView.findViewById(R.id.formItemDetails);
//                EditText image = newView.findViewById(R.id.formItemImage);
//
//                Button btnUpdate = newView.findViewById(R.id.formUpdateButton);

                String childId = FirebaseDatabase.getInstance().getReference().child("products")
                        .child(getRef(holder.getAbsoluteAdapterPosition()).getKey()).toString();
//                Log.i("ChildId",childId);
                Intent toUpdateForm = new Intent(context,update_item.class);
                toUpdateForm.putExtra("itemName",model.getName());
                toUpdateForm.putExtra("itemPrice",model.getPrice());
                toUpdateForm.putExtra("itemDetails",model.getDetails());
                toUpdateForm.putExtra("itemImage",model.getImage());
                toUpdateForm.putExtra("childId",childId);

                toUpdateForm.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(toUpdateForm);

//                name.setText(model.getName());
//                price.setText(model.getPrice());
//                details.setText(model.getDetails());
//                image.setText(model.getImage());
//
//                dialogPlus.show();

//                btnUpdate.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        Map<String,Object> map = new HashMap<>();
//                        map.put("name",name.getText().toString());
//                        map.put("details",details.getText().toString());
//                        map.put("price",price.getText().toString());
//                        map.put("image",image.getText().toString());
//
//                        FirebaseDatabase.getInstance().getReference().child("products")
//                                .child(getRef(holder.getAbsoluteAdapterPosition()).getKey())
//                                .updateChildren(map)
//                                .addOnSuccessListener(new OnSuccessListener<Void>() {
//                                    @Override
//                                    public void onSuccess(Void unused) {
//                                        Toast.makeText(holder.itemName.getContext(),"Data Updated Successfully.",Toast.LENGTH_SHORT).show();
//                                        dialogPlus.dismiss();
//                                    }
//                                })
//
//                                .addOnFailureListener(new OnFailureListener() {
//                                    @Override
//                                    public void onFailure(@NonNull Exception e) {
//                                        Toast.makeText(holder.itemName.getContext(),"Error while updating data.",Toast.LENGTH_SHORT).show();
//                                        dialogPlus.dismiss();
//                                    }
//                         });
//                    }
//                });
            }
        });

        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(holder.itemName.getContext());
                builder.setTitle("Delete Data");
                builder.setMessage("Are you sure you want to delete?");

                builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        FirebaseDatabase.getInstance().getReference().child("products")
                                .child(getRef(holder.getAbsoluteAdapterPosition()).getKey())
                                .removeValue();

                        Toast.makeText(holder.itemName.getContext(),"Item deleted successfully...",Toast.LENGTH_LONG).show();
                    }
                });

                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(holder.itemName.getContext(),"Operation Cancelled",Toast.LENGTH_LONG).show();
                    }
                });

                builder.show();
            }
        });
    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.admin_menu_item,parent,false);
        return new myViewHolder(view);
    }

    class myViewHolder extends RecyclerView.ViewHolder{

        ImageView itemImage;
        TextView itemName,itemDetails,itemPrice;

        Button btnEdit,btnDelete;

        public myViewHolder(@NonNull View itemView) {
            super(itemView);

            itemImage = itemView.findViewById(R.id.image);
            itemName = itemView.findViewById(R.id.itemName);
            itemDetails = itemView.findViewById(R.id.itemDetails);
            itemPrice = itemView.findViewById(R.id.itemPrice);

            btnEdit=itemView.findViewById(R.id.editBtn);
            btnDelete = itemView.findViewById(R.id.deleteBtn);

        }
    }
}
