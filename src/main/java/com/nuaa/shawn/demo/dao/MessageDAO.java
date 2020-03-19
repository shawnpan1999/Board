package com.nuaa.shawn.demo.dao;

import com.nuaa.shawn.demo.model.Message;
import com.nuaa.shawn.demo.model.MsgType;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface MessageDAO {
    String TABLE_NAME = " message ";
    String INSERT_FIELDS = " msg_type, from_id, to_id, content, has_read, conversation_id, created_date ";
    String SELECT_FIELDS = " id, " + INSERT_FIELDS;
    String DIALOG_TYPE_NUM = "2";

    @Insert({"insert into ", TABLE_NAME, "(", INSERT_FIELDS,
            ") values (#{msgType},#{fromId},#{toId},#{content},#{hasRead},#{conversationId},#{createdDate})"})
    int addMessage(Message message);

    @Select({"select ", SELECT_FIELDS, " from ", TABLE_NAME, " where conversation_id=#{conversationId} order by id desc limit #{offset}, #{limit}"})
    List<Message> getConversationDetail(@Param("conversationId") String conversationId,
                                        @Param("offset") int offset, @Param("limit") int limit);

    int getMessageUnreadCount(@Param("userId") int userId, @Param("msgType") int msgType, @Param("conversationId") String conversationId);

    //获取消息列表，其中赞和评论合并为一条消息，私信对话按 conversation_id 合并
    //TODO: limit 还没写进去
    @Select({"select ", INSERT_FIELDS, " from ( select *,GROUP_CONCAT(DISTINCT from_id) as from_ids,COUNT(id) as cnt from ",
             "(select * from message where to_id=#{userId} and msg_type<", DIALOG_TYPE_NUM, " order by created_date desc limit 10000000000) temp1 ",
             "group by msg_type order by created_date desc) t1 ",
             "union ",
             "select ", INSERT_FIELDS, " from ( select *,GROUP_CONCAT(DISTINCT from_id) as from_ids,COUNT(id) as cnt from ",
             "(select * from message where to_id=#{userId} and msg_type=", DIALOG_TYPE_NUM, " order by created_date desc limit 10000000000) temp2 ",
             "group by conversation_id order by created_date desc) t2"})
    List<Message> getMessageList(@Param("userId") int userId,
                                 @Param("offset") int offset, @Param("limit") int limit);

//这个太 JB 恐怖了
//    SELECT * FROM
//            (SELECT *,GROUP_CONCAT(DISTINCT from_id) AS from_ids,COUNT(id) AS cnt FROM
//            (SELECT * FROM message WHERE to_id=13 AND msg_type<2 ORDER BY created_date DESC LIMIT 10000000000) temp1
//    GROUP BY msg_type ORDER BY created_date DESC) t1
//            UNION
//    SELECT * FROM
//            (SELECT *,GROUP_CONCAT(DISTINCT from_id) AS from_ids,COUNT(id) AS cnt FROM
//            (SELECT * FROM message WHERE to_id=13 AND msg_type=2 ORDER BY created_date DESC LIMIT 10000000000) temp2
//    GROUP BY conversation_id ORDER BY created_date DESC) t2
}
