import java.sql.*;
import java.util.Scanner;

public class tableCreate {
    static void insertMovie(Connection con, Scanner sc) {
        System.out.println("Enter the Movie name:");
        String movieName = sc.next();
        System.out.println("Enter the Lead Actor name/Press Enter:");
        String actorName = sc.next();
        System.out.println("Enter the Lead Actress name/Press Enter:");
        String actressName = sc.next();
        System.out.println("Enter the Director name:");
        String directorName = sc.next();
        System.out.println("Enter the Year of release/Press Enter:");
        int releaseYear = sc.nextInt();
        String insertQuery = "Insert into movies values(?,?,?,?,?)";
        try {
            PreparedStatement p = con.prepareStatement(insertQuery);
            p.setString(1, movieName);
            p.setString(2, actorName);
            p.setString(3, actressName);
            p.setString(4, directorName);
            p.setInt(5, releaseYear);
            int flag = p.executeUpdate();
            if (flag != 0) {
                System.out.println("Movie is added successfully.");
            } else {
                System.out.println("Error occured During inserting.");
            }
        } catch (SQLException e) {
            System.out.println("Error:" + e);
        }
    }

    static void selectMovie(Connection con, Scanner sc) {
        System.out.println(
                "Select the search Criteria:\n 1)Movie Name \n 2)Actor Name \n 3)Actress Name \n 4)Director Name\n2 5)Year of release");
        int opt = sc.nextInt();
        String query;
        if (opt == 1) {
            System.out.println("Enter the Movie name:");
            String movieName = sc.next();
            query = "select * from movies where name='" + movieName + "'";
        } else if (opt == 2) {
            System.out.println("Enter the Lead Actor name:");
            String actorName = sc.next();
            query = "select * from movies where actor='" + actorName + "'";
        } else if (opt == 3) {
            System.out.println("Enter the Lead Actress name:");
            String actressName = sc.next();
            query = "select * from movies where actress='" + actressName + "'";
        } else if (opt == 4) {
            System.out.println("Enter the Director name:");
            String directorName = sc.next();
            query = "select * from movies where director='" + directorName + "'";
        } else {
            System.out.println("Enter the Year of release:");
            int releaseYear = sc.nextInt();
            query = "select * from movies where ryear='" + releaseYear + "'";
        }
        Statement s;
        try {
            s = con.createStatement();
            ResultSet rs = s.executeQuery(query);
            System.out.println("");
            System.out.printf("%-15s%-15s%-15s%-15s%-4s\n", "Movie Name", "Actor Name", "Actress Name", "Director Name",
                    "Year");
            while (rs.next()) {
                System.out.printf("%-15s%-15s%-15s%-15s%-4s\n", rs.getString(1), rs.getString(2), rs.getString(3),
                        rs.getString(4), rs.getInt(5));
            }
        } catch (SQLException e1) {

            System.out.println("Error:" + e1);
        }
    }

    static void movieList(Connection con, Scanner sc) {
        String query = "select * from movies";
        Statement s;
        try {
            s = con.createStatement();
            ResultSet rs = s.executeQuery(query);
            System.out.printf("%-15s%-15s%-15s%-15s%-4s\n", "Movie Name", "Actor Name", "Actress Name", "Director Name",
                    "Year");
            while (rs.next()) {
                System.out.printf("%-15s%-15s%-15s%-15s%-4s\n", rs.getString(1), rs.getString(2), rs.getString(3),
                        rs.getString(4), rs.getInt(5));
            }
        } catch (SQLException e) {
            System.out.println("Error:" + e);
        }
    }

    public static void main(String[] args) {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/mulesoft", "root",
                    "");
            Scanner sc = new Scanner(System.in);
            Statement smt = con.createStatement();
            // con.setAutoCommit(false);
            // String creationiQuery = "CREATE TABLE MOVIES(NAME VARCHAR(20),ACTOR
            // VARCHAR(20),ACTRESS VARCHAR(20),DIRECTOR VARCHAR(20),RYEAR INT(4))";
            // int flag = smt.executeUpdate(creationiQuery);
            while (true) {
                System.out.println(
                        "\nChoose the options: \n 1)Insert Movie \n 2)Select Movies \n 3)Movies List \n 4)Stop");
                int opt = sc.nextInt();
                if (opt == 1) {
                    insertMovie(con, sc);
                } else if (opt == 2) {
                    selectMovie(con, sc);
                } else if (opt == 3) {
                    movieList(con, sc);
                } else {
                    break;
                }
            }
            // System.out.println("Database Created Successfully");
            // con.commit();
            smt.close();
            con.close();
        } catch (Exception e) {
            System.out.println("Error: " + e);
        }
    }
}