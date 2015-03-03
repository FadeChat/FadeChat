package com.example.fadechat;

import java.util.List;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;


public class MsgAdapter extends ArrayAdapter<Msg> {

	private int resourceId;
	
	public MsgAdapter(Context context, int textViewResourceId,
			List<Msg> objects) {
		super(context, textViewResourceId, objects);
		resourceId = textViewResourceId;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		Msg msg = getItem(position);
		View view;
		ViewHolder viewHolder;
		
		
			
		
		if (convertView == null) {
			view = LayoutInflater.from(getContext()).inflate(resourceId, null);
			
			//view들을 관리하는 객체 viewholder 생성
			viewHolder = new ViewHolder();
			//왼쪽 오른쪽의 레이아웃과 Msg내용을 담는 textview를 설정
			viewHolder.leftLayout = (LinearLayout) view.findViewById(R.id.left_layout);
			viewHolder.rightLayout = (LinearLayout) view.findViewById(R.id.right_layout);
			viewHolder.rightFadeLayout= (LinearLayout) view.findViewById(R.id.right_fade_layout);
			viewHolder.timer= (LinearLayout) view.findViewById(R.id.timer);
			
			viewHolder.leftMsg = (TextView) view.findViewById(R.id.left_msg);
			viewHolder.rightMsg = (TextView) view.findViewById(R.id.right_msg);
			viewHolder.rightFadeMsg= (TextView) view.findViewById(R.id.right_fade_msg);
			viewHolder.timeMsg= (TextView) view.findViewById(R.id.time);
			
			
			view.setTag(viewHolder);
		} else {
			view = convertView;
			viewHolder = (ViewHolder) view.getTag();
		}
		
		if (msg.getType() == Msg.TYPE_RECEIVED) {
		
			//메세지를 받았을 때 좌측 레이아웃을 보이게하고 우측 레이아웃을 넘기고 좌측 메세지를 갖고오기
				viewHolder.leftLayout.setVisibility(View.VISIBLE);
				viewHolder.rightLayout.setVisibility(View.GONE);
				viewHolder.leftMsg.setText(msg.getContent());
			
		} 
		
		else if(msg.getType() == Msg.TYPE_SENT) {
			
			//메세지를 보냈을때 때 우측 레이아웃을 보이게하고 좌측 레이아웃을 넘기고 우측 메세지를 갖고오기

			if(msg.getTimer()==0)			
			{

				viewHolder.rightFadeLayout.setVisibility(View.GONE);
				viewHolder.timer.setVisibility(View.GONE);
				
				viewHolder.rightLayout.setVisibility(View.VISIBLE);
				viewHolder.leftLayout.setVisibility(View.GONE);
				viewHolder.rightMsg.setText(msg.getContent());
				
			}
			else
			{

				viewHolder.rightLayout.setVisibility(View.GONE);
				viewHolder.timer.setVisibility(View.VISIBLE);
				

				viewHolder.rightFadeLayout.setVisibility(View.VISIBLE);
				viewHolder.leftLayout.setVisibility(View.GONE);
				viewHolder.timeMsg.setText(" "+msg.getTimer());
				viewHolder.timeMsg.setTextSize(20f);
				viewHolder.timeMsg.setTextColor(Color.RED);
				viewHolder.rightFadeMsg.setText(msg.getContent());
				
			}
			
			
			
		}
		
		return view;
	}
	
	class ViewHolder {
		
		public TextView timeMsg;

		public LinearLayout rightFadeLayout;

		LinearLayout timer;

		public TextView rightFadeMsg;

		LinearLayout leftLayout;
		
		LinearLayout rightLayout;
		
		
		
		TextView leftMsg;
		
		TextView rightMsg;
		
	}

}
