package com.attaartechs.ezypasswordmanager.adapters;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.attaartechs.ezypasswordmanager.R;
import com.attaartechs.ezypasswordmanager.Utils.AppConstants;
import com.attaartechs.ezypasswordmanager.models.Category;

import java.util.EventListener;
import java.util.List;

/**
 * Created by Muhammad Hassaan on 10/19/2019.
 */



import android.content.Context;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

        import androidx.recyclerview.widget.RecyclerView;

        import com.attaartechs.ezypasswordmanager.R;
        import com.attaartechs.ezypasswordmanager.models.Category;
import com.attaartechs.ezypasswordmanager.models.Password;
import com.balysv.materialripple.MaterialRippleLayout;

import java.util.EventListener;
        import java.util.List;

import static android.content.Context.CLIPBOARD_SERVICE;

/**
 * Created by Muhammad Hassaan on 10/19/2019.
 */
public class PasswordsAdapter extends RecyclerView.Adapter<PasswordsAdapter.ViewHolder> {

    private List<Password> mData;
    private Context myContext;
    // data is passed into the constructor
    public PasswordsAdapter(Context context, List<Password> data) {
        this.myContext = context;
        this.mData = data;
    }


    // inflates the row layout from xml when needed
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(myContext).inflate(R.layout.item_password, parent, false);
        return new ViewHolder(view);
    }

    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

        final Password password = mData.get(position);


        holder.textPasswordName.setText(password.csName);
        holder.textPassword.setText(password.csPassword);

        if(password.isCategorized())
        {
            holder.imagePassword.setImageDrawable(myContext.getResources().getDrawable(AppConstants.listCategory.get(password.categoryId-1).nDrawableId));
        }
        else
        {
            holder.imagePassword.setImageDrawable(myContext.getResources().getDrawable(R.drawable.padlock));
        }
        holder.btnCopy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(categoryListener != null)
                {
                    categoryListener.onCopyClicked(password.csPassword);
                }

            }
        });

       holder.rootView.setOnLongClickListener(new View.OnLongClickListener() {
           @Override
           public boolean onLongClick(View v) {

               if(categoryListener!= null)
               {
                   categoryListener.onLongClicked(position);
               }

               return false;
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
        TextView textPasswordName;

        View rootView;
        TextView textPassword;

        View btnCopy;

        ImageView imagePassword;
        ViewHolder(View itemView) {
            super(itemView);

            textPassword = itemView.findViewById(R.id.textPassword);
            textPasswordName = itemView.findViewById(R.id.textPasswordName);
            btnCopy = itemView.findViewById(R.id.btnCopyPassword);
            imagePassword = itemView.findViewById(R.id.imagePassword);
            rootView = itemView.findViewById(R.id.rootView);

        }


    }

    // convenience method for getting data at click position

    // allows clicks events to be caught
    private PasswordListener  categoryListener;

    public void setListener(PasswordListener listener)
    {
        categoryListener = listener;
    }
    public interface PasswordListener extends EventListener
    {
        void onPasswordClicked(Category category,int nposition);
        void onLongClicked(int nPosition);
        void onCopyClicked(String csTextToCopy);
    }
}