/*
 * Copyright (C) 2015 Paul Burke
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.zf.lottery;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.TextView;

import com.zf.common.widget.dragdrop.ItemTouchHelperAdapter;
import com.zf.lottery.parse.Lottery;
import com.zf.lottery.parse.LotteryGroup;
import com.zf.lottery.parse.LotteryInfoRequest;
import com.zf.lottery.parse.LotteryInfoResolveListener;

public class LotteryListAdapter extends RecyclerView.Adapter<LotteryListAdapter.ItemViewHolder> implements
        ItemTouchHelperAdapter {
    private LotteryGroup group;
    private LotteryInfoRequest request = null;

    public LotteryListAdapter(Context context, LotteryGroup group) {
        this.group = group;
        request = new LotteryInfoRequest();
    }

    @Override
    public ItemViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {
        //addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL_LIST));
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ExpandableListView view = (ExpandableListView) inflater.inflate(R.layout.list_lottery, parent, false);
        ItemViewHolder itemViewHolder = new ItemViewHolder(view);
        return itemViewHolder;
    }

    @Override
    public void onBindViewHolder(final ItemViewHolder holder, int position) {
//        Uri uri = Uri.parse("http://www.opencai.net/static/v2/logos/dlt.gif");
//        holder.imageView.setImageURI(uri);
        request.request(group.getLottery(position), new LotteryInfoResolveListener() {
            @Override
            public void onResolved(Lottery lottery) {
                //((ExpandableListView) holder.itemView).setAdapter(new LotteryItemAdapter(lottery));
            }
        });
    }

    @Override
    public void onItemDismiss(int position) {
        notifyItemRemoved(position);
    }

    @Override
    public boolean onItemMove(int fromPosition, int toPosition) {
        notifyItemMoved(fromPosition, toPosition);
        return true;
    }

    @Override
    public int getItemCount() {
        return group.getLotteries().size();
    }

    public static class ItemViewHolder extends RecyclerView.ViewHolder {
        public final TextView nameView;
        public final TextView phaseView;
        public final TextView dateView;
        public final TextView redNumberView;
        public final TextView blueNumberView;
        //public final SimpleDraweeView imageView;

        public ItemViewHolder(View itemView) {
            super(itemView);
            nameView = (TextView) itemView.findViewById(R.id.nameView);
            phaseView = (TextView) itemView.findViewById(R.id.phaseView);
            dateView = (TextView) itemView.findViewById(R.id.dateView);
            redNumberView = (TextView) itemView.findViewById(R.id.redNumberView);
            blueNumberView = (TextView) itemView.findViewById(R.id.blueNumberView);
            //imageView = (SimpleDraweeView) itemView.findViewById(R.id.imageView);
        }
    }
}
