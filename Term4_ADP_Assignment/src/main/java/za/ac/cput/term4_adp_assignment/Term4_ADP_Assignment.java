package za.ac.cput.term4_adp_assignment;

import za.ac.cput.term4_adp_assignment.Admin_Gui.adminMain;
import za.ac.cput.term4_adp_assignment.Admin_Gui.courseManage;
import za.ac.cput.term4_adp_assignment.Admin_Gui.enrollMan;
import static za.ac.cput.term4_adp_assignment.DBManager.DBStartup.initializeDB;
import za.ac.cput.term4_adp_assignment.Log_Sign_Guis.loginForm;
import za.ac.cput.term4_adp_assignment.Log_Sign_Guis.signForm;
import za.ac.cput.term4_adp_assignment.Student_Gui.dashboard;
import za.ac.cput.term4_adp_assignment.Student_Gui.registered;

public class Term4_ADP_Assignment {

    public static void main(String[] args) {
        //initializeDB();
        new loginForm();
    }
}
