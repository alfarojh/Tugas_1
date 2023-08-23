import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        MemberController memberController = new MemberController();
        while (true) {
            int pilihan = JOptionPane.showOptionDialog(null,
                    "Pilih pilihan Anda: ",
                    "Menu", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE,
                    null,
                    new Object[]{"Tambah", "Hapus", "Tampilkan"},
                    "Pilihan 1");

            if (pilihan == -1) break;
            else if (pilihan == 0) {
                String name = JOptionPane.showInputDialog("Masukkan nama: ");
                String address = JOptionPane.showInputDialog("Masukkan alamat: ");
                String phone = JOptionPane.showInputDialog("Masukkan no. telp: ");

                memberController.addMember(name, address, phone);
            } else if (pilihan == 1) {
                String name = JOptionPane.showInputDialog("Masukkan nama yang ingin dihapus: ");
                memberController.removeMember(memberController.getIDByName(name));
            } else if (pilihan == 2) {
                JOptionPane.showMessageDialog(null, memberController.getListMember());
            }
        }

    }
}