package com.example.kureichyk.phone;

import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

//import java.sql.Connection;

/**
 * Created by kureichyk on 16.02.2018.
 */

public class JDBCConnect extends AsyncTask<String, Void, JSONArray> {
    final static String url = "jdbc:mysql://172.26.30.174:3306/phone";
    final static String LOGIN = "root";
    final static String PASS= "root";

    @Override
    protected JSONArray doInBackground(String... query) {
        String z=null;
        JSONArray resultSet = new JSONArray();
        try {
            Class.forName("com.mysql.jdbc.Driver");
            java.sql.Connection con = null;
            Statement st = null;
            ResultSet rs = null;
            try {
                con =  DriverManager.getConnection(url, LOGIN, PASS);
                if (con != null) {
                    st = con.createStatement();
                    rs = st.executeQuery(query[0]);
                    if (rs != null) {
                        int columnCount = rs.getMetaData().getColumnCount();
                        // Сохранение данных в JSONArray
                        while (rs.next()) {
                            JSONObject rowObject = new JSONObject();
                            for (int i = 1; i <= columnCount; i++) {
                                rowObject.put(rs.getMetaData().getColumnName(i), (rs.getString(i) != null) ? rs.getString(i) : "");
                            }
                            resultSet.put(rowObject);
                        }
                    }
                }

            }  catch (SQLException e) {
                e.printStackTrace();
            } catch (JSONException e1) {
                e1.printStackTrace();
            } finally {
                try {
                    if (rs != null) rs.close();
                    if (st != null) st.close();
                    if (con != null) con.close();
                } catch (SQLException e) {
                    throw new RuntimeException(e.getMessage());
                }
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return resultSet;
    }
}




