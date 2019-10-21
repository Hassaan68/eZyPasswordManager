package com.attaartechs.ezypasswordmanager.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.attaartechs.ezypasswordmanager.R;
import com.attaartechs.ezypasswordmanager.models.Category;

import java.util.EventListener;
import java.util.List;

/**
 * Created by Muhammad Hassaan on 10/19/2019.
 */
public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {

    private List<Category> mData;
    private Context myContext;
    // data is passed into the constructor
    public CategoryAdapter(Context context, List<Category> data) {
        this.myContext = context;
        this.mData = data;
    }


    // inflates the row layout from xml when needed
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(myContext).inflate(R.layout.category_item, parent, false);
        return new ViewHolder(view);
    }

    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

        final Category category = mData.get(position);

        holder.mCategoryImage.setImageDrawable(myContext.getResources().getDrawable(category.nDrawableId));

        if(category.isChecked)
        {
            holder.imageChecked.setVisibility(View.VISIBLE);
        }
        else
        {
            holder.imageChecked.setVisibility(View.GONE);
        }
        holder.mCategoryImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(categoryListener != null)
                {
                    categoryListener.onCategoryClicked(category,position);
                }
            }
        });
    }

    // total number of rows
    @Override
    public int getItemCount() {
        return mData.size();
    }


    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder  {
        ImageView mCategoryImage;

        ImageView imageChecked;
        ViewHolder(View itemView) {
            super(itemView);
            mCategoryImage = itemView.findViewById(R.id.imageCategory);
            imageChecked = itemView.findViewById(R.id.imageChecked);
        }


    }

    // convenience method for getting data at click position

    // allows clicks events to be caught
    private CategoryListener  categoryListener;

    public void setListener(CategoryListener listener)
    {
        categoryListener = listener;
    }
    public interface CategoryListener extends EventListener
    {
        void onCategoryClicked(Category category,int nposition);
    }
}