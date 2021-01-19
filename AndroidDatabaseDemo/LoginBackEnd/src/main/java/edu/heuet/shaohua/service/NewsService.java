package edu.heuet.shaohua.service;

import edu.heuet.shaohua.dataobject.NewsDO;

import java.util.List;

public interface NewsService {
    NewsDO selectDetailById(long id);

    List<NewsDO> selectDetailByTitle(String title);

    List<NewsDO> selectAll();
}
