package org.apache.ibatis.wen;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.util.function.Function;

public class DaoUtils {

    private static SqlSessionFactory sqlSessionFactory;

    static {
        String resoure = "wen/mybatis-config.xml";
        InputStream inputStream = null;
        try {
            inputStream = Resources.getResourceAsStream(resoure);
        } catch (IOException e) {
            System.err.println("read mybatis-config.xml fail");
            e.printStackTrace();
            System.exit(1);
        }
        // 加载mybatis-config.xml配置文件，并创建SqlSessionFactory对象
        sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
    }

    public static <R> R execute(Function<SqlSession, R> function) {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        try {
            R apply = function.apply(sqlSession);
            sqlSession.commit();
            return apply;
        } catch (Exception e) {
            // 出现异常的时候，回滚事务
            sqlSession.rollback();
            System.out.println("execute error");
            throw e;
        } finally {
            sqlSession.close();
        }
    }


}
