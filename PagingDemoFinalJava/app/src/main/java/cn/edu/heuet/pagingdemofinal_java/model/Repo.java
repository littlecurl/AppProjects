package cn.edu.heuet.pagingdemofinal_java.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * @ClassName Repo
 * @Author littlecurl
 * @Date 2020/1/8 10:05
 * @Version 1.0.0
 * @Description TODO
 */
@Entity(tableName = "repos")
public class Repo {
    @PrimaryKey
    @ColumnInfo(name = "id")
    public long id;
    @ColumnInfo(name = "name")
    public String name;
    @ColumnInfo(name = "full_name")
    public String full_name;
    @ColumnInfo(name = "description")
    public String description;
    @ColumnInfo(name = "html_url")
    public String html_url;
    @ColumnInfo(name = "stargazers_count")
    public int stargazers_count;
    @ColumnInfo(name = "forks_count")
    public int forks_count;
    @ColumnInfo(name = "language")
    public String language;
}
