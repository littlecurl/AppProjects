package edu.heuet.shaohua.service.impl;

import edu.heuet.shaohua.dao.UserDOMapper;
import edu.heuet.shaohua.dao.UserPasswordDOMapper;
import edu.heuet.shaohua.dataobject.UserDO;
import edu.heuet.shaohua.dataobject.UserPasswordDO;
import edu.heuet.shaohua.error.BusinessException;
import edu.heuet.shaohua.error.EmBusinessError;
import edu.heuet.shaohua.service.UserService;
import edu.heuet.shaohua.service.model.UserModel;
import edu.heuet.shaohua.validator.ValidationResult;
import edu.heuet.shaohua.validator.ValidatorImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

/* @Service标注加在具体的Service实现类上 */
@Service
public class UserServiceImpl implements UserService  {
    /**
     * Controller层接收View层传来的参数，
     *     将参数转给Service层
     *     Service层再调用DAO层访问数据
     *     Service层再将DAO层返回给Controller层
     *     Controller层将Service层返回传递给View层
     */
    @Autowired
    private UserDOMapper userDOMapper;

    /**
     * 每一个需要自动注入的对象都需要加@Autowired注解
     */
    @Autowired
    private UserPasswordDOMapper userPasswordDOMapper;

    @Autowired
    private ValidatorImpl validator;

    public UserServiceImpl() {
    }


    @Override
    public UserModel getUserById(Integer id) {
        /*
        **************** 对返回值类型model的解释：****************
        在Service层持有DoMapper的对象，调用查询数据方法
        用UserDO接口对象接收
        因为dataobject层的UserDO属性与数据库字段一一对应
        有时候因为业务需要，一些字段并不在同一张表里
        导致对应的UserDO属性不全
        故不应该使用UserDO作为返回值传递给Controller
        而应该再建立一级model层，来整合业务需要的所有属性
        */
        UserDO userDO = userDOMapper.selectByPrimaryKey(id);
        /* userDO的判空处理 */
        if (userDO == null){ return  null; }
        /* 通过UserDO的id查询到关联表user_password中的关联数据 */
        UserPasswordDO userPasswordDO = userPasswordDOMapper.selectByUserId(userDO.getId());

        /* 调用方法，整合UserDO和UserPasswordDO为userModel并返回给Controller */
        return convertFromDataObject(userDO, userPasswordDO);
    }

    /**
     * 用户获取验证码时，检测是否已存在注册用户
     * @param telephone 用户手机号
     * @return 是否存在当前手机号的用户
     */
    @Override
    public Boolean getUserByTelephone(String telephone) {
        UserDO userDO = userDOMapper.selectByTelephone(telephone);
        if (userDO == null){
            return  false;
        } else {
            return  true;
        }
    }

    /* 整合业务需要的所有属性为UserModel，这里为两张表的所有属性 */
    private UserModel convertFromDataObject(UserDO userDO, UserPasswordDO userPasswordDO){
        /* UserDO的判空处理 */
        if (userDO == null){ return null; }
        UserModel userModel = new UserModel();
        /* 先把 userDO 中的数据复制给 userModel */
        BeanUtils.copyProperties(userDO, userModel);
        /* userPasswordDO的判空处理 */
        if (userPasswordDO != null) {
            /*
            已经复制过一遍了，如果再次进行复制，有可能出现id字段相同而值不同
            因此不能再次复制，只能通过setter方法进行设置
             */
            userModel.setEncryptPassword(userPasswordDO.getEncryptPassword());
        }
        // 将整合好的userModel返回
        return userModel;
    }

    /**
     * 用户注册服务的实现
     * 加上@Transactional注解是为了避免出现用户信息插入不全，程序意外结束
     *
     * @param userModel 用户信息
     */
    @Override
    @Transactional
    public void register(UserModel userModel) throws BusinessException{
        // 先进行整体判空处理，这样代码才健壮一些
        if (userModel == null){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR);
        }

        // 优化后的model校验
        ValidationResult result = validator.validate(userModel);
        if (result.isHasError()){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR, result.getErrMsg());
        }

        // model --->  dataobject:UserDO
        // 之所以使用insertSelective方法，这样可以避免使用null字段，而使用设计数据库时的默认值
        UserDO userDO = convertFromModel(userModel);
        try {
            userDOMapper.insertSelective(userDO);
        }catch (DuplicateKeyException ex){
            // 手动回滚事务
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"手机号已重复注册");
        }
        /*
        一旦insertSelective成功，user表的id就会自增（这需要去UserDOMapper.xml文件中设置id为主键自增）
        这时候就可以通过userDo进行get了
        将get到的id赋值给userModel，并传递给convertPasswoedFromModel方法
         */
        userModel.setId(userDO.getId());

        // model --->  dataobject:UserPasswordDO
        UserPasswordDO userPasswordDO = convertPasswordFromModel(userModel);
        userPasswordDOMapper.insertSelective(userPasswordDO);
    }

    /**
     * 实现model --->  dataobject:UserDO
     *
     * @param userModel Model
     * @return UserDO
     */
    private UserDO convertFromModel(UserModel userModel){
        // 每一层都进行判空，这样代码才处处健壮
        if(userModel == null){
            return null;
        }
        UserDO userDO = new UserDO();
        // source是userModel，target是userDO，
        // 这样在copy过程中，userModel中多余的属性会被自动丢弃
        BeanUtils.copyProperties(userModel, userDO);

        return userDO;
    }

    /**
     * 实现model --->  dataobject:UserDO
     *
     * @param userModel Model
     * @return UserDO
     */
    private UserPasswordDO convertPasswordFromModel(UserModel userModel){
        if (userModel == null){
            return  null;
        }
        UserPasswordDO userPasswordDO = new UserPasswordDO();
        /*
        对于userPasswordDO，我们不能像userDO那样进行copy
        因为我们在整合DO为model时，id是从userDO那里copy过来的，
        强行copy会导致user_password表中id不一致
        还有一点是，应该现有userDO中的属性，才会有userPasswordDO中的属性
        所以这里的userPasswordDO的id属性不需要设置，自动递增即可
         */
        userPasswordDO.setEncryptPassword(userModel.getEncryptPassword());
        /*
         user_password表总共三个字段，id不用我们管，可不要漏掉user_id字段
         否则无法根据外键进行查询密码了
         之所以userModel可以getId，在register方法中有提到
         */
        userPasswordDO.setUserId(userModel.getId());
        return userPasswordDO;
    }

    /**
     * 用户登录服务的实现
     *
     * @param telphone 手机号
     * @param encryptPassword 加密密码
     * @return 用户Model
     */
    @Override
    public UserModel validateLogin(String telphone, String encryptPassword) throws BusinessException {
        // 通过用户的手机获取用户信息
        /* 这里的selectByTelphone是我们在UserDOMapper.xml中手动实现的 */
        UserDO userDO = userDOMapper.selectByTelephone(telphone);
        if (userDO == null){
            throw new BusinessException(EmBusinessError.USER_LOGIN_FAIL);
        }
        UserPasswordDO userPasswordDO = userPasswordDOMapper.selectByUserId(userDO.getId());
        UserModel userModel = convertFromDataObject(userDO, userPasswordDO);

        // 比对用户信息内加密的面是否和传输进来的密码相匹配
        if (!StringUtils.equals(encryptPassword,userModel.getEncryptPassword())){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"用户名或密码错误");
        }
        return userModel;
    }


}
