/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mysql;

import Views.Menu;

public class MySQL_Example {
    
    private static MySQL myDataBase = new MySQL();

    public static void main(String[] args) {
        Menu menu =  new Menu(myDataBase);
        menu.setVisible(true);
        myDataBase.connectionToDataBase();
//        myDataBase.createCoursesTable();
//        myDataBase.createProfessorsTable();
    }
    
}
