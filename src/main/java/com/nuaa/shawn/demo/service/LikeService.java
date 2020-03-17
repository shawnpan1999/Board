package com.nuaa.shawn.demo.service;

import com.nuaa.shawn.demo.util.JedisAdapter;
import com.nuaa.shawn.demo.util.RedisKeyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LikeService {
    @Autowired
    JedisAdapter jedisAdapter;

    /** 判断用户对某内容的喜欢状态(1喜欢 0无) */
    public int getLikeStatus(int userId, int entityType, int entityId) {
        String likeKey = RedisKeyUtil.getLikeKey(entityId, entityType); //LIKE:entityType:entityId
        return jedisAdapter.sismember(likeKey, String.valueOf(userId)) ? 1 : 0;
    }

    public long like(int userId, int entityType, int entityId) {
        String likeKey = RedisKeyUtil.getLikeKey(entityId, entityType);
        jedisAdapter.sadd(likeKey, String.valueOf(userId)); //添加到喜欢者的集合，使用集合还可以防止重复点赞
        return jedisAdapter.scard(likeKey);
    }

    public long dislike(int userId, int entityType, int entityId) {
        String likeKey = RedisKeyUtil.getLikeKey(entityId, entityType);
        jedisAdapter.srem(likeKey, String.valueOf(userId)); //从喜欢者集合里剔除
        return jedisAdapter.scard(likeKey);
    }

//    public long disLike(int userId, int entityType, int entityId) {
//        // 在反对集合里增加
//        String disLikeKey = RedisKeyUtil.getDisLikeKey(entityId, entityType);
//        jedisAdapter.sadd(disLikeKey, String.valueOf(userId));
//        // 从喜欢里删除
//        String likeKey = RedisKeyUtil.getLikeKey(entityId, entityType);
//        jedisAdapter.srem(likeKey, String.valueOf(userId));
//        return jedisAdapter.scard(likeKey);
//    }
}
