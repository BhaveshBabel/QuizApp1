package com.example.admin.quizapp.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.admin.quizapp.Common.Common;
import com.example.admin.quizapp.Model.Category;
import com.example.admin.quizapp.QuestionActivity;
import com.example.admin.quizapp.R;

import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.MyViewHolder>{

    Context context;
    List<Category> categories;

    public CategoryAdapter(Context context, List<Category> categories) {
        this.context = context;
        this.categories = categories;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.layout_category_item, viewGroup, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        myViewHolder.txt_category.setText(categories.get(i).getName());
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        CardView card_category;
        TextView txt_category;

        public MyViewHolder(@NonNull View itemView){
            super(itemView);
            card_category = (CardView)itemView.findViewById(R.id.card_category);
            txt_category = (TextView)itemView.findViewById(R.id.txt_category);

            card_category.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {
                    Common.selectedCategory = categories.get(getAdapterPosition()); //assign current category
                    Intent intent = new Intent(context, QuestionActivity.class);
                    context.startActivity(intent);
                }
            });
        }
    }
}
