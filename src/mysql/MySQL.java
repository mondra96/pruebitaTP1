/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mysql;

import Views.AddCourse;
import Views.CoursesList;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;

/**
 *
 * @author Jorge
 */
public class MySQL {

    private Connection connection;
    private Statement statetment;
    private ResultSet resultSet;

    public void connectionToDataBase() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/professorscourses", "root", "");
            System.out.println("Conexión Exitosa");
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(MySQL.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void createProfessorsTable() {
        try {
            String Query = "CREATE TABLE professors "
                    + "(name VARCHAR(50),"
                    + "lastName VARCHAR(50), "
                    + "salary INT, "
                    + "identityCard INT NOT NULL AUTO_INCREMENT,"
                    + "PRIMARY KEY (identityCard))";
            statetment = connection.createStatement();
            statetment.executeUpdate(Query);
        } catch (SQLException ex) {
            Logger.getLogger(MySQL.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void createCoursesTable() {
        try {
            String Query = "CREATE TABLE courses "
                    + "(name VARCHAR(25),"
                    + "acronym VARCHAR(50), "
                    + "professorId INT, "
                    + "credits INT)";
            statetment = connection.createStatement();
            statetment.executeUpdate(Query);
        } catch (SQLException ex) {
            Logger.getLogger(MySQL.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void addProfessor(String name, String lastName, String identityCard, int salary) {
        try {
            String Query = "INSERT INTO professors VALUES("
                    + "\"" + name + "\", "
                    + "\"" + lastName + "\", "
                    + "\"" + salary + "\", "
                    + "\"" + identityCard + "\")";
            statetment = connection.createStatement();
            statetment.executeUpdate(Query);
            JOptionPane.showMessageDialog(null, "Profesor Agregado");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(null, "No se permiten numeros en este espacio", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(null, "No se permiten letras en este espacio", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void addCourse(String name, String acronym, int professorId, int credits) {
        try {
            String Query = "INSERT INTO courses VALUES("
                    + "\"" + name + "\", "
                    + "\"" + acronym + "\", "
                    + "\"" + professorId + "\", "
                    + "\"" + credits + "\")";
            statetment = connection.createStatement();
            statetment.executeUpdate(Query);
            JOptionPane.showMessageDialog(null, "Curso Agregado");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(null, "No se permiten numeros en este espacio", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(null, "No se permiten letras en este espacio", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public String getProfessor(String id) {
        String professorToString = "";
        try {
            String Query = "SELECT * FROM professors "
                    + "WHERE identityCard= \"" + id + "\"";
            statetment = connection.createStatement();

            resultSet = statetment.executeQuery(Query);
            while (resultSet.next()) {
                professorToString += ("Nombre: " + resultSet.getString("name") + " "
                        + resultSet.getString("lastName")
                        + "\nCedula: " + resultSet.getString("identityCard") + " "
                        + "\nSalario: " + resultSet.getInt("salary"));
            }
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
        return professorToString;
    }

    public String getCourse(String acronym) {
        String courseToString = "";
        try {
            String Query = "SELECT * FROM courses "
                    + "WHERE acronym= \"" + acronym + "\"";
            statetment = connection.createStatement();

            resultSet = statetment.executeQuery(Query);
            while (resultSet.next()) {
                courseToString += ("Nombre: " + resultSet.getString("name")
                        + "\nSiglas: " + resultSet.getString("acronym") + " "
                        + "\nCreditos: " + resultSet.getString("credits") + " "
                        + "\n°°Profesor°°\n" + getProfessor(resultSet.getInt("professorId") + ""));
            }
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
        return courseToString;
    }

    public void getProfessorsList() {
        try {
            String Query = "SELECT * FROM professors ";
            statetment = connection.createStatement();

            resultSet = statetment.executeQuery(Query);
            while (resultSet.next()) {
                AddCourse.getProfessorsList().addItem(resultSet.getInt("identityCard"));
            }
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
    }

    public void getCoursesList() {
        try {
            String Query = "SELECT * FROM courses ";
            statetment = connection.createStatement();

            resultSet = statetment.executeQuery(Query);
            DefaultListModel model = new DefaultListModel();
            while (resultSet.next()) {
                model.addElement(resultSet.getString("acronym"));
            }
            CoursesList.getProfessorsList().setModel(model);
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
    }

    public void deleteCourse(String acronym) {
        try {
            String Query = "DELETE FROM courses WHERE acronym = \"" + acronym + "\"";
            statetment = connection.createStatement();
            statetment.executeUpdate(Query);
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public void UpdateCourse(String table_name, String id) {
        try {
            String Query = "UPDATE " + table_name + " SET id = \"" + id + "\"";
            statetment = connection.createStatement();
            statetment.executeUpdate(Query);
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
}
