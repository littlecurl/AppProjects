package cn.edu.heuet.pagingdemofinal_java.db;

import cn.edu.heuet.pagingdemofinal_java.data.RepoBoundaryCallback;

/**
 * @ClassName CRUDApiImpl
 * @Author littlecurl
 * @Date 2020/1/8 15:28
 * @Version 1.0.0
 * @Description TODO
 */
public class CRUDApiImpl implements CRUDApi {
    @Override
    public void insertFinished(String errorMsg) {
        RepoBoundaryCallback.networkErrors.postValue(errorMsg);
        RepoBoundaryCallback.isRequestInProgress = false;
    }
}
