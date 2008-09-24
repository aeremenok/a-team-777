package ru.spb.hmi.server;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


/**
 * User: pemman Date: 26.12.2007 Time: 10:52:30
 */
public class DataBaseDoc implements IDoc {

    private String id = null;

    public static List getDocumentsList(){
        List l = new ArrayList();

        //todo
        SAPSimpleConnection c = new SAPSimpleConnection();
        PreparedStatement ps = null;
        try {
            ps = c.getConnection().prepareStatement("select id from documents");
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                l.add(rs.getString(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally{
            try {
                if(ps!=null)ps.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            if(c != null) try {
                c.closeConnection();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return l;
    }

    public String getProperty(String name) {
        System.out.print("[DataBaseDoc] #" + this.id + ": getProperty " + name +" = ");

        SAPSimpleConnection c = new SAPSimpleConnection();
        PreparedStatement ps = null;
        try {
            ps = c.getConnection().prepareStatement("select " + name + " from documents where id = ?");
            ps.setString(1,name);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                System.out.println(rs.getString(1));

                return rs.getString(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally{
            try {
                if(ps!=null)ps.close();
            } catch (SQLException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
            if(c != null) try {
                c.closeConnection();
            } catch (SQLException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
        }
        System.out.println(" NONE! ");

        return null;
    }

    public DataBaseDoc(){
        this.id = null;
    }

    public  DataBaseDoc(String id) throws Exception {
        this.id = null;

        SAPSimpleConnection c = new SAPSimpleConnection();
        PreparedStatement ps = null;
        try {
            ps = c.getConnection().prepareStatement("select * from documents where id = ?");
            ps.setString(1,id);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                this.id = id;
                return;
            }
            System.out.println(" DOC NOT FOUND YOU CAN NOT WORK WITH IT! ");

            throw new Exception("doc not found!");
        } catch (SQLException e) {
            e.printStackTrace();
        }finally{
            try {
                if(ps!=null)ps.close();
            } catch (SQLException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
            if(c != null) try {
                c.closeConnection();
            } catch (SQLException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
        }


    }
    public void setProperty(String name, String value) {
        System.out.println("[DataBaseDoc] #" + this.id + ":  setProperty " + name + " = " + value );

        SAPSimpleConnection c = new SAPSimpleConnection();
        PreparedStatement ps = null;
        try {
            if(this.id ==null)
            ps = c.getConnection().prepareStatement("insert into documents set "+ name + "=?");
            else                 {
                ps = c.getConnection().prepareStatement("update documents set "+ name + "=? where id = ?");
                ps.setString(2,this.id);
            }

            ps.setString(1, value);
            int kol = ps.executeUpdate();

            if(kol==1)
                System.out.println(" INSERTED! ");
            else
                System.out.println(" KOL= "+kol);
        } catch (SQLException e) {
            e.printStackTrace();
        }finally{
            try {
                if(ps!=null)ps.close();
            } catch (SQLException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
            if(c != null) try {
                c.closeConnection();
            } catch (SQLException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
        }

    }

    public void delete() {
        System.out.println("[DataBaseDoc] #" + this.id + ":  delete");

        SAPSimpleConnection c = new SAPSimpleConnection();
        PreparedStatement ps = null;
        if(this.id != null)
        try {
            ps = c.getConnection().prepareStatement("delete from documents where id=?");
            ps.setString(1, this.id);
            int kol = ps.executeUpdate();

            if(kol==1)
                System.out.println(" DELETED! ");
            else
                System.out.println(" KOL= "+kol);

        } catch (SQLException e) {
            e.printStackTrace();
        }finally{
            try {
                if(ps!=null)ps.close();
            } catch (SQLException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
            if(c != null) try {
                c.closeConnection();
            } catch (SQLException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
        }

        this.id = null;
    }

    public String getXML() {
        StringBuffer stb = new StringBuffer();
        stb.append("<xml>");

        SAPSimpleConnection c = new SAPSimpleConnection();
        PreparedStatement ps = null;
        if(this.id != null)
        try {
            ps = c.getConnection().prepareStatement("select * from documents where id=?");
            ps.setString(1, this.id);
            ResultSet rs = ps.executeQuery();

            //todo
            stb.append("<pro1>").append(rs.getString(1)).append("</pro1>");


        } catch (SQLException e) {
            e.printStackTrace();
        }finally{
            try {
                if(ps!=null)ps.close();
            } catch (SQLException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
            if(c != null) try {
                c.closeConnection();
            } catch (SQLException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
        }

        stb.append("</xml>");
        return stb.toString();
    }
}
