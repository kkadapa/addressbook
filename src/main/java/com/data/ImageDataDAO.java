package com.data;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
/**
 * Created by karthik on 1/28/15.
 */

public class ImageDataDAO {

    public static ImageDataModel getImageData(int offset,int noOfRecords) {

        List<Integer> pageIDList = new ArrayList<Integer>();
        List<String> articleTitList = new ArrayList<String>();
        List<String> textDataList = new ArrayList<String>();

        try {

            ImageDataModel dataModel = new ImageDataModel();
            DatabaseHelper dbHelper = new DatabaseHelper();
            Connection connection = dbHelper.connectionPool.reserveConnection();

            Statement statement = connection.createStatement();

            ResultSet result = statement
                    .executeQuery("select * from imageintegration.imagedata limit "+offset+","+noOfRecords+" ");

            while (result.next()) {

                pageIDList.add(result.getInt("pageid"));

                articleTitList.add(result.getString("articletitle"));

                textDataList.add(result.getString("textdata"));
            }

            dataModel.setImgPageIdList(pageIDList);
            dataModel.setArticleTitleList(articleTitList);
            dataModel.setTextDataList(textDataList);
            dbHelper.connectionPool.releaseConnection(connection);

            return dataModel;
        } catch (SQLException ex) {
            System.out.println("Exception at ImageDataDAO");
            ex.printStackTrace();
            return null;
        }
    }
}
