package com.example.noteapp281.adapter;

//对话框是提示用户做出决定或输入更多信息的小窗口。对话框不会占据整个屏幕，通常适用于需要用户进行操作才能继续执行的模态框事件。
import android.app.Dialog;
import android.content.Context;
//它是一种运行时绑定（run-time binding）机制
//使用Context.startActivity()或Activity.startActivityForResult()，传入一个intent来启动一个activity。
//使用Activity.setResult()，传入一个intent来从activity中返回结果。
//将intent对象传给Context.startService()来启动一个service或者传消息给一个运行的service。
//将intent对象传给Context.bindService()来绑定一个service。
//将intent对象传给Context.sendBroadcast(),Context.sendOrderedBroadcast(),
// 或者Contex.sendStickyBroadcast()方法，则它们被传给 broadcast receiver。
//Intent由以下各个组成部分:
//
//component(组件) :目的组件
//Action (动作) :用来表现意图的行动
//category (类别) :用来表现动作的类别
//data (数据) :表示与动作要操纵的数据
//type (数据类型) :对于data范例的描写
//extras (扩展信息) :扩展信息
//Flags (标志位) :期望这个意图的运行模式
//显式 Intent 是指用于启动某个特定应用组件    Intent intent = new Intent(MainActivity.this,YourActivity.class);
//    startActivity(intent);可以看到，构建了一个Intent，传入了 MainActivity.this（上下文），
//    YourActivity.class（目标活动，即component），即在MainActivity.this之上启动目标活动，
//    如此一来，Intent非常明确，即称之为显示Intent。
//Intent的发送者在构造Intent对象时，通过一定的设置，由系统进行筛选，需要借助Intent Filter（过滤器)来实现“筛选”这一过程
//    //显式（指定组件名称）
//    public void componentClick(View view){
//        Intent intent = new Intent();
//        ComponentName componentName = new ComponentName(this,Main2Activity.class);
//        intent.setComponent(componentName);
//        //等价于 Intent intent = new Intent(this,Main2Activity.class);
//        startActivity(intent);
//    }
import android.content.Intent;

import android.view.LayoutInflater;
//Android中的View类代表用户界面中基本的构建块。。一个View在屏幕中占据一个矩形区域、并且负责绘制和事件处理。
//创建:构造方法:创建view时，分为两种情况，一种是写代码创建，一种是从xml文件中创建，第二种情况会转化和应用你在xml设置的view属性。
//onFinishInflate():在该view和子view都从xml文件中创建完成时被调用。
//布局	onMeasure(int, int)	确定view和子view的大小要求时被调用。
//onLayout(boolean, int, int, int, int)	给view的子view确定大小和位置的时候调用。
//onSizeChanged(int, int, int, int)	当view的大小变化时被调用
//绘制	onDraw(android.graphics.Canvas)	当view需要渲染他的内容时被调用。
//事件处理	onKeyDown(int, android.view.KeyEvent)	当物理按键按下时，被调用。
//onKeyUp(int, android.view.KeyEvent)	当物理按键抬起时，被调用。
//onTrackballEvent(android.view.MotionEvent)	当轨迹球运动事件发生时，被调用。
//onTouchEvent(android.view.MotionEvent)	当触摸屏幕事件发生时，被调用。
//焦点	onFocusChanged(boolean, int, android.graphics.Rect)	获得、失去焦点时，被调用。
//onWindowFocusChanged(boolean)	包含视图的屏幕获得、失去焦点的时候被调用。
//附着	onAttachedToWindow()	当view附着到window时，被调用。
//onDetachedFromWindow()	当view从window上剥离，被调用。
//onWindowVisibilityChanged(int)	包含视图的window的可见性发生变化时，别调用。
import android.view.View;
//
import android.view.ViewGroup;
//
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.noteapp281.EditActivity;
import com.example.noteapp281.NoteDbOpenHelper;
import com.example.noteapp281.R;
import com.example.noteapp281.bean.Note;
import com.example.noteapp281.util.ToastUtil;

import java.util.List;
//在指定了泛型为RecyclerHoler后，
// 方法2也会根据泛型改变onBindViewHolder(RecyclerHolder holder, int position)
public class MyAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
//定义一个数组队列类型为<Note> 类型。
    private List<Note> mBeanList;
    //LayoutInflater是一个用于将xml布局文件加载为View或者ViewGroup对象的工具，我们可以称之为布局加载器。
//首先要注意LayoutInflater本身是一个抽象类，我们不可以直接通过new的方式去获得它的实例，通常有下面三种方式
//LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//LayoutInflater inflater = LayoutInflater.from(context);
//在Activity内部调用getLayoutInflater()方法
    private LayoutInflater mLayoutInflater;
//Context描述一个应用程序环境的信息（即上下文）；是一个抽象类，Android提供了该抽象类的具体实现类；
// 通过它我们可以获取应用程序的资源和类（包括应用级别操作，如启动Activity，发广播，接受Intent等）。
    private Context mContext;

    private NoteDbOpenHelper mNoteDbOpenHelper;
//整型变量
    private int viewType;
    //整型常量
    public static int TYPE_LINEAR_LAYOUT = 0;
    public static int TYPE_GRID_LAYOUT = 1;

    public MyAdapter(Context context, List<Note> mBeanList){
        this.mBeanList = mBeanList;
        this.mContext = context;
        mLayoutInflater = LayoutInflater.from(mContext);
        mNoteDbOpenHelper = new NoteDbOpenHelper(mContext);
    }

    public void setViewType(int viewType) {
        this.viewType = viewType;
    }

    @Override
    public int getItemViewType(int position) {
        return viewType;
    }

    @NonNull
    @Override
    //ViewGroup parent：可以简单理解为item的根ViewGroup，item的子控件加载在其中
    //int viewType：item的类型，可以根据viewType来创建不同的ViewHolder，来加载不同的类型的item
    //这个方法就是用来创建出一个新的ViewHolder，可以根据需求的itemType，创建出多个ViewHolder。
    // 创建多个itemType时，需要getItemViewType(int position)方法配合
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(viewType == TYPE_LINEAR_LAYOUT){
            //就是传递要转换的 xml 布局过来。
            //取值组合	ViewGroup root	boolean attachToRoot
           // 第一组	notNull	false   	返回的是添加了根节点 View 对象以及布局参数的 root 对象。
           // 第二组	notNull	true        	返回的是没有布局参数信息的根节点 View 对象。
            //第三组	null	false
            //第四组	null	true         	返回的是没有布局参数信息的根节点 View 对象。
            View view = mLayoutInflater.inflate(R.layout.list_item_layout, parent, false);
            MyViewHolder myViewHolder = new MyViewHolder(view);
            return myViewHolder;
        }else if(viewType == TYPE_GRID_LAYOUT){
            View view = mLayoutInflater.inflate(R.layout.list_item_grid_layout, parent, false);
            MyGridViewHolder myGridViewHolder = new MyGridViewHolder(view);
            return myGridViewHolder;
        }

        return null;
    }
//VH holder：就是在onCreateViewHolder()方法中，创建的ViewHolder
//int position：item对应的DataList数据源集合的postion
//postion就是adapter position，RecycelrView中item的数量，
// 就是根据DataList数据源集合的数量来创建的
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder == null) {
            return;
        }
        if(holder instanceof MyViewHolder){
            bindMyViewHolder((MyViewHolder) holder, position);
        } else if (holder instanceof MyGridViewHolder) {
            bindGridViewHolder((MyGridViewHolder) holder, position);
        }
    }

    private void bindMyViewHolder(MyViewHolder holder, int position) {
        Note note = mBeanList.get(position);
        holder.mTvTitle.setText(note.getTitle());
        holder.mTvContent.setText(note.getContent());
        holder.mTvTime.setText(note.getCreatedTime());
        holder.rlContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(mContext, EditActivity.class);
                intent.putExtra("note", note);
                mContext.startActivity(intent);
            }
        });

        holder.rlContainer.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                // 弹出弹窗
                Dialog dialog = new Dialog(mContext, android.R.style.ThemeOverlay_Material_Dialog_Alert);
                View dialogView = mLayoutInflater.inflate(R.layout.list_item_dialog_layout, null);
                TextView tvDelete = dialogView.findViewById(R.id.tv_delete);
                TextView tvEdit = dialogView.findViewById(R.id.tv_edit);

                tvDelete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int row = mNoteDbOpenHelper.deleteFromDbById(note.getId());
                        if (row > 0) {
                            removeData(position);
                        }
                        dialog.dismiss();
                    }
                });


                tvEdit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(mContext, EditActivity.class);
                        intent.putExtra("note", note);
                        mContext.startActivity(intent);
                        dialog.dismiss();
                    }
                });
                dialog.setContentView(dialogView);
                dialog.setCanceledOnTouchOutside(true);
                dialog.show();
                return true;
            }
        });
    }


    private void bindGridViewHolder(MyGridViewHolder holder, int position) {
        Note note = mBeanList.get(position);
        holder.mTvTitle.setText(note.getTitle());
        holder.mTvContent.setText(note.getContent());
        holder.mTvTime.setText(note.getCreatedTime());
        holder.rlContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(mContext, EditActivity.class);
                intent.putExtra("note", note);
                mContext.startActivity(intent);
            }
        });

        holder.rlContainer.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                // 弹出弹窗
                Dialog dialog = new Dialog(mContext, android.R.style.ThemeOverlay_Material_Dialog_Alert);
                View dialogView = mLayoutInflater.inflate(R.layout.list_item_dialog_layout, null);
                TextView tvDelete = dialogView.findViewById(R.id.tv_delete);
                TextView tvEdit = dialogView.findViewById(R.id.tv_edit);

                tvDelete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int row = mNoteDbOpenHelper.deleteFromDbById(note.getId());
                        if (row > 0) {
                            removeData(position);
                        }
                        dialog.dismiss();
                    }
                });


                tvEdit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(mContext, EditActivity.class);
                        intent.putExtra("note", note);
                        mContext.startActivity(intent);
                        dialog.dismiss();
                    }
                });
                dialog.setContentView(dialogView);
                dialog.setCanceledOnTouchOutside(true);
                dialog.show();
                return true;
            }
        });
    }
//这个方法的返回值，便是RecyclerView中实际item的数量。
// 有些情况下，当增加了HeaderView或者FooterView后，需要注意考虑这个返回值
    @Override
    public int getItemCount() {
        return mBeanList.size();
    }

    public void refreshData(List<Note> notes) {
        this.mBeanList = notes;
        notifyDataSetChanged();
    }

    public void removeData(int pos) {
        mBeanList.remove(pos);
        notifyItemRemoved(pos);
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        TextView mTvTitle;
        TextView mTvContent;
        TextView mTvTime;
        ViewGroup rlContainer;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            this.mTvTitle = itemView.findViewById(R.id.tv_title);
            this.mTvContent = itemView.findViewById(R.id.tv_content);
            this.mTvTime = itemView.findViewById(R.id.tv_time);
            this.rlContainer = itemView.findViewById(R.id.rl_item_container);
        }
    }

    class MyGridViewHolder extends RecyclerView.ViewHolder{

        TextView mTvTitle;
        TextView mTvContent;
        TextView mTvTime;
        ViewGroup rlContainer;

        public MyGridViewHolder(@NonNull View itemView) {
            super(itemView);
            this.mTvTitle = itemView.findViewById(R.id.tv_title);
            this.mTvContent = itemView.findViewById(R.id.tv_content);
            this.mTvTime = itemView.findViewById(R.id.tv_time);
            this.rlContainer = itemView.findViewById(R.id.rl_item_container);
        }
    }
}
