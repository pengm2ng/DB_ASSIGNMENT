import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

import javax.swing.plaf.synth.SynthSpinnerUI;

public class SqlTest3 {
    public static void main(String[] args) throws SQLException {

        Connection conn = null;
        Statement state = null;
        PreparedStatement prep = null;
        ResultSet res = null;
        String jdbcURL = "jdbc:postgresql://localhost:5432/assignment3";
        String user = "ajouDatabase";
        String password = "captainajou";

        try {
            Scanner scan = new Scanner(System.in);

            System.out.println("SQL Programming Test");

            System.out.println("Connecting PostgreSQL database");
            // JDBC를 이용해 PostgreSQL 서버 및 데이터베이스 연결

            conn = DriverManager.getConnection(jdbcURL, user, password);
            System.out.println("connection sueccess");

            
            state = conn.createStatement();

             // 3개 테이블 생성: Create table문 이용
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

            scan.nextLine();

            // Trigger R2 생성
            // Delete문 실행
            // Query 1 실행하고 결과는 적절한 Print문을 이용해 Display

            System.out.println("Trigger test 1");
            state = conn.createStatement();
            state.execute(
                    "create or replace function deleteTrigger() returns Trigger as $$ begin delete from Apply where sID = Old.sID; return new; end; $$ language  'plpgsql';");
            state.execute("create TRIGGER R2 after delete on student for each row execute procedure deletetrigger();");
            state.execute("delete from student where gpa < 3.5;");

            System.out.println("query 1");
            prep = conn.prepareStatement("select * from Student order by sID;");

            ResultSet result = prep.executeQuery();
            int count = 0;
            System.out.println("\t" + " " + "\t|" + String.format("%5s", "sID") + "\t|" + String.format("%10s", "sName")
                    + "\t|" + String.format("%5s", "gpa") + "\t|" + String.format("%10s", "sizeHS") + "\t|");
            System.out.println("-----------------------------------------------------------------------");
            while (result.next()) {
                count++;
                String sid = result.getString(1);
                String sName = result.getString(2);
                String gpa = result.getString(3);
                String sizeHS = result.getString(4);

                System.out
                        .println("\t" + count + "\t|" + String.format("%5s", sid) + "\t|" + String.format("%10s", sName)
                                + "\t|" + String.format("%5s", gpa) + "\t|" + String.format("%10s", sizeHS) + "\t|");
            }
            System.out.println("\n");

            prep.close();
            state.close();
            result.close();

            System.out.println("Continue? (Enter 1 for continue)");
            scan.nextLine();
            System.out.println();

            // Query 2 실행하고 결과는 적절한 Print문을 이용해 Display
            // Tuple print시 Tuple 번호도 함께 print (예시는 위 “Trigger test 1” 참조)
            System.out.println("query 2");
            prep = conn.prepareStatement("select * from Apply order by sID, cName, major;");
            result = prep.executeQuery();
            count = 0;
            System.out.println("\t" + " " + "\t|" + String.format("%5s", "sID") + "\t|" + String.format("%10s", "CName")
                    + "\t|" + String.format("%20s", "major") + "\t|" + String.format("%10s", "decision") + "\t|");
            System.out
                    .println("--------------------------------------------------------------------------------------");
            while (result.next()) {
                count++;
                String sID = result.getString(1);
                String cName = result.getString(2);
                String major = result.getString(3);
                String decision = result.getString(4);
                System.out.println(
                        "\t" + count + "\t|" + String.format("%5s", sID) + "\t|" + String.format("%10s", cName) + "\t|"
                                + String.format("%20s", major) + "\t|" + String.format("%10s", decision) + "\t|");
            }
            System.out.println("\n");

            prep.close();
            result.close();

            System.out.println("Continue? (Enter 1 for continue)");
            scan.nextLine();
            System.out.println();

            // Trigger R4 생성
            // Insert문 실행
            // Query 3 실행하고 결과는 적절한 Print문을 이용해 Display
            // Tuple print시 Tuple 번호도 함께 print (예시는 위 “Trigger test 1” 참조)
            System.out.println("Trigger test 2");
            state = conn.createStatement();
            state.execute(
                    "create or replace function ignoreOrInsert() returns Trigger as $$ begin IF exists(select * from College where cName = New.cName) THEN return null; ELSE return New; END IF; end; $$ language 'plpgsql';");
            state.execute(
                    "create TRIGGER R4 before insert on college for each row execute procedure ignoreOrInsert();");
            state.execute("insert into College values ('UCLA', 'CA', 20000);");
            state.execute("insert into College values ('MIT', 'hello', 10000);");
            prep = conn.prepareStatement("select * from College order by cName;");
            result = prep.executeQuery();
            count = 0;

            System.out.println("query 3");
            System.out.println("\t" + " " + "\t|" + String.format("%10s", "cName") + "\t|"
                    + String.format("%10s", "states") + "\t|" + String.format("%15s", "enrollment") + "\t|");
            System.out.println("------------------------------------------------------------------------------");

            while (result.next()) {
                count++;
                String cName = result.getString(1);
                String states = result.getString(2);
                String enrollment = result.getString(3);
                System.out.println("\t");
                System.out.println("\t" + count + "\t|" + String.format("%10s", cName) + "\t|"
                        + String.format("%10s", states) + "\t|" + String.format("%15s", enrollment) + "\t|");
            }
            System.out.println("\n");
            prep.close();
            state.close();
            result.close();

            // 테이블 및 튜플을 위 “2) 테이블, 튜플”의 상태로 PgAdmin을 이용해 리셋
            System.out.println("Continue? (Enter 1 for continue)");
            scan.nextLine();
            System.out.println("Drop table");

            state = conn.createStatement();
            state.execute("DROP TABLE APPLY cascade");
            state.execute("DROP TABLE STUDENT cascade");
            state.execute("DROP TABLE COLLEGE cascade");

            System.out.println("Create previous tables");
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
            System.out.println("Create Table Success");
            System.out.println("Continue? (Enter 1 for continue)");
            scan.nextLine();

            // View CSEE 생성
            // Query 4 실행하고 결과는 적절한 Print문을 이용해 Display
            // Tuple print시 Tuple 번호도 함께 print (예시는 위 “Trigger test 1” 참조)
            System.out.println("View test 1");
            System.out.println("query 4");
            state = conn.createStatement();
            state.execute(
                    "create view CSEE as select  sID, cName, major from apply where  major = 'CS' OR major = 'EE';");
            prep = conn.prepareStatement("select * from CSEE order by sID, cName, major;");

            result = prep.executeQuery();
            count = 0;

            System.out.println("\t" + " " + "\t|" + String.format("%5s", "sID") + "\t|" + String.format("%15s", "cName")
                    + "\t|" + String.format("%20s", "major") + "\t|");
            System.out.println("------------------------------------------------------------------------------");

            while (result.next()) {
                count++;
                String sid = result.getString(1);
                String cName = result.getString(2);
                String major = result.getString(3);

                System.out.println("\t" + count + "\t|" + String.format("%5s", sid) + "\t|"
                        + String.format("%15s", cName) + "\t|" + String.format("%20s", major) + "\t|");
            }
            System.out.println("\n");
            state.close();
            prep.close();
            result.close();

            System.out.println("Continue? (Enter 1 for continue)");
            scan.nextLine();

            // Trigger CSEEinsert 생성
            // Insert문 실행
            // Query 5 실행하고 결과는 적절한 Print문을 이용해 Display
            // Tuple print시 Tuple 번호도 함께 print (예시는 위 “Trigger test 1” 참조)

            System.out.println("View test 2");
            state = conn.createStatement();
            state.execute(
                    "create or replace function insertCSEE() returns Trigger as $$ begin IF NEW.major = 'CS' or New.major = 'EE' THEN insert into apply VALUES (NEW.sid, NEW.cName, NEW.major, null); return NEW; ELSE RETURN NULL; END IF; end; $$ language 'plpgsql';");
            state.execute(
                    "create TRIGGER CSEEinsert instead of insert on csee for each row execute procedure insertcsee();");
            state.execute("insert into CSEE values (333, 'UCLA', 'biology');");

            System.out.println("query 5");
            prep = conn.prepareStatement("select * from CSEE order by sID, cName, major; ");
            result = prep.executeQuery();
            count = 0;

            System.out.println("\t" + " " + "\t|" + String.format("%5s", "sID") + "\t|" + String.format("%15s", "cName")
                    + "\t|" + String.format("%20s", "major") + "\t|");
            System.out.println("------------------------------------------------------------------------------");
            while (result.next()) {
                count++;
                String sid = result.getString(1);
                String cName = result.getString(2);
                String major = result.getString(3);

                System.out.println("\t" + count + "\t|" + String.format("%5s", sid) + "\t|"
                        + String.format("%15s", cName) + "\t|" + String.format("%20s", major) + "\t|");
            }
            System.out.println("\n");
            state.close();
            prep.close();
            result.close();
            System.out.println("Continue? (Enter 1 for continue)");
            scan.nextLine();

            // Query 6 실행하고 결과는 적절한 Print문을 이용해 Display
            // Tuple print시 Tuple 번호도 함께 print (예시는 위 “Trigger test 1” 참조)
            System.out.println("query 6");
            prep = conn.prepareStatement("select * from Apply order by sID, cName, major;");
            result = prep.executeQuery();
            count = 0;

            System.out.println("\t" + " " + "\t|" + String.format("%5s", "sID") + "\t|" + String.format("%15s", "cName")
                    + "\t|" + String.format("%20s", "major") + "\t|" + String.format("%10s", "decision") + "\t|");
            System.out.println(
                    "---------------------------------------------------------------------------------------------");
            while (result.next()) {
                count++;
                String sid = result.getString(1);
                String cName = result.getString(2);
                String major = result.getString(3);
                String decision = result.getString(4);

                System.out.println(
                        "\t" + count + "\t|" + String.format("%5s", sid) + "\t|" + String.format("%15s", cName) + "\t|"
                                + String.format("%20s", major) + "\t|" + String.format("%10s", decision) + "\t|");
            }
            System.out.println("\n");
            state.close();
            prep.close();
            result.close();
            System.out.println("Continue? (Enter 1 for continue)");
            scan.nextLine();

            // Insert문 실행
            // Query 7 실행하고 결과는 적절한 Print문을 이용해 Display
            // Tuple print시 Tuple 번호도 함께 print (예시는 위 “Trigger test 1” 참조)
            System.out.println("View test 3");
            state = conn.createStatement();
            state.execute("insert into CSEE values (333, 'UCLA', 'CS');");

            System.out.println("query 7");
            prep = conn.prepareStatement("select * from CSEE order by sID, cName, major;");
            result = prep.executeQuery();
            count = 0;

            System.out.println("\t" + " " + "\t|" + String.format("%5s", "sID") + "\t|" + String.format("%15s", "cName")
                    + "\t|" + String.format("%20s", "major") + "\t|");
            System.out.println("------------------------------------------------------------------------------");
            while (result.next()) {
                count++;
                String sid = result.getString(1);
                String cName = result.getString(2);
                String major = result.getString(3);

                System.out.println("\t" + count + "\t|" + String.format("%5s", sid) + "\t|"
                        + String.format("%15s", cName) + "\t|" + String.format("%20s", major) + "\t|");
            }
            System.out.println("\n");
            state.close();
            prep.close();
            result.close();
            System.out.println("Continue? (Enter 1 for continue)");
            scan.nextLine();

         // Query 8 실행하고 결과는 적절한 Print문을 이용해 Display
            System.out.println("query 8");
            prep = conn.prepareStatement("select * from Apply order by sID, cName, major;");
            result = prep.executeQuery();
            count = 0;

            System.out.println("\t" + " " + "\t|" + String.format("%5s", "sID") + "\t|" + String.format("%15s", "cName")
                    + "\t|" + String.format("%20s", "major") + "\t|" + String.format("%10s", "decision") + "\t|");
            System.out.println(
                    "---------------------------------------------------------------------------------------------");
            while (result.next()) {
                count++;
                String sid = result.getString(1);
                String cName = result.getString(2);
                String major = result.getString(3);
                String decision = result.getString(4);

                System.out.println(
                        "\t" + count + "\t|" + String.format("%5s", sid) + "\t|" + String.format("%15s", cName) + "\t|"
                                + String.format("%20s", major) + "\t|" + String.format("%10s", decision) + "\t|");
            }
            System.out.println("\n");
            state.close();
            prep.close();
            result.close();

            conn.close();

        } catch (SQLException ex) {
            throw ex;
        }
    }
}