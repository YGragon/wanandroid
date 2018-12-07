package com.dong.wanandroid.data.db;

import com.dong.wanandroid.BaseApplication;
import com.dong.wanandroid.data.banner.Banner;
import com.dong.wanandroid.greendao.gen.BannerDao;
import com.dong.wanandroid.greendao.gen.HomeArticleModelDao;
import com.dong.wanandroid.greendao.gen.ReadRecordModelDao;
import com.dong.wanandroid.greendao.gen.UserModelDao;
import com.dong.wanandroid.data.home.HomeArticleModel;
import com.dong.wanandroid.data.read_record.ReadRecordModel;
import com.dong.wanandroid.data.user.UserModel;

import java.util.List;

/**
 * 数据库操作类
 */

public class DBHelper {
    private static DBHelper mDBHelper = null;
    private DBHelper(){}
    public static DBHelper getInstance() {
        synchronized (DBHelper.class) {
            if (mDBHelper == null) {
                mDBHelper = new DBHelper();
            }
        }
        return mDBHelper;
    }

    /**
     * 插入用户数据到数据库
     * @param userModel
     */
    public static void setUserToDb(UserModel userModel){
        UserModelDao userModelDao = BaseApplication.getDaoInstant().getUserModelDao();
        userModelDao.insert(userModel) ;
    }
    /**
     * 从数据库获取用户id 数据
     * @param id
     */
    public static UserModel getUserFromDbById(int id){
        UserModelDao userModelDao = BaseApplication.getDaoInstant().getUserModelDao();
        UserModel userModel = userModelDao.queryBuilder().where(UserModelDao.Properties.Id.eq(id)).unique();
        return userModel;
    }
    /**
     * 从数据库获取用户数据
     */
    public static List<UserModel> getUserFromDb(){
        UserModelDao userModelDao = BaseApplication.getDaoInstant().getUserModelDao();
        List<UserModel> userModels = userModelDao.loadAll();
        return userModels;
    }

    /**
     * 插入文章数据到数据库
     * @param homeArticleModel
     */
    public static void setArticleToDb(List<HomeArticleModel> homeArticleModel){
        HomeArticleModelDao articleModelDao = BaseApplication.getDaoInstant().getHomeArticleModelDao();
        articleModelDao.insertInTx(homeArticleModel);
    }
    /**
     * 从数据库获取所有文章数据
     */
    public static List<HomeArticleModel>  getArticleFromDb(){
        HomeArticleModelDao articleModelDao = BaseApplication.getDaoInstant().getHomeArticleModelDao();
        List<HomeArticleModel> articleModels = articleModelDao.loadAll();
        return articleModels;
    }


    /**
     * 插入Banner数据到数据库
     * @param bannerData
     */
    public static void setBannerToDb(List<Banner> bannerData){
        BannerDao bannerDao = BaseApplication.getDaoInstant().getBannerDao();
        bannerDao.insertInTx(bannerData);
    }
    /**
     * 从数据库获取所有文章数据
     */
    public static List<Banner>  getBannerFromDb(){
        BannerDao bannerDao = BaseApplication.getDaoInstant().getBannerDao();
        List<Banner> banners = bannerDao.loadAll();
        return banners;
    }

    /**
     * 插入阅读记录到数据库
     * @param readRecordModel
     */
    public static void setReadRecordToDb(ReadRecordModel readRecordModel){
        ReadRecordModelDao readRecordModelDao = BaseApplication.getDaoInstant().getReadRecordModelDao();
        List<ReadRecordModel> readRecordFromDb = getReadRecordFromDb();
        if (readRecordFromDb.size() > 0){
            for (int i = 0 ; i < readRecordFromDb.size() ; i++){
                if (readRecordModel.getId() == readRecordFromDb.get(i).getId()){
                    continue;
                }else {
                    // 未在数据库中则存储，退出则清空记录
                    readRecordModelDao.insert(readRecordModel);
                    break;
                }
            }
        }else {
            readRecordModelDao.insert(readRecordModel);
        }
    }
    /**
     * 从数据库获取所有已经阅读文章数据
     */
    public static List<ReadRecordModel>  getReadRecordFromDb(){
        ReadRecordModelDao readRecordModelDao = BaseApplication.getDaoInstant().getReadRecordModelDao();
        List<ReadRecordModel> readRecordModels = readRecordModelDao.loadAll();
        return readRecordModels;
    }
    /**
     * 从数据库删除所有已经阅读文章数据
     */
    public static void  clearReadRecordFromDb() {
        ReadRecordModelDao readRecordModelDao = BaseApplication.getDaoInstant().getReadRecordModelDao();
        readRecordModelDao.deleteAll();
    }
}
