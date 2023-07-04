package com.example.noteapp281;
//ContentValues类和Hashtable比较类似，
// 它也是负责存储一些名值对，但是它存储的名值对当中的名是一个String类型，而值都是基本类型。
//ContentValues initialValues = new ContentValues()语句实例化一个contentValues类。
//initialValues.put（KEY_TITLE, title）语句将列名和对应的列值放置到initialValues里边。
//mDb.insert（DATABASE_TABLE, null, initialValues）语句负责插入一条新的纪录，
// 如果插入成功则会返回这条记录的id，如果插入失败会返回-1。
import android.content.ContentValues;
//Context描述一个应用程序环境的信息（即上下文）；是一个抽象类，Android提供了该抽象类的具体实现类；
// 通过它我们可以获取应用程序的资源和类（包括应用级别操作，如启动Activity，发广播，接受Intent等）。
import android.content.Context;
//Cursor 是每行的集合。使用 moveToFirst()定位第一行。
// 你必须知道每一列的名称。你必须知道每一列的数据类型。Cursor是一个随机的数据源。所有的数据都是通过下标取得。
//·close()——关闭游标，释放资源
        //·copyStringToBuffer(int columnIndex, CharArrayBufferbuffer)——在缓冲区中检索请求的列的文本，将将其存储
        //·getColumnCount()——返回所有列的总数
       // ·getColumnIndex(String columnName)——返回指定列的名称，如果不存在返回-1
        //·getColumnIndexOrThrow(StringcolumnName)——从零开始返回指定列名称，如果不存在将抛出IllegalArgumentException 异常。
       // ·getColumnName(int columnIndex)——从给定的索引返回列名
      //  ·getColumnNames()——返回一个字符串数组的列名
       // ·getCount()——返回Cursor 中的行数
       // ·moveToFirst()——移动光标到第一行
       // ·moveToLast()——移动光标到最后一行
       // ·moveToNext()——移动光标到下一行
      //  ·moveToPosition(int position)——移动光标到一个绝对的位置
      //  ·moveToPrevious()——移动光标到上一行
import android.database.Cursor;

import android.database.sqlite.SQLiteDatabase;
//SQLiteOpenHelper重点在于helper，他是SQLiteDatabase的一个帮助类，
        //便于开发者实现对SQLite的数据进行写入（增删改）和读取（查询）。
//SQLiteOpenHelper具体有create方法，
/*
* public SQLiteOpenHelper(@Nullable Context context,上下文
                        @Nullable String name,数据库名称
                        @Nullable CursorFactory factory,游标工厂
                        int version)版本号
    {                   null:无需考虑
        this(context, name, factory, version, null);
    }
    * */
import android.database.sqlite.SQLiteOpenHelper;

import android.text.TextUtils;

import com.example.noteapp281.bean.Note;
//ArrayList 类是一个可以动态修改的数组，与普通数组的区别就是它是没有固定大小的限制，我们可以添加或删除元素。
//ArrayList 继承了 AbstractList ，并实现了 List 接口。
import java.util.ArrayList;
import java.util.List;

public class NoteDbOpenHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "noteSQLite.db";//数据库名字
    private static final String TABLE_NAME_NOTE = "note";//表名

    private static final String CREATE_TABLE_SQL = "create table " +
            TABLE_NAME_NOTE + " (id integer primary key autoincrement, " +
            "title text, content text, create_time text)";
//创建表：主键并且自动增加，有title，content，create_time文本类型的字段。
//构造方法
    public NoteDbOpenHelper(Context context) {
        super(context, DB_NAME, null, 1);
    }

//它的作用是数据库第一次创建时，进入执行；一般将创建表的sql语句写进其中。
//一个参数：当前连接或者创建的SQLiteDatabase对象
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_SQL);
        //执行一条sql语句
        //execSQL(String sql)
    }

    //它的作用是数据库的版本号更新（增加时），进入执行；一般将修改表整体结构的sql语句写入其中。
    //三个参数：当前连接或者创建的SQLiteDatabase对象、旧版本号、新版本号
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
//插入字段
    public long insertData(Note note) {
//将数据写入数据库
        SQLiteDatabase db = getWritableDatabase();
//调用Note中的函数，函数原型：
        //public String getTitle() {
        //        return title;
        //    }
        //
        //    public void setTitle(String title) {
        //        this.title = title;
        //    }
        ContentValues values = new ContentValues();
        values.put("title", note.getTitle());
        values.put("content", note.getContent());
        values.put("create_time", note.getCreatedTime());
        //插入一条记录
        //insert(String table,String nullColumnHack,ContentValues  values)
        // 参数1  表名称，
        // 参数2  空列的默认值
        // 参数3  ContentValues类型的一个封装了列名称和列值的Map；
        //②编写插入数据的SQL语句，直接调用SQLiteDatabase的execSQL()方法来执行
        //String stu_sql="insert into stu_table(sname,snumber) values('xiaoming','01005')";
        return db.insert(TABLE_NAME_NOTE, null, values);
    }

    public int deleteFromDbById(String id) {
        SQLiteDatabase db = getWritableDatabase();
        //删除一条记录
        //delete(String table,String whereClause,String[]  whereArgs)
       // ①调用SQLiteDatabase的delete(String table,String whereClause,String[]  whereArgs)方法
        //参数1  表名称
        //参数2  删除条件
        //参数3  删除条件值数组
        //②编写删除SQL语句，调用SQLiteDatabase的execSQL()方法来执行删除。
        return db.delete(TABLE_NAME_NOTE, "id like ?", new String[]{id});
    }
//更改数据
    public int updateData(Note note) {

        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("title", note.getTitle());
        values.put("content", note.getContent());
        values.put("create_time", note.getCreatedTime());
        //①调用SQLiteDatabase的update(String table,ContentValues values,String  whereClause, String[]  whereArgs)方法
        //参数1  表名称
        //参数2  跟行列ContentValues类型的键值对Key-Value
        //参数3  更新条件（where字句）
        //参数4  更新条件数组
        //②编写更新的SQL语句，调用SQLiteDatabase的execSQL执行更新。
        //修改记录
//update(String table,ContentValues values,String whereClause,String[]  whereArgs)
        return db.update(TABLE_NAME_NOTE, values,
                "id like ?", new String[]{note.getId()});
    }

    public List<Note> queryAllFromDb() {
//创建数据库库
        //在设计模式中有对依赖倒置原则。程序要尽量依赖于抽象，不依赖于具体。
        // 从Java语法上，这种方式是使用接口引用指向具体实现。
        //面向接口编程
        //提高程序宽展性,以后修改维护好些
// ArrayList<E> objectName =new ArrayList<>();　 // 初始化
//E: 泛型数据类型，用于设置 objectName 的数据类型，只能为引用数据类型。
// objectName: 对象名。ArrayList 是一个数组队列，提供了相关的添加、删除、修改、遍历等功能。
        SQLiteDatabase db = getWritableDatabase();
        //List是接口，它是不可以被实例化的（接口是个抽象类）所以必须以它的实现类去实例化它。
        // list对象虽然也是被实例化为ArrayList但是它实际是List对象，
        // list只能使用ArrayList中已经实现了的List接口中的方法，ArrayList中那些自己的、
        // 没有在List接口定义的方法是不可以被访问到的。
        List<Note> noteList = new ArrayList<>();
        //查询一条记录
//query(String table,String[] columns,String selection,String[]
// selectionArgs,String groupBy,String having,String  orderBy)
        //public  Cursor query(String table,String[] columns,String selection,String[]
        // selectionArgs,String groupBy,String having,String orderBy,String limit);
       // 参数table:表名称
        //参数columns:列名称数组
        //参数selection:条件字句，相当于where
        //参数selectionArgs:条件字句，参数数组
       // 参数groupBy:分组列
        //参数having:分组条件
       // 参数orderBy:排序列
       // 参数limit:分页查询限制
       // 参数Cursor:返回值，相当于结果集ResultSet
        Cursor cursor = db.query(TABLE_NAME_NOTE,
                null, null, null,
                null, null, null);
        if (cursor != null) {
            while (cursor.moveToNext()) {//光标移动到下一行，
                //返回指定列的名称，如果不存在返回-1
                String id = cursor.getString(cursor.getColumnIndex("id"));
                String title = cursor.getString(cursor.getColumnIndex("title"));
                String content = cursor.getString(cursor.getColumnIndex("content"));
                String createTime = cursor.getString(cursor.getColumnIndex("create_time"));

                Note note = new Note();
                note.setId(id);
                note.setTitle(title);
                note.setContent(content);
                note.setCreatedTime(createTime);

                noteList.add(note);
            }
            cursor.close();
        }

        return noteList;

    }

    public List<Note> queryFromDbByTitle(String title) {
        //对于字符串处理Android为我们提供了一个简单实用的TextUtils类，如果处理比较简单的内容不用去思考正则表达式不妨试试这个在android.text.TextUtils的类，主要的功能如下:
        //是否为空字符 boolean android.text.TextUtils.isEmpty(CharSequence str)
        //拼接字符串 String android.text.TextUtils.join(CharSequence delimiter, Object[] tokens)
        //拆分字符串 String[] android.text.TextUtils.split(String text, String expression)
        //拆分字符串使用正则 String[] android.text.TextUtils.split(String text, Pattern pattern)
        //确定大小写是否有效在当前位置的文本 int android.text.TextUtils.getCapsMode(CharSequence cs, int off, int reqModes)
        if (TextUtils.isEmpty(title)) {
            return queryAllFromDb();
        }

        SQLiteDatabase db = getWritableDatabase();
        List<Note> noteList = new ArrayList<>();

        Cursor cursor = db.query(TABLE_NAME_NOTE, null,
                "title like ?", new String[]{"%"+title+"%"},
                null, null, null);

        if (cursor != null) {

            while (cursor.moveToNext()) {
                String id = cursor.getString(cursor.getColumnIndex("id"));
                String title2 = cursor.getString(cursor.getColumnIndex("title"));
                String content = cursor.getString(cursor.getColumnIndex("content"));
                String createTime = cursor.getString(cursor.getColumnIndex("create_time"));

                Note note = new Note();
                note.setId(id);
                note.setTitle(title2);
                note.setContent(content);
                note.setCreatedTime(createTime);
                noteList.add(note);
            }
            cursor.close();
        }
        return noteList;
    }
}
