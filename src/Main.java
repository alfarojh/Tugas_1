import controller.MemberController;

import javax.swing.*;

public class Main {

    public static void main(String[] args) {
        MemberController memberController = new MemberController();
        while (true) {
            int choice = memberController.showMembers();

            if (choice == -1) {
                choice = JOptionPane.showConfirmDialog(null,
                        "Apakah Anda yakin?", "Konfirmasi", JOptionPane.YES_NO_OPTION);

                if (choice == JOptionPane.YES_OPTION) break;
            }
            else if (choice == 0) memberController.addMember();
            else if (choice == 1) memberController.removeMember();
            else if (choice == 2) memberController.updateMember();
            memberController.refreshIDMember();
        }
    }

}