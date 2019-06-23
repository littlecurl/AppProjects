package edu.heuet.android.logindemo.dao;

import edu.heuet.android.logindemo.dataobject.UserPasswordDO;
/* 这里的方法都是调用的Mapper.xml的SQL语句 */
public interface UserPasswordDOMapper {
    int deleteByPrimaryKey(Integer id);
    int insert(UserPasswordDO record);
    int insertSelective(UserPasswordDO record);
    UserPasswordDO selectByPrimaryKey(Integer id);

    /* 手动编写满足业务需要的方法 */
    UserPasswordDO selectByUserId(Integer UserId);

    int updateByPrimaryKeySelective(UserPasswordDO record);
    int updateByPrimaryKey(UserPasswordDO record);
}