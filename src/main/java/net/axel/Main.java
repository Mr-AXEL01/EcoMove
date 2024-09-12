package net.axel;

import net.axel.presentations.AdminMenu;
import net.axel.presentations.UserMenu;

import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws SQLException {

        System.out.println("                                                                                                                                                   \n" +
                "                                                                                                                                                   \n" +
                "EEEEEEEEEEEEEEEEEEEEEE                                     MMMMMMMM               MMMMMMMM                                                         \n" +
                "E::::: W O R A ::::::E                                     M:::::::M             M:::::::M                                                         \n" +
                "E::::::::::::::::::::E                                     M::::::::M           M::::::::M                                                         \n" +
                "EE::::::EEEEEEEEE::::E                                     M:::::::::M         M:::::::::M                                                         \n" +
                "  E:::::E       EEEEEE    cccccccccccccccc   ooooooooooo   M::::::::::M       M::::::::::M   ooooooooooo vvvvvvv           vvvvvvv eeeeeeeeeeee    \n" +
                "  E:::::E               cc:::::::::::::::c oo:::::::::::oo M:::::::::::M     M:::::::::::M oo:::::::::::oov:::::v         v:::::vee::::::::::::ee  \n" +
                "  E::::::EEEEEEEEEE    c:::::::::::::::::co:::::::::::::::oM:::::::M::::M   M::::M:::::::Mo:::::::::::::::ov:::::v       v:::::ve::::::eeeee:::::ee\n" +
                "  E:::::::::::::::E   c:::::::cccccc:::::co:::::ooooo:::::oM::::::M M::::M M::::M M::::::Mo:::::ooooo:::::o v:::::v     v:::::ve::::::e     e:::::e\n" +
                "  E:::::::::::::::E   c::::::c     ccccccco::::o     o::::oM::::::M  M::::M::::M  M::::::Mo::::o     o::::o  v:::::v   v:::::v e:::::::eeeee::::::e\n" +
                "  E::::::EEEEEEEEEE   c:::::c             o::::o     o::::oM::::::M   M:::::::M   M::::::Mo::::o     o::::o   v:::::v v:::::v  e:::::::::::::::::e \n" +
                "  E:::::E             c:::::c             o::::o     o::::oM::::::M    M:::::M    M::::::Mo::::o     o::::o    v:::::v:::::v   e::::::eeeeeeeeeee  \n" +
                "  E:::::E       EEEEEEc::::::c     ccccccco::::o     o::::oM::::::M     MMMMM     M::::::Mo::::o     o::::o     v:::::::::v    e:::::::e           \n" +
                "EE::::::EEEEEEEE:::::Ec:::::::cccccc:::::co:::::ooooo:::::oM::::::M               M::::::Mo:::::ooooo:::::o      v:::::::v     e::::::::e          \n" +
                "E::::::::::::::::::::E c:::::::::::::::::co:::::::::::::::oM::::::M               M::::::Mo:::::::::::::::o       v:::::v       e::::::::eeeeeeee  \n" +
                "E::::::::::::::::::::E  cc:::::::::::::::c oo:::::::::::oo M::::::M               M::::::M oo:::::::::::oo         v:::v         ee:::::::::::::e  \n" +
                "EEEEEEEEEEEEEEEEEEEEEE    cccccccccccccccc   ooooooooooo   MMMMMMMM               MMMMMMMM   ooooooooooo            vvv            eeeeeeeeeeeeee  \n" +
                "                                                                                                                                                   \n" +
                "                                                                                                                                                   \n" +
                "                                                                                                                                                   ");


        try {
            UserMenu menu = new UserMenu();
            menu.displayMenu();
        } catch (SQLException e) {
            System.err.println("Error initializing the application: " + e.getMessage());
        }
    }
}