package com.example.crudappjava.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.crudappjava.R;
import com.example.crudappjava.databinding.UserListRowBinding;
import com.example.crudappjava.model.User;
import com.squareup.picasso.Picasso;

import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserViewHolder> {

    private List<User> mUserList;
    private Context mContext;
    private View.OnClickListener mOnClickListener;
    private LayoutInflater layoutInflater;

    public UserAdapter(List<User> mUserList, Context mContext, View.OnClickListener mOnClickListener) {
        this.mUserList = mUserList;
        this.mContext = mContext;
        this.mOnClickListener = mOnClickListener;
        layoutInflater = LayoutInflater.from(mContext);
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        UserListRowBinding userListRowBinding = DataBindingUtil.inflate(layoutInflater,R.layout.user_list_row,parent,false);
        return new UserViewHolder(userListRowBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        holder.setData(position);
    }

    @Override
    public int getItemCount() {
        return mUserList.size();
    }

    public class UserViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final UserListRowBinding mBinding;
        public UserViewHolder(@NonNull UserListRowBinding itemBinding) {
            super(itemBinding.getRoot());
            this.mBinding = itemBinding;
        }

        private int mPosition;
        public void setData(int position) {
            this.mPosition = position;
            User user = mUserList.get(position);
            mBinding.txtName.setText(user.getFirstName()+" "+user.getLastName());
            mBinding.txtEmail.setText(user.getEmail());
            Picasso.with(mContext).load(user.getAvatar()).into(mBinding.imgUser);
            mBinding.getRoot().setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            view.setTag(mPosition);
            mOnClickListener.onClick(view);
        }
    }
}
