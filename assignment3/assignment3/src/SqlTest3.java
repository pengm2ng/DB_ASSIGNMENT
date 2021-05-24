import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class SqlTest3 {
    public static void main(String[] args) throws SQLException {

        Connection conn = null;
        Statement state = null;
        PreparedStatement prep = null;
        ResultSet res = null;
        String jdbcURL = "jdbc:postgresql://localhost:5432/assignment3";
        String user = "";
        String password = "";

        try {
            Scanner scan = new Scanner(System.in);

            System.out.println("SQL Programming Test");

            System.out.println("Connecting PostgreSQL database");
            // JDBC를 이용해 PostgreSQL 서버 및 데이터베이스 연결

            conn = DriverManager.getConnection(jdbcURL, user, password);
            System.out.println("connection sueccess");

            // 3개 테이블 생성: Create table문 이용
            state = conn.createStatement();
            state.execute("create table College(cName varchar(20), state varchar(20), enrollment int)");
            state.execute("create table Student(sID int, sName varchar(20), GPA numeric(2,1), sizeHS int)");
            state.execute("create table Apply(sID int, cName varchar(20), major varchar(20), decision char)");

            System.out.println("Inserting tuples to College, Student, Apply relations");
            // 3개 테이블에 튜플 생성: Insert문 이용

            state.execute("insert into College values ('Stanford', 'CA', 15000)");
            state.execute("insert into College values ('Berkeley', 'CA', 36000)");
            state.execute("insert into College values ('MIT', 'MA', 10000)");
            state.execute("insert into College values ('Cornell', 'NY', 21000)");
            state.execute("insert into Student values (123, 'Amy', 3.9, 1000)");
            state.execute("insert into Student values (234, 'Bob', 3.6, 1500)");
            state.execute("insert into Student values (345, 'Craig', 3.5, 500)");
            state.execute("insert into Student values (456, 'Doris', 3.9, 1000)");
            state.execute("insert into Student values (567, 'Edward', 2.9, 2000)");
            state.execute("insert into Student values (678, 'Fay', 3.8, 200)");
            state.execute("insert into Student values (789, 'Gary', 3.4, 800)");
            state.execute("insert into Student values (987, 'Helen', 3.7, 800)");
            state.execute("insert into Student values (876, 'Irene', 3.9, 400)");
            state.execute("insert into Student values (765, 'Jay', 2.9, 1500)");
            state.execute("insert into Student values (654, 'Amy', 3.9, 1000)");
            state.execute("insert into Student values (543, 'Craig', 3.4, 2000)");
            state.execute("insert into Apply values (123, 'Stanford', 'CS', 'Y')");
            state.execute("insert into Apply values (123, 'Stanford', 'EE', 'N')");
            state.execute("insert into Apply values (123, 'Berkeley', 'CS', 'Y')");
            state.execute("insert into Apply values (123, 'Cornell', 'EE', 'Y')");
            state.execute("insert into Apply values (234, 'Berkeley', 'biology', 'N')");
            state.execute("insert into Apply values (345, 'MIT', 'bioengineering', 'Y')");
            state.execute("insert into Apply values (345, 'Cornell', 'bioengineering', 'N')");
            state.execute("insert into Apply values (345, 'Cornell', 'CS', 'Y')");
            state.execute("insert into Apply values (345, 'Cornell', 'EE', 'N')");
            state.execute("insert into Apply values (678, 'Stanford', 'history', 'Y')");
            state.execute("insert into Apply values (987, 'Stanford', 'CS', 'Y')");
            state.execute("insert into Apply values (987, 'Berkeley', 'CS', 'Y')");
            state.execute("insert into Apply values (876, 'Stanford', 'CS', 'N')");
            state.execute("insert into Apply values (876, 'MIT', 'biology', 'Y')");
            state.execute("insert into Apply values (876, 'MIT', 'marine biology', 'N')");
            state.execute("insert into Apply values (765, 'Stanford', 'history', 'Y')");
            state.execute("insert into Apply values (765, 'Cornell', 'history', 'N')");
            state.execute("insert into Apply values (765, 'Cornell', 'psychology', 'Y')");
            state.execute("insert into Apply values (543, 'MIT', 'CS', 'N')");
            System.out.println();
            state.close();
            
            System.out.println("Continue? (Enter 1 for continue)");
            scan.nextLine();

            System.out.println("Query 1");
            // Query 1을 실행: Select문 이용
            // Query 처리 결과는 적절한 Print문을 이용해 Display

            prep = conn.prepareStatement("select * from College;");

            ResultSet result = prep.executeQuery();

            while (result.next()) {
                String cName = result.getString(1);
                String states = result.getString(2);
                String enrollment = result.getString(3);
                System.out.println("\t|" + String.format("%10s", cName) + "\t|" + String.format("%5s", states) + "\t|"
                        + String.format("%10s", enrollment) + "\t|");
            }
            System.out.println("\n");

            System.out.println("Continue? (Enter 1 for continue)");
            scan.nextLine();
            // Query 2 ~ Query 5에 대해 Query 1과 같은 방식으로 실행: Select문 이용
            // Query 처리 결과는 적절한 Print문을 이용해 Display
            // query2
            prep.close();
            result.close();

            System.out.println("Query 2");
            System.out.println();
            prep = conn.prepareStatement("select * from Student;");
            result = prep.executeQuery();

            while (result.next()) {
                String sID = result.getString(1);
                String sName = result.getString(2);
                String gpa = result.getString(3);
                String sizeHS = result.getString(4);
                System.out.println("\t|" + String.format("%5s", sID) + "\t|" + String.format("%10s", sName) + "\t|"
                        + String.format("%5s", gpa) + "\t|" + String.format("%10s", sizeHS) + "\t|");
            }
            System.out.println("\n");

            prep.close();
            result.close();

            // query3

            System.out.println("Query 3");
            prep = conn.prepareStatement("select * from Apply;");
            result = prep.executeQuery();

            while (result.next()) {
                String sID = result.getString(1);
                String cName = result.getString(2);
                String major = result.getString(3);
                String decision = result.getString(4);

                System.out.println("\t|" + String.format("%5s", sID) + "\t|" + String.format("%10s", cName) + "\t|"
                        + String.format("%20s", major) + "\t|" + String.format("%5s", decision) + "\t|");
            }
            System.out.println("\n");
            prep.close();
            result.close();
            // query4
            System.out.println("Query 4");
            // 11개

            prep = conn.prepareStatement(
                    "select cName, major, min(GPA), max(GPA) from Student, Apply where Student.sID = Apply.sID group by cName, major having min(GPA) > 3.0 order by cName, major; ");

            result = prep.executeQuery();

            while (result.next()) {
                String cName = result.getString(1);
                String major = result.getString(2);
                String gpaMin = result.getString(3);
                String gpaMax = result.getString(4);
                System.out.println("\t|" + String.format("%10s", cName) + "\t|" + String.format("%20s", major) + "\t|"
                        + String.format("%5s", gpaMin) + "\t|" + String.format("%5s", gpaMax) + "\t|");
            }
            System.out.println("\n");
            prep.close();
            result.close();
            // query5
            // 2개

            System.out.println("Query 5");

            prep = conn.prepareStatement(
                    "select distinct cName from Apply A1 where 6 > (select count(*) from Apply A2 where A2.cName = A1.cName); ");
            result = prep.executeQuery();

            while (result.next()) {
                String cName = result.getString(1);

                System.out.println(String.format("\t|" + "%10s", cName) + "\t|");
            }
            System.out.println("\n");

            System.out.println("Continue? (Enter 1 for continue)");
            scan.nextLine();
            prep.close();
            result.close();

            System.out.println("Query 6");
            // Query 6을 실행: Select문 이용
            // 사용자에게 major1, major2 값으로 CS, EE 입력 받음
            // 입력 받은 값을 넣어 Query를 처리하고
            // 결과는 적절한 Print문을 이용해 Display
            // 3개
            System.out.println("major1을 입력하시오.");
            String major1 = scan.nextLine();
            System.out.println("\nmajor2를 입력하시오.");
            String major2 = scan.nextLine();
            prep = conn.prepareStatement(
                    "select sID, sName from Student where sID = any (select sID from Apply where major = ?) and not sID = any (select sID from Apply where major = ?); ");
            prep.setString(1, major1);
            prep.setString(2, major2);
            result = prep.executeQuery();

            while (result.next()) {
                String sID = result.getString(1);
                String sName = result.getString(2);
                System.out.println(String.format("\t|" + "%5s", sID) + "\t|" + String.format("%10s", sName) + "\t|");
            }

            System.out.println("\n");

            System.out.println("Continue? (Enter 1 for continue)");
            scan.nextLine();
            prep.close();
            result.close();

            System.out.println("Query 7");
            // Query 7을 실행: Select문 이용
            // 사용자에게 major, cName 값으로 CS, Stanford 입력 받음
            // 입력 받은 값을 넣어 Query를 처리하고
            // 결과는 적절한 Print문을 이용해 Displa
            // 3개
            System.out.println("major를 입력하시오.");
            String major = scan.nextLine();
            System.out.println("\nCollege Name을 입력하시오.");
            String cName = scan.nextLine();

            prep = conn.prepareStatement(
                    "select sName, GPA from Student natural join Apply where major = ? and cName = ?");
            prep.setString(1, major);
            prep.setString(2, cName);
            result = prep.executeQuery();

            while (result.next()) {
                String sName = result.getString(1);
                String gpa = result.getString(2);
                System.out.println("\t|" + String.format("%10s", sName) + "\t|" + String.format("%5s", gpa) + "\t|");
            }
            System.out.println("\n");

            System.out.println("Database Connection Close\n");
            prep.close();
            result.close();
            conn.close();

        } catch (SQLException ex) {
            throw ex;
        }
    }
}