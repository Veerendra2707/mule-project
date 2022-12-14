import java.sql.*;
import java.util.Scanner;

public class MovieDatabase {
    private static void insertMovie(Connection con, Scanner sc) {
        System.out.println("Enter the Movie name:");
        String movieName = sc.nextLine();
        System.out.println("Enter the Lead Actor name/Press Enter:");
        String actorName = sc.nextLine();
        System.out.println("Enter the Lead Actress name/Press Enter:");
        String actressName = sc.nextLine();
        System.out.println("Enter the Director name:");
        String directorName = sc.nextLine();
        System.out.println("Enter the Year of release/Press Enter:");
        int releaseYear = Integer.parseInt(sc.nextLine());
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

    private static void selectMovie(Connection con, Scanner sc) {
        System.out.println(
                "Select the search Criteria:\n 1)Movie Name  2)Actor Name  3)Actress Name  4)Director Name  5)Year of release");
        int opt = Integer.parseInt(sc.nextLine());

        String query;
        if (opt == 1) {
            System.out.println("Enter the Movie name:");
            String movieName = sc.nextLine();
            query = "select * from movies where name='" + movieName + "'";
        } else if (opt == 2) {
            System.out.println("Enter the Lead Actor name:");
            String actorName = sc.nextLine();
            query = "select * from movies where actor='" + actorName + "'";
        } else if (opt == 3) {
            System.out.println("Enter the Lead Actress name:");
            String actressName = sc.nextLine();
            query = "select * from movies where actress='" + actressName + "'";
        } else if (opt == 4) {
            System.out.println("Enter the Director name:");
            String directorName = sc.nextLine();
            query = "select * from movies where director='" + directorName + "'";
        } else {
            System.out.println("Enter the Year of release:");
            int releaseYear = Integer.parseInt(sc.nextLine());
            query = "select * from movies where ryear='" + releaseYear + "'";
        }
        Statement s;
        try {
            s = con.createStatement();
            ResultSet rs = s.executeQuery(query);
            System.out.println("");
            System.out.printf("%-20s%-20s%-20s%-20s%-4s\n", "Movie Name", "Actor Name", "Actress Name", "Director Name",
                    "Year");
            while (rs.next()) {
                System.out.printf("%-20s%-20s%-20s%-20s%-4s\n", rs.getString(1), rs.getString(2), rs.getString(3),
                        rs.getString(4), rs.getInt(5));
            }
        } catch (SQLException e1) {

            System.out.println("Error:" + e1);
        }
    }

    private static void movieList(Connection con, Scanner sc) {
        String query = "select * from movies order by ryear";
        Statement s;
        try {
            s = con.createStatement();
            ResultSet rs = s.executeQuery(query);
            System.out.printf("%-20s%-20s%-20s%-20s%-4s\n", "Movie Name", "Actor Name", "Actress Name", "Director Name",
                    "Year");
            while (rs.next()) {
                System.out.printf("%-20s%-20s%-20s%-20s%-4s\n", rs.getString(1), rs.getString(2), rs.getString(3),
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
            String creationiQuery = "CREATE TABLE MOVIES(NAME VARCHAR(20) NOT NULL,ACTOR VARCHAR(20),ACTRESS VARCHAR(20),DIRECTOR VARCHAR(20) NOT NULL,RYEAR INT(4))";
            int flag = smt.executeUpdate(creationiQuery);
            while (true) {
                System.out.println(
                        "\nChoose the options: \n 1)Insert Movie 2)Select Movies 3)Movies List 4)Stop");
                int opt = Integer.parseInt(sc.nextLine());
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
            // con.commit();
            smt.close();
            con.close();
        } catch (Exception e) {
            System.out.println("Error: " + e);
        }
    }
}