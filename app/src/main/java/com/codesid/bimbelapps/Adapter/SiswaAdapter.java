package com.codesid.bimbelapps.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.codesid.bimbelapps.DetailSiswaActivity;
import com.codesid.bimbelapps.Model.Siswa;
import com.codesid.bimbelapps.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

public class SiswaAdapter extends FirebaseRecyclerAdapter<Siswa,SiswaAdapter.siswaViewHolder> {
    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    Context context;
    public SiswaAdapter(Context context, @NonNull FirebaseRecyclerOptions<Siswa> options) {
        super(options);
        this.context = context;

    }

    @Override
    protected void onBindViewHolder(@NonNull siswaViewHolder holder, int position, @NonNull Siswa model) {
        holder.tv_nama.setText(model.getNama());
        holder.tv_ttl.setText(model.getTtl());
        holder.ll_content.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DetailSiswaActivity.class);
                intent.putExtra("nama",model.getNama());
                intent.putExtra("ttl",model.getTtl());
                intent.putExtra("paket",model.getPaket());
                intent.putExtra("tingkat",model.getTingkat());
                intent.putExtra("url",model.getUrlFile());
                intent.putExtra("key",model.getKey());
                context.startActivity(intent);
            }
        });


    }


    @NonNull
    @Override
    public siswaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view
                = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.	frogo_rv_list_type_2, parent, false);
        return new SiswaAdapter.siswaViewHolder(view);
    }

    public class siswaViewHolder extends RecyclerView.ViewHolder {
        TextView tv_nama,tv_ttl;
        LinearLayout ll_content;
        public siswaViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_nama = itemView.findViewById(R.id.frogo_rv_list_type_2_tv_title);
            tv_ttl = itemView.findViewById(R.id.frogo_rv_list_type_2_tv_subtitle);
            ll_content = itemView.findViewById(R.id.frogo_rv_list_type_2_root_linear_layout);
        }
    }
}
