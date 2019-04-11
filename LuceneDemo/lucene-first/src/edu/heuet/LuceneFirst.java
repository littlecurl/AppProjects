package edu.heuet;
/**
 * 一、创建索引 indexWriter
 *      1、Directory
 *      2、IndexWriter
 *      3、读取文件
 *      4、创建Field
 *      5、Document
 *      6、indexWriter
 *      7、关闭indexWriter
 *
 * 二、查询索引 或 使用luke(要求JDK1.9+)查看索引库中的内容
 *      1、Directory
 *      2、indexReader
 *      3、indexSearcher
 *      4、Query
 *      5、TopDocs(查询结果)
 *      6、totalHits查询总记录数
 *      7、scoreDocs取文档列表
 *      8、打印结果scoreDocs
 *      9、关闭indexReader
 */

import org.apache.commons.io.FileUtils;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.*;
import org.apache.lucene.search.*;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.junit.Test;

import java.io.File;

public class LuceneFirst {
    // 一、创建索引
    @Test
    public void createIdex() throws Exception{
        // 1、创建一个Directory对象，指定索引库保存的位置
        Directory directory = FSDirectory.open( new File("F:\\Workspaces\\IDEA\\LuceneDemo\\lucene-first\\temp\\index").toPath());
        // 2、基于Directory对象创建一个IndexWrite对象
        IndexWriter indexWriter = new IndexWriter(directory, new IndexWriterConfig());
        // 3、读取磁盘上的文件
        File dir = new File("F:\\Workspaces\\IDEA\\LuceneDemo\\lucene-first\\temp\\searchsource");
        File[] files = dir.listFiles();
        for(File file: files){
            String fileName = file.getName();
            String filePath = file.getPath();
            String fileContent = FileUtils.readFileToString(file,"utf-8");
            long fileSize = FileUtils.sizeOf(file);

            // 4、创建Field
            Field fieldName = new TextField("name",fileName, Field.Store.YES);
            Field fieldPath = new TextField("path",filePath, Field.Store.YES);
            Field fieldContent = new TextField("content",fileContent, Field.Store.YES);
            Field fieldSize = new TextField("size",fileSize+"", Field.Store.YES);

            // 5、创建文档对象，向文档对象中添加域
            Document document = new Document();
            document.add(fieldName);
            document.add(fieldPath);
            document.add(fieldContent);
            document.add(fieldSize);

            // 6、把文档对象写入索引库
            indexWriter.addDocument(document);
        }
        // 7、关闭IndexWriter对象
        indexWriter.close();
    }

    // 二、查询索引
    @Test
    public void searchIndex() throws Exception{
        // 1
        Directory directory = FSDirectory.open(new File("F:\\Workspaces\\IDEA\\LuceneDemo\\lucene-first\\temp\\index").toPath());
        // 2
        IndexReader indexReader = DirectoryReader.open(directory);
        // 3
        IndexSearcher indexSearcher = new IndexSearcher(indexReader);
        // 4、查询字母 c
        Query query = new TermQuery(new Term("content", "c"));
        // 5
        TopDocs topDocs = indexSearcher.search(query, 10);
        // 6
        System.out.println("查询总记录数："+topDocs.totalHits);
        // 7
        ScoreDoc[] scoreDocs = topDocs.scoreDocs;
        // 8
        for(ScoreDoc doc:scoreDocs){
            int docId = doc.doc;
            Document document = indexSearcher.doc(docId);
            System.out.println(document.get("name"));
            System.out.println(document.get("path"));
            System.out.println(document.get("size"));
            System.out.println(document.get("content"));
            System.out.println("--------------------------");
        }
        // 9
        indexReader.close();

    }

}
