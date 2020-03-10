package com.nuaa.shawn.demo.dao;

import com.nuaa.shawn.demo.model.Message;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface MessageDAO {
    String TABLE_NAME = "message";
    String INSERT_FIELDS = " author_id, created_date, text ";
    String SELECT_FIELDS = " id, " + INSERT_FIELDS;
    String ORDER_BY = " id ";

    @Insert({"insert into ", TABLE_NAME, "(", INSERT_FIELDS,
            ") values (#{authorId},#{createdDate},#{text})"})
    int addMessage(Message message);

    @Select({"select", SELECT_FIELDS, "from", TABLE_NAME, "order by", ORDER_BY, "desc limit #{offset},#{limit}"})
    List<Message> selectByOffset(@Param("offset") int offset, @Param("limit") int limit);
}
