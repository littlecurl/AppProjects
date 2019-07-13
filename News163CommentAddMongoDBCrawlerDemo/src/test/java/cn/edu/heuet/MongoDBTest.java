package cn.edu.heuet;

import cn.edu.heuet.getcomment.MongoDBUtil;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class MongoDBTest {
    //插入一个文档
    @Test
    public void insertOneTest(){
        //获取数据库连接对象
        MongoDatabase db = MongoDBUtil.getConnect();
        //获取集合
        MongoCollection<Document> table = db.getCollection("user");
        //要插入的数据
        Document row = new Document("name","littlecurl")
                .append("sex", "男")
                .append("age", 20)
                .append("other","冗余列");
        //插入一个文档
        table.insertOne(row);
    }

    //插入多个文档
    @Test
    public void insertManyTest(){
        //获取数据库连接对象
        MongoDatabase mongoDatabase = MongoDBUtil.getConnect();
        //获取集合
        MongoCollection<Document> collection = mongoDatabase.getCollection("user");
        //要插入的数据
        List<Document> list = new ArrayList<>();
        for(int i = 1; i <= 3; i++) {
            Document document = new Document("name", "张三")
                    .append("sex", "男")
                    .append("age", 18);
            list.add(document);
        }
        //插入多个文档
        collection.insertMany(list);
    }

    //删除与筛选器匹配的单个文档
    @Test
    public void deleteOneTest(){
        //获取数据库连接对象
        MongoDatabase mongoDatabase = MongoDBUtil.getConnect();
        //获取集合
        MongoCollection<Document> collection = mongoDatabase.getCollection("user");
        //申明删除条件
        Bson filter = Filters.eq("age",18);
        //删除与筛选器匹配的单个文档
        collection.deleteOne(filter);
    }

    //删除与筛选器匹配的所有文档
    @Test
    public void deleteManyTest(){
        //获取数据库连接对象
        MongoDatabase mongoDatabase = MongoDBUtil.getConnect();
        //获取集合
        MongoCollection<Document> collection = mongoDatabase.getCollection("user");
        //申明删除条件
        Bson filter = Filters.eq("age",18);
        //删除与筛选器匹配的所有文档
        collection.deleteMany(filter);
    }

    //修改单个文档
    @Test
    public void updateOneTest(){
        //获取数据库连接对象
        MongoDatabase mongoDatabase = MongoDBUtil.getConnect();
        //获取集合
        MongoCollection<Document> collection = mongoDatabase.getCollection("user");
        //修改过滤器
        Bson filter = Filters.eq("name", "张三");
        //指定修改的更新文档
        Document document = new Document("$set", new Document("age", 23));
        //修改单个文档
        collection.updateOne(filter, document);
    }

    //修改多个文档
    @Test
    public void updateManyTest(){
        //获取数据库连接对象
        MongoDatabase mongoDatabase = MongoDBUtil.getConnect();
        //获取集合
        MongoCollection<Document> collection = mongoDatabase.getCollection("user");
        //修改过滤器
        Bson filter = Filters.eq("name", "张三");
        //指定修改的更新文档
        Document document = new Document("$set", new Document("age", 123));
        //修改多个文档
        collection.updateMany(filter, document);
    }

    //查找集合中的所有文档
    @Test
    public void findTest(){
        //获取数据库连接对象
        MongoDatabase mongoDatabase = MongoDBUtil.getConnect();
        //获取集合
        MongoCollection<Document> collection = mongoDatabase.getCollection("user");
        //查找集合中的所有文档
        FindIterable findIterable = collection.find();
        MongoCursor cursor = findIterable.iterator();
        while (cursor.hasNext()) {
            System.out.println(cursor.next());
        }
    }

    //指定查询过滤器查询
    @Test
    public void FilterfindTest(){
        //获取数据库连接对象
        MongoDatabase mongoDatabase = MongoDBUtil.getConnect();
        //获取集合
        MongoCollection<Document> collection = mongoDatabase.getCollection("user");
        //指定查询过滤器
        Bson filter = Filters.eq("name", "张三");
        //指定查询过滤器查询
        FindIterable findIterable = collection.find(filter);
        MongoCursor cursor = findIterable.iterator();
        while (cursor.hasNext()) {
            System.out.println(cursor.next());
        }
    }

    //取出查询到的第一个文档
    @Test
    public void findFirstTest(){
        //获取数据库连接对象
        MongoDatabase mongoDatabase = MongoDBUtil.getConnect();
        //获取集合
        MongoCollection<Document> collection = mongoDatabase.getCollection("user");
        //查找集合中的所有文档
        FindIterable findIterable = collection.find();
        //取出查询到的第一个文档
        Document document = (Document) findIterable.first();
        //打印输出
        System.out.println(document);
    }
}
