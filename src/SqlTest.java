import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class SqlTest {
    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        

        Class.forName("org.postgresql.Driver");
       
        Connection conn = null;
        Statement state = null;
        ResultSet res = null;
        String jdbcURL = "jdbc:postgresql://localhost:5432/assignment";
        String user = "postgres";
        String password = "song@7250";
        
        try {
            Scanner scan = new Scanner(System.in);
            
            System.out.println("SQL Programming Test");
            
            System.out.println("Connecting PostgreSQL database");
            // JDBC를 이용해 PostgreSQL 서버 및 데이터베이스 연결
            
                    
            
            conn = DriverManager.getConnection(jdbcURL, user, password);
            System.out.println("connection sueccess");

            System.out.println("Creating College, Student, Apply relations");
            // 3개 테이블 생성: Create table문 이용

            System.out.println("Inserting tuples to College, Student, Apply relations");
            // 3개 테이블에 튜플 생성: Insert문 이용

            System.out.println("Continue? (Enter 1 for continue)");
            scan.nextLine();
            System.out.println("Query 1");
            // Query 1을 실행: Select문 이용
            // Query 처리 결과는 적절한 Print문을 이용해 Displ

            System.out.println("Continue? (Enter 1 for continue)");
            scan.nextLine(); // Query 2 ~ Query 5에 대해 Query 1과 같은 방식으로 실행: Select문 이용
            // Query 처리 결과는 적절한 Print문을 이용해 Display

            System.out.println("Continue? (Enter 1 for continue)");
            scan.nextLine();
            System.out.println("Query 6");
            // Query 6을 실행: Select문 이용
            // 사용자에게 major1, major2 값으로 CS, EE 입력 받음
            // 입력 받은 값을 넣어 Query를 처리하고
            // 결과는 적절한 Print문을 이용해 Display

            System.out.println("Continue? (Enter 1 for continue)");
            scan.nextLine();
            System.out.println("Query 7");
            // Query 7을 실행: Select문 이용
            // 사용자에게 major, cName 값으로 CS, Stanford 입력 받음
            // 입력 받은 값을 넣어 Query를 처리하고
            // 결과는 적절한 Print문을 이용해 Displa
            conn.close();
        } catch (SQLException ex) {
            throw ex;
        }
    }
}