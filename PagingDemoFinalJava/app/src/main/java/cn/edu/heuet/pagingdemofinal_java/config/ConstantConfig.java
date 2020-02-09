package cn.edu.heuet.pagingdemofinal_java.config;

/**
 * @ClassName ConstantConfig
 * @Author littlecurl
 * @Date 2020/1/11 10:14
 * @Version 1.0.0
 * @Description TODO
 */
public class ConstantConfig {
    // 每页查询数据库数据数量
    public static final int DATABASE_PAGE_SIZE = 20;
    // 每页请求网络数据数量
    public static final int NETWORK_PAGE_SIZE = 30; // prefetchDistance = 5  ； 30 = 2 * 15
    // 后端基地址
    public static final String BASE_URL = "https://api.github.com/";
    // 查询条件修饰
    public static final String IN_QUALIFIER = "in:name,description";
}
