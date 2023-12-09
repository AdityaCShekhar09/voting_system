import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.*;

public class Main {
    public static Connection databselink;

    public static PrintWriter out;

    public static Connection getConnection(){
        String database="login";
        String databaseUser="root";
        String databasePassword="";
        String url="jdbc:mysql://localhost:3306/"+database;

        try {
            if (databselink == null || databselink.isClosed()) {
                databselink = DriverManager.getConnection(url, databaseUser, databasePassword);
                System.out.println("Database connected");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle the exception, log it, or throw a custom exception as needed.
        }
        return databselink;
    }

    public static void main(String[] args) {
        try {
            while (true) {
                ServerSocket server = new ServerSocket(5000);
                System.out.println("Server started");

                System.out.println("Waiting for a client ...");

                Socket socket = server.accept();
                System.out.println("Client accepted");

                // takes input from the client socket
                DataInputStream in = new DataInputStream(
                        new BufferedInputStream(socket.getInputStream()));

                String userData = in.readLine();
                System.out.println(userData);
                String[] data = userData.split(".!");

                Connection connectDB = getConnection();
                if (data[0].equals("1")) {
                    String verifyLogin = "SELECT count(1) FROM voter WHERE email = '" + data[1] + "'AND password = '" + data[2] + "'";
                    try {
                        Statement statement = connectDB.createStatement();
                        ResultSet query = statement.executeQuery(verifyLogin);
                        out = new PrintWriter(socket.getOutputStream(), true);

                        while (query.next()) {
                            if (query.getInt(1) == 1) {
                                out.println("1");
                            } else {
                                out.println("0");
                            }
                        }
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }finally {
                        server.close();
                    }
                }
                else if(data[0].equals("2")){
                    String registerUser="INSERT INTO voter VALUES("+data[1]+",'"+data[2]+"','"+data[3]+"','"+data[4]+"','"+data[5]+"')";
                    out = new PrintWriter(socket.getOutputStream(), true);
                    try{
                        Statement statement=connectDB.createStatement();
                        int rows = statement.executeUpdate(registerUser);

                        if(rows>0){
                            out.println("1");
                        }
                        else{
                            out.println("0");
                        }

                    } catch (SQLException e) {
                        out.println("0");
                        e.printStackTrace();
                    } finally {
                        server.close();
                    }
                }else if (data[0].equals("3")) {
                    String getall = "select * from candidates;";
                    out = new PrintWriter(socket.getOutputStream(), true);
                    ResultSet query;
                    try {
                        Statement statement = connectDB.createStatement();
                        boolean hasResults = statement.execute(getall);
                        if(hasResults){
                            query = statement.executeQuery(getall);
                            out = new PrintWriter(socket.getOutputStream(), true);
                            String alldata="";
                            while (query.next()) {
                                    int id=query.getInt("candidate_id");
                                    String name=query.getNString("cname");
                                    String dob=query.getString("dob");
                                    String gender=query.getString("gender");
                                    String ph = query.getString("ph");
                                    alldata+=id+"./d"+name+"./d"+dob+"./d"+gender+"./d"+ph;
                                    System.out.println(alldata);
                                alldata+="./e";
                            }
                            out.println(alldata);
                        }else
                            out.println("");
                    } catch (SQLException e) {
                        out.println("");
                        e.printStackTrace();
                    }finally {
                        server.close();
                    }
                }
                else if (data[0].equals("4")){
                    String predel="delete from candidates where candidate_id="+data[1]+";";
                    String delete="delete from candidates where candidate_id="+Integer.parseInt(data[1])+";";
                    out = new PrintWriter(socket.getOutputStream(), true);
                    try{
                        Statement statement=connectDB.createStatement();
                        int r = statement.executeUpdate(predel);
                        int rows = statement.executeUpdate(delete);

                        if(rows>0){
                            out.println("1");
                        }
                        else{
                            out.println("0");
                        }

                    } catch (SQLException e) {
                        out.println("0");
                        e.printStackTrace();
                    } finally {
                        server.close();
                    }
                }else if(data[0].equals("5")){
                    String registerUser="INSERT INTO candidates VALUES("+data[1]+",'"+data[2]+"','"+data[3]+"','"+data[4]+"',"+data[5]+")";
                    out = new PrintWriter(socket.getOutputStream(), true);
                    try{
                        Statement statement=connectDB.createStatement();
                        int rows = statement.executeUpdate(registerUser);

                        if(rows>0){
                            out.println("1");
                        }
                        else{
                            out.println("0");
                        }

                    } catch (SQLException e) {
                        out.println("0");
                        e.printStackTrace();
                    } finally {
                        server.close();
                    }
                }else if (data[0].equals("6")) {
                    String getall = "select candidate_id,cname from candidates;";
                    out = new PrintWriter(socket.getOutputStream(), true);
                    ResultSet query;
                    try {
                        Statement statement = connectDB.createStatement();
                        boolean hasResults = statement.execute(getall);
                        if(hasResults){
                            query = statement.executeQuery(getall);
                            out = new PrintWriter(socket.getOutputStream(), true);
                            String alldata="";
                            while (query.next()) {
                                int id=query.getInt("candidate_id");
                                String name=query.getNString("cname");
                                alldata+=id+"./d"+name;
                                alldata+="./e";
                            }
                            out.println(alldata);
                        }else
                            out.println("");
                    } catch (SQLException e) {
                        out.println("");
                        e.printStackTrace();
                    }finally {
                        server.close();
                    }
                }else if(data[0].equals("7")){
                    String getId="select id from voter where email = '"+data[1]+"';";
                    try {
                        Statement statement = connectDB.createStatement();
                        ResultSet query = statement.executeQuery(getId);
                        out = new PrintWriter(socket.getOutputStream(), true);

                        while (query.next()) {
                            out.println(query.getString(1));
                        }
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }finally {
                        server.close();
                    }
                }else if(data[0].equals("8")){
                    String addVote="INSERT INTO votes VALUES("+data[1]+","+data[2]+");";
                    out = new PrintWriter(socket.getOutputStream(), true);
                    try{
                        Statement statement=connectDB.createStatement();
                        int rows = statement.executeUpdate(addVote);

                        if(rows>0){
                            out.println("1");
                        }
                        else{
                            out.println("0");
                        }

                    } catch (SQLException e) {
                        out.println("0");
                        e.printStackTrace();
                    } finally {
                        server.close();
                    }
                } else if (data[0].equals("9")) {
                    String result="select cname, count(voter_id) 'Number of votes' from votes,candidates where votes.candidate_id=candidates.candidate_id group by votes.candidate_id;";
                    out = new PrintWriter(socket.getOutputStream(), true);
                    ResultSet query;
                    try {
                        Statement statement = connectDB.createStatement();
                        boolean hasResults = statement.execute(result);
                        if(hasResults){
                            query = statement.executeQuery(result);
                            out = new PrintWriter(socket.getOutputStream(), true);
                            String alldata="";
                            while (query.next()) {
                                String name =query.getNString("cname");
                                int n=query.getInt("Number of votes");
                                alldata+=name+"./d"+n;
                                alldata+="./e";
                            }
                            out.println(alldata);
                        }else
                            out.println("");
                    } catch (SQLException e) {
                        out.println("");
                        e.printStackTrace();
                    }finally {
                        server.close();
                    }
                } else if (data[0].equals("10")) {
                    String check= "select count(*) from votes where voter_id="+data[1]+";";
                    try {
                        Statement statement = connectDB.createStatement();
                        ResultSet query = statement.executeQuery(check);
                        out = new PrintWriter(socket.getOutputStream(), true);

                        while (query.next()) {
                            if (query.getInt(1) == 1) {
                                out.println("1");
                            } else {
                                out.println("0");
                            }
                        }
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }finally {
                        server.close();
                    }
                }else if(data[0].equals("11")){
                    String verifyLogin = "select cname, count(voter_id) as v from votes, candidates where votes.candidate_id=candidates.candidate_id group by votes.candidate_id order by v desc limit 1;";
                    try {
                        Statement statement = connectDB.createStatement();
                        ResultSet query = statement.executeQuery(verifyLogin);
                        out = new PrintWriter(socket.getOutputStream(), true);

                        while (query.next()) {
                            String name=query.getString("cname");
                            out.println(name);
                        }
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }finally {
                        server.close();
                    }
                } else if (data[0].equals("12")) {
                    String start = "update flag set val=1 where n=1;";
                    String deletevs="delete from votes where 1=1;";
                    String deletev="delete from voter where 1=1;";
                    String deletec="delete from candidates where 1=1;";
                    out = new PrintWriter(socket.getOutputStream(), true);
                    try{
                        Statement statement=connectDB.createStatement();
                        int rows1=statement.executeUpdate(deletevs);
                        int rows2=statement.executeUpdate(deletev);
                        int rows3 = statement.executeUpdate(deletec);
                        int rows = statement.executeUpdate(start);

                        if(rows>0){
                            out.println("1");
                        }
                        else{
                            out.println("0");
                        }

                    } catch (SQLException e) {
                        out.println("0");
                        e.printStackTrace();
                    } finally {
                        server.close();
                    }

                }else if(data[0].equals("13")){
                    String stop="update flag set val=0 where n=1;";
                    out = new PrintWriter(socket.getOutputStream(), true);
                    try{
                        Statement statement=connectDB.createStatement();
                        int rows = statement.executeUpdate(stop);

                        if(rows>0){
                            out.println("1");
                        }
                        else{
                            out.println("0");
                        }

                    } catch (SQLException e) {
                        out.println("0");
                        e.printStackTrace();
                    } finally {
                        server.close();
                    }
                }else if(data[0].equals("14")){
                    String logincheck = "select val from flag;";
                    try {
                        Statement statement = connectDB.createStatement();
                        ResultSet query = statement.executeQuery(logincheck);
                        out = new PrintWriter(socket.getOutputStream(), true);

                        while (query.next()) {
                            if(query.getInt("val")==1)
                                out.println("1");
                            else
                                out.println("0");
                        }
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }finally {
                        server.close();
                    }
                }else if (data[0].equals("15")) {
                    String getall = "select * from voter;";
                    out = new PrintWriter(socket.getOutputStream(), true);
                    ResultSet query;
                    try {
                        Statement statement = connectDB.createStatement();
                        boolean hasResults = statement.execute(getall);
                        if(hasResults){
                            query = statement.executeQuery(getall);
                            out = new PrintWriter(socket.getOutputStream(), true);
                            String alldata="";
                            while (query.next()) {
                                int id=query.getInt("id");
                                String name=query.getNString("name");
                                String dob=query.getString("email");
                                String gender=query.getString("gender");
                                String ph = query.getString("password");
                                alldata+=id+"./d"+name+"./d"+dob+"./d"+gender+"./d"+ph;
                                System.out.println(alldata);
                                alldata+="./e";
                            }
                            out.println(alldata);
                        }else
                            out.println("");
                    } catch (SQLException e) {
                        out.println("");
                        e.printStackTrace();
                    }finally {
                        server.close();
                    }
                }else if(data[0].equals("16")){
                    String registerUser="INSERT INTO voter VALUES("+data[1]+",'"+data[2]+"','"+data[3]+"','"+data[4]+"','"+data[5]+"')";
                    out = new PrintWriter(socket.getOutputStream(), true);
                    try{
                        Statement statement=connectDB.createStatement();
                        int rows = statement.executeUpdate(registerUser);

                        if(rows>0){
                            out.println("1");
                        }
                        else{
                            out.println("0");
                        }

                    } catch (SQLException e) {
                        out.println("0");
                        e.printStackTrace();
                    } finally {
                        server.close();
                    }
                }else if (data[0].equals("17")){
                    String predel="delete from votes where voter_id="+data[1]+";";
                    String delete="delete from voter where id="+data[1]+";";
                    out = new PrintWriter(socket.getOutputStream(), true);
                    try{
                        Statement statement=connectDB.createStatement();
                        int r = statement.executeUpdate(predel);
                        int rows = statement.executeUpdate(delete);

                        if(rows>0){
                            out.println("1");
                        }
                        else{
                            out.println("0");
                        }

                    } catch (SQLException e) {
                        out.println("0");
                        e.printStackTrace();
                    } finally {
                        server.close();
                    }
                }
            }
        }
        catch(IOException i)
            {
                System.out.println(i);
            }
        }
}