import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Review05 {

    public static void main(String[] args) {
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

        //ConnectionでDBと接続
            con = DriverManager.getConnection("jdbc:mysql://localhost/kadaidb?useSSL=false&allowPublicKeyRetrieval=true",
                        "root",
                        "-lyQad_41+J8$,w"
                        );
        //PreparedStatementオブジェクトを作成
            pstmt = con.prepareStatement("SELECT * FROM person WHERE id = ?");

        //Select文を実行して結果を格納/代入
            System.out.print("検索キーワードを入力してください > ");
            int input = setInt();

        //ResultSet
            pstmt.setInt(1, input);
            rs = pstmt.executeQuery();

            while (rs.next()){
                System.out.println(rs.getString("name"));
                System.out.println(rs.getInt("age"));
            }

        } catch (ClassNotFoundException e) {
            System.err.println("JDBCドライバのロードに失敗しました。");
            e.printStackTrace();
        } catch (SQLException e) {
            System.err.println("データベースに異常が発生しました。");
            e.printStackTrace();
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch(SQLException e) {
                    System.err.println("ResultSetを閉じる時にエラーが発生しました。");
                    e.printStackTrace();
                }
            }
            if (pstmt != null) {
                try {
                    pstmt.close();
                } catch(SQLException e) {
                    System.err.println("PreparedStatementを閉じる時にエラーが発生しました。");
                    e.printStackTrace();
                }
            }
            if (con != null) {
                try {
                    con.close();
                } catch(SQLException e) {
                    System.err.println("データベースの切断時にエラーが発生しました。");
                    e.printStackTrace();
                }
            }
        }
    }

    private static int setInt() {
        int result = 0;
        try {
            BufferedReader key = new BufferedReader(new InputStreamReader(System.in));
            String line = key.readLine();
            result = Integer.parseInt(line);
        } catch (IOException e) {
            System.err.println("入力に例外が発生しました。");
            e.printStackTrace();
        } catch (NumberFormatException e) {
            System.err.println("整数ではない文字が入力されました。");
            e.printStackTrace();
        }
        return result;
    }

}
