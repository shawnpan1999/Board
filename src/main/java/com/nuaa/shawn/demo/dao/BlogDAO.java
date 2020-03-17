package com.nuaa.shawn.demo.dao;

import com.nuaa.shawn.demo.model.Blog;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface BlogDAO {
    String TABLE_NAME = "blog";
    String INSERT_FIELDS = " author_id, created_date, text , like_count, comment_count";
    String SELECT_FIELDS = " id, " + INSERT_FIELDS;
    String ORDER_BY = " id ";

    @Insert({"insert into ", TABLE_NAME, "(", INSERT_FIELDS,
            ") values (#{authorId},#{createdDate},#{text},#{lickCount},#{commentCount})"})
    int addBlog(Blog blog);

    @Select({"select ", SELECT_FIELDS , " from ", TABLE_NAME, " where id=#{id}"})
    Blog getById(int id);

    @Select({"select", SELECT_FIELDS, "from", TABLE_NAME, "order by", ORDER_BY, "desc limit #{offset},#{limit}"})
    List<Blog> selectByOffset(@Param("offset") int offset, @Param("limit") int limit);

    @Update({"update ", TABLE_NAME, " set comment_count = #{commentCount} where id=#{id}"})
    int updateCommentCount(@Param("id") int id, @Param("commentCount") int commentCount);

    @Update({"update ", TABLE_NAME, " set like_count = #{likeCount} where id=#{id}"})
    int updateLikeCount(@Param("id") int id, @Param("likeCount") int likeCount);
}
