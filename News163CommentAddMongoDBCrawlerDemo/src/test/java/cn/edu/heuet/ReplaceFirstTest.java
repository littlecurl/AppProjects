package cn.edu.heuet;

import org.junit.Test;

public class ReplaceFirstTest {
    @Test
    public void replaceFirstTest(){
        String url = "http://comment.api.163.com/api/v1/products/a2869674571f77b5a0867c3d71db5856/threads/EJV829K60001899O/comments/newList?ibc=newspc&limit=30&showLevelThreshold=72&headLimit=1&tailLimit=2&offset=0&callback=jsonp_1563033398749&_=1563033398750";
        for (int i=0; i<=300; i+=30) {
            String commentJsonUrl = url.replaceFirst("offset\\=[0-9]{1,3}","offset="+i);
            System.out.println(commentJsonUrl);
        }
    }
}
