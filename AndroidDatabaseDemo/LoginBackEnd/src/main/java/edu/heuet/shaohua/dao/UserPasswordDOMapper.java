package edu.heuet.shaohua.dao;

import edu.heuet.shaohua.dataobject.UserPasswordDO;

/**
 * 这里的方法都是调用的Mapper.xml的SQL语句
 */
public interface UserPasswordDOMapper {
    int deleteByPrimaryKey(Integer id);
    int insert(UserPasswordDO record);
    int insertSelective(UserPasswordDO record);
    UserPasswordDO selectByPrimaryKey(Integer id);
    UserPasswordDO selectByUserId(Integer UserId);
    int updateByPrimaryKeySelective(UserPasswordDO record);
    int updateByPrimaryKey(UserPasswordDO record);
}