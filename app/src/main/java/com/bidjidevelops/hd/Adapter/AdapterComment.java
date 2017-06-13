package com.bidjidevelops.hd.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bidjidevelops.hd.Comment;
import com.bidjidevelops.hd.Gson.GsonComment;
import com.bidjidevelops.hd.Helper;
import com.bidjidevelops.hd.Profile;
import com.bidjidevelops.hd.R;
import com.bumptech.glide.Glide;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by You on 20/04/2017.
 */

public class AdapterComment extends RecyclerView.Adapter<AdapterComment.ViewHolder> {
    Context context;
    public List<GsonComment.Commentar> DataComment;

    public AdapterComment(Comment listener, List<GsonComment.Commentar> DataComment) {

        context = listener;
        this.DataComment = DataComment;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_coment, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final AdapterComment.ViewHolder holder, final int position) {
        holder.imguser_comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent a = new Intent(v.getContext(), Profile.class);
                a.putExtra("iduser",DataComment.get(position).idUser);
                a.putExtra("imageuser",DataComment.get(position).image);
                a.putExtra("usernam",DataComment.get(position).username);
                a.putExtra("desk",DataComment.get(position).desk);
                a.putExtra("email",DataComment.get(position).email);
                a.putExtra("sekolah",DataComment.get(position).school);
                a.putExtra("sekolah",DataComment.get(position).school);
                a.putExtra("coverimg",DataComment.get(position).coverimg);
                v.getContext().startActivity(a);
            }
        });
        holder.txtcoment_coment.setText(DataComment.get(position).commentarnya);
        holder.txtcoment_comentall.setText(DataComment.get(position).commentarnya);
        holder.txtcoment_coment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.txtcoment_comentall.setVisibility(View.VISIBLE);
                holder.txtcoment_coment.setVisibility(View.GONE);
            }
        });
        holder.txtcoment_comentall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.txtcoment_comentall.setVisibility(View.GONE);
                holder.txtcoment_coment.setVisibility(View.VISIBLE);
            }
        });
        holder.txtusername_comment.setText(DataComment.get(position).username); Glide.with(context)
                .load(Helper.BASE_IMGUS+DataComment.get(position).image)
                .crossFade()
                .placeholder(R.drawable.student)
                .into(holder.imguser_comment);

    }

    @Override
    public int getItemCount() {
      return DataComment.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtusername_comment, txtsekolah_comment, txtcoment_coment,txtcoment_comentall;
        CircleImageView imguser_comment, ivComment, imguser;

        public ViewHolder(View itemView) {
            super(itemView);
            txtcoment_coment = (TextView) itemView.findViewById(R.id.txtcoment_coment);
            txtusername_comment = (TextView) itemView.findViewById(R.id.txtusername_comment);
            txtcoment_comentall = (TextView) itemView.findViewById(R.id.txtcomment_comentall);
            imguser_comment = (CircleImageView) itemView.findViewById(R.id.imguser_comment);
        }
    }
}
