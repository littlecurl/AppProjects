package cn.edu.heuet.pagingdemofinal_java.db;

import androidx.paging.DataSource;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import cn.edu.heuet.pagingdemofinal_java.model.Repo;

/**
 * @ClassName RepoDao
 * @Author littlecurl
 * @Date 2020/1/8 11:34
 * @Version 1.0.0
 * @Description TODO
 */
@Dao
public interface RepoDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void insert(List<Repo> repos);

    // Do a similar query as the search API:
    // Look for repos that contain the query string in the name or in the description
    // and order those results descending, by the number of stars and then by name
    @Query("SELECT * FROM repos WHERE (name LIKE :queryString) OR (description LIKE " +
            ":queryString) ORDER BY stargazers_count DESC, name ASC")
    //updated to include DataSource object, when data is updated, it is handled by DataSource.Factory
    public DataSource.Factory<Integer, Repo> reposByName(String queryString);
}
