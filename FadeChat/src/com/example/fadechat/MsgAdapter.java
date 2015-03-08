package com.example.fadechat;

import java.util.List;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
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
			viewHolder.leftFadeLayout=(LinearLayout) view.findViewById(R.id.left_fade_layout);

			viewHolder.timer_right= (LinearLayout) view.findViewById(R.id.timer_right);
			viewHolder.timer_left= (LinearLayout) view.findViewById(R.id.timer_left);
			
			viewHolder.leftMsg = (TextView) view.findViewById(R.id.left_msg);
			viewHolder.rightMsg = (TextView) view.findViewById(R.id.right_msg);

			viewHolder.leftFadeMsg= (TextView) view.findViewById(R.id.left_fade_msg);
			viewHolder.rightFadeMsg= (TextView) view.findViewById(R.id.right_fade_msg);

			viewHolder.timeMsg_right= (TextView) view.findViewById(R.id.time_right);
			viewHolder.timeMsg_left= (TextView) view.findViewById(R.id.time_left);
			
			
			view.setTag(viewHolder);
		} else {
			view = convertView;
			viewHolder = (ViewHolder) view.getTag();
		}
		
		if (msg.getType() == Msg.TYPE_RECEIVED) {
		
			
			//메세지를 받았을 때 좌측 레이아웃을 보이게하고 우측 레이아웃을 넘기고 좌측 메세지를 갖고오기
			
			//fade off 일 때 메세지 리시브
			if(msg.getTimer()==0)
			{
				viewHolder.leftFadeLayout.setVisibility(View.GONE);
				viewHolder.rightFadeLayout.setVisibility(View.GONE);
			
				viewHolder.timer_right.setVisibility(View.GONE);
				viewHolder.timer_left.setVisibility(View.GONE);
				
				viewHolder.leftLayout.setVisibility(View.VISIBLE);
				viewHolder.rightLayout.setVisibility(View.GONE);
				
				viewHolder.leftMsg.setText(msg.getContent());
				viewHolder.leftMsg.setPaintFlags(viewHolder.leftMsg.getPaintFlags() | Paint.FAKE_BOLD_TEXT_FLAG);

				viewHolder.leftMsg.setTextColor(Color.BLACK);

			
			}
			//fade on 일 때 메세지 리시브
			else
			{
				viewHolder.leftLayout.setVisibility(View.GONE);
				viewHolder.rightLayout.setVisibility(View.GONE);

				viewHolder.leftFadeLayout.setVisibility(View.VISIBLE);
				viewHolder.rightFadeLayout.setVisibility(View.GONE);
				
				viewHolder.timer_left.setVisibility(View.VISIBLE);
				viewHolder.timer_right.setVisibility(View.GONE);
				

				viewHolder.timeMsg_left.setText(" "+msg.getTimer());
				viewHolder.timeMsg_left.setTextSize(20f);
				viewHolder.timeMsg_left.setTextColor(Color.RED);
				viewHolder.timeMsg_left.setPaintFlags(viewHolder.timeMsg_left.getPaintFlags() | Paint.FAKE_BOLD_TEXT_FLAG);

				viewHolder.leftFadeMsg.setText(msg.getContent());
				viewHolder.leftFadeMsg.setPaintFlags(viewHolder.leftFadeMsg.getPaintFlags() | Paint.FAKE_BOLD_TEXT_FLAG);

				
			}
			/*			원래 리시브.
			viewHolder.leftLayout.setVisibility(View.VISIBLE);
			viewHolder.rightLayout.setVisibility(View.GONE);
			viewHolder.leftMsg.setText(msg.getContent());
			*/
			
			
		} 
		
		else if(msg.getType() == Msg.TYPE_SENT) {
			
			//메세지를 보냈을때 때 우측 레이아웃을 보이게하고 좌측 레이아웃을 넘기고 우측 메세지를 갖고오기

			//fade off 일때 메시지 센트
			if(msg.getTimer()==0)			
			{

				viewHolder.leftFadeLayout.setVisibility(View.GONE);
				viewHolder.rightFadeLayout.setVisibility(View.GONE);
				
				viewHolder.timer_right.setVisibility(View.GONE);
				viewHolder.timer_left.setVisibility(View.GONE);
				
				viewHolder.rightLayout.setVisibility(View.VISIBLE);
				viewHolder.leftLayout.setVisibility(View.GONE);
				
				viewHolder.rightMsg.setText(msg.getContent());
				viewHolder.rightMsg.setPaintFlags(viewHolder.rightMsg.getPaintFlags() | Paint.FAKE_BOLD_TEXT_FLAG);		

			}
			
			//fade on 일때 메시지 센트
			else
			{

				viewHolder.rightLayout.setVisibility(View.GONE);
				viewHolder.leftLayout.setVisibility(View.GONE);
				
				
				viewHolder.timer_right.setVisibility(View.VISIBLE);
				viewHolder.timer_left.setVisibility(View.GONE);
								

				viewHolder.rightFadeLayout.setVisibility(View.VISIBLE);
				viewHolder.leftFadeLayout.setVisibility(View.GONE);

				viewHolder.timeMsg_right.setText(" "+msg.getTimer());
				viewHolder.timeMsg_right.setTextSize(20f);
				viewHolder.timeMsg_right.setTextColor(Color.MAGENTA);
				viewHolder.timeMsg_right.setPaintFlags(viewHolder.timeMsg_right.getPaintFlags() | Paint.FAKE_BOLD_TEXT_FLAG);
				
				viewHolder.rightFadeMsg.setText(msg.getContent());
				viewHolder.rightFadeMsg.setPaintFlags(viewHolder.rightFadeMsg.getPaintFlags() | Paint.FAKE_BOLD_TEXT_FLAG);

			}
			
			
			
		}
		
		return view;
	}
	
	
	
	// 텍스트뷰, 리니어레이아웃 에대한 클래스 (뷰홀더) 생성
	class ViewHolder {
		
		TextView timeMsg_right;

		TextView timeMsg_left;

		
		LinearLayout rightFadeLayout;
		
		LinearLayout leftFadeLayout;

		
		LinearLayout timer_right;
		
		LinearLayout timer_left;


		TextView rightFadeMsg;
		
		TextView leftFadeMsg;

		
		LinearLayout leftLayout;
		
		LinearLayout rightLayout;
		
		
		TextView leftMsg;
		
		TextView rightMsg;
		
	}

}
