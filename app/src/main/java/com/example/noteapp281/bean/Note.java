package com.example.noteapp281.bean;

import java.io.Serializable;
//get是取来值的方法，set是设置值的方法，
//一个类通过关键字implements声明自己使用一个或者多个接口。 implements 是实现多个接口, 接口的方法一般为空的, 必须重写才能使用
//JAVA中不支持多重继承，但是可以用接口 来实现，这样就要用到implements，继承只能继承一个类，但implements可以实现多个接口，用逗号分开就行了
//class 子类名 extends 父类名 implenments 接口名
//序列化：Serializable-- 1.当你想要把内存中的对象状态保存到一个文件中或者数据中的时候
//             2.当你想用套接字在网络上传送对象的时候
//             3.当你想要通过RMI传输对象的时候
public class Note implements Serializable {

    private String title;
    private String content;
    private String createdTime;
    private String id;
//在上述代码中 title、content 、createdTime和id 的作用域是 private，
// 因此在类外部无法对它们的值进行设置。为了解决这个问题，可以为 Note 类添加一个构造方法，
// 然后在构造方法中传递参数进行修改。

    public String getTitle() {
        return title;
    }
//在 Note 类的构造方法中使用了 this 关键字对属性 title、content 、createdTime和id赋值，this 表示当前对象。
// this.name=name语句表示一个赋值语句，等号左边的 this.name 是指当前对象具有的变量 name，
// 等号右边的 name 表示参数传递过来的数值。
    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(String createdTime) {
        this.createdTime = createdTime;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Note{" +
                "title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", createdTime='" + createdTime + '\'' +
                ", id='" + id + '\'' +
                '}';
    }
}
