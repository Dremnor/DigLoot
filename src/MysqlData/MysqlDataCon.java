/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MysqlData;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;
import me.Dremnor.DigLoot.Main;

import org.bukkit.Bukkit;

/**
 *
 * @author Mokito
 */
public class MysqlDataCon {
    private Connection connection;

    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }
    private String host,database,username,password,table;
    Main plugin = Main.getPlugin(Main.class);
    
    public MysqlDataCon(){

        plugin.getConfig().options().copyDefaults();
        plugin.saveConfig();
        
        this.host=plugin.getConfig().getString("mysql.host");
        this.database=plugin.getConfig().getString("mysql.database");
        this.username=plugin.getConfig().getString("mysql.username");
        this.password=plugin.getConfig().getString("mysql.password");
        this.table=plugin.getConfig().getString("mysql.table");
        
        try{

            synchronized(this){
                if(getConnection() !=null && getConnection().isClosed()){
                return;
            }
                Class.forName("com.mysql.jdbc.Driver");
                setConnection(DriverManager.getConnection("jdbc:mysql://"+this.host+":3306/"+this.database+"?autoReconnect=true&useSSL=false",this.username,this.password));
                if(plugin.debug)Bukkit.getConsoleSender().sendMessage("DB Connected");
            }
        }catch(SQLException | ClassNotFoundException e){
            e.printStackTrace();
        }
        
        
    }
    
    
    public void setUp() throws SQLException{
        PreparedStatement statment = this.connection.prepareStatement("SHOW TABLES LIKE ?");
        statment.setString(1, this.table);
        
        ResultSet result = statment.executeQuery();
        if(result.next()){
            if(plugin.debug)System.out.println("Tabela istnieje!");
            
        }else{
            if(plugin.debug)System.out.println("Tabela nie istnieje!");
            statment = this.connection.prepareStatement("CREATE TABLE `"+this.table+"` (" +
                                                        "  `id` int(11) NOT NULL," +
                                                        "  `uuid` text COLLATE utf8_polish_ci NOT NULL," +
                                                        "  `name` text COLLATE utf8_polish_ci NOT NULL," +
                                                        "  `storage` longtext COLLATE utf8_polish_ci," +
                                                        "  `count` bigint(20) NOT NULL" +
                                                        ") ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_polish_ci;");
            statment.executeUpdate();
            
            statment = this.connection.prepareStatement("ALTER TABLE `"+this.table+"`" +
            "  ADD PRIMARY KEY (`id`)");
            statment.executeUpdate();
            
            statment = this.connection.prepareStatement("ALTER TABLE `"+this.table+"`" +
            "  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2");
            statment.executeUpdate();
            
        }  
    }
    
    public boolean playerExist(UUID uuid) throws SQLException{
        PreparedStatement statment = this.connection.prepareStatement("SELECT * FROM "+this.table+" WHERE uuid=?");
        statment.setString(1, uuid.toString());
        
        ResultSet result = statment.executeQuery();
        if(result.next()){
            System.out.println("Gracz istnieje!");
            return true;
        }else{
            System.out.println("Gracz nie istnieje!");
            return false;
        }        
    }
    
    public void playerCreate(final UUID uuid,String player) throws SQLException{
        PreparedStatement statment = this.connection.prepareStatement("INSERT INTO "+this.table+"(uuid,name,storage,count) VALUE(?,?,?,?)");
        statment.setString(1, uuid.toString());
        statment.setString(2, player);
        statment.setString(3, "");
        statment.setInt(4, 0);
        statment.executeUpdate();
        
        
    }
    
}
