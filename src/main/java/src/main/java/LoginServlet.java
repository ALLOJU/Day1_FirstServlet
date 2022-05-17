package src.main.java;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.regex.Pattern;

@WebServlet(
        description = "Login Servlet Testing",
        urlPatterns = {"/LoginServlet"},
        initParams = {
                @WebInitParam(name="user",value="Mounika"),
                @WebInitParam(name="password",value="Bridgelabz")
        }
)
public class LoginServlet extends HttpServlet {

    private static final String FIRST_NAME_PATTERN="^[A-Z]{1}[a-zA-Z]{2}[a-zA-z0-9]*";
    private static final String PASSWORD_PATTERN="^(?=.*[@#$%^&+=])(?=.*[0-9])(?=.*[A-Z]).{8,}$";
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String user=req.getParameter("user");
        boolean validateFirstName = validateFirstName(user);
        boolean checkFirstName = checkFirstName(req, resp, validateFirstName);
        String pwd=req.getParameter("pwd");
        boolean validatePassword = validatePassword(pwd);
        boolean checkPassword = checkPassword(req, resp, validatePassword);
        //get servlet config init params
        String userID=getServletConfig().getInitParameter("user");
        String password=getServletConfig().getInitParameter("password");
        if(userID.equals(user) && password.equals(pwd)){
            req.setAttribute("user",user);
            req.getRequestDispatcher("LoginSuccess.jsp").forward(req,resp);

        }
        else{
            RequestDispatcher rd= getServletContext().getRequestDispatcher("./login.html");
            PrintWriter out=resp.getWriter();
            out.println("<font color=red>Either username or password is wrong.</font>");
            rd.include(req,resp);
        }
    }

    private boolean checkPassword(HttpServletRequest req, HttpServletResponse resp, boolean validatePassword) throws IOException {
        if(validatePassword == false){
            RequestDispatcher rd = getServletContext().getRequestDispatcher("/login.jsp");
            PrintWriter out = resp.getWriter();
            out.println("<font color=red>Invalid Password: Password - Rule1 – minimum 8 Characters - Rule2 – Should have at least 1 UpperCase- Rule3 – Should have at least 1 numeric number in the password - Rule4 – Has exactly 1 Special Character</font>");
            return false;
        }
        return true;
    }


    private boolean validatePassword(String pwd) {
        Pattern check = Pattern.compile(PASSWORD_PATTERN);
        boolean value = check.matcher(pwd).matches();
        return value;
    }

    private boolean checkFirstName(HttpServletRequest request, HttpServletResponse response, boolean validateFirstName) throws IOException, ServletException {
        if (validateFirstName==false){
            RequestDispatcher rd=getServletContext().getRequestDispatcher("/login.jsp");
            PrintWriter out=response.getWriter();
            out.println("<font color=red>user name is Incorrect</font>");
            rd.include(request,response);
            return false;
        }
        return true;
    }

    private boolean validateFirstName(String firstName) {


        Pattern check= Pattern.compile(FIRST_NAME_PATTERN);
        boolean value=check.matcher(firstName).matches();
        return value;
    }
}

