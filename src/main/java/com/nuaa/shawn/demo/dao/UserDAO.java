package com.nuaa.shawn.demo.dao;

import com.nuaa.shawn.demo.model.User;
import org.apache.ibatis.annotations.*;

/*【DAO层】注解方式*/
@Mapper    //@Mapper 说明这个接口是和数据库一一匹配的
public interface UserDAO {
    String TABLE_NAME = "user";
    String INSET_FIELDS = " name, password, salt, head_url ";
    String SELECT_FIELDS = " id, name, password, salt, head_url";
    //为什么把这些字符串独立出来？避免以后需要修改语句时下面要修改一大串
    //通过注解可以执行相应的 SQL 语句：增删改查
    @Insert({"insert into ", TABLE_NAME, "(", INSET_FIELDS,
            ") values (#{name},#{password},#{salt},#{headUrl})"})   //#{name} 是和参数 user 的属性 name 对应起来的，极其方便
    int addUser(User user);

    @Select({"select ", SELECT_FIELDS, " from ", TABLE_NAME, " where id=#{id}"})    //这里直接就可以用 #{id} ,上面是spring找不到的时候才去参数再里面找
    User selectById(int id);

    @Update({"update ", TABLE_NAME, " set password=#{password} where id=#{id}"})
    void updatePassword(User user);

    @Delete({"delete from ", TABLE_NAME, " where id=#{id}"})
    void deleteById(int id);
}
