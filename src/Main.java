import javax.swing.*;
import java.awt.*;

public class Main {
    private static final MemberController memberController = new MemberController();
    public static void main(String[] args) {
        memberController.addMember("Udin", "Bandung", "082312342321");
        memberController.addMember("Santi", "Jakarta", "089238483934");
        memberController.addMember("Budi", "Surabaya", "08523892383");

        while (true) {
            int pilihan = JOptionPane.showOptionDialog(null,
                    "Pilih pilihan Anda: ",
                    "Menu", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE,
                    null,
                    new String[]{"Tambah", "Hapus", "Update", "Tampilkan"},
                    "Pilihan 1");

            if (pilihan == -1) {
                JOptionPane.showMessageDialog(null, "Keluar dari program.");
                break;
            }
            else if (pilihan == 0) addMember();
            else if (pilihan == 1) removeMember();
            else if (pilihan == 2) updateMember();
            else if (pilihan == 3) showMembers();
        }
    }
    
    //========================================== CRUD =============================================

    /**
     * Fungsi untuk menambahkan anggota baru.
     * Menerima input nama, alamat, dan nomor telepon dari pengguna,
     * lalu memanggil fungsi addMember dari memberController.
     * Menampilkan pesan kesuksesan atau kegagalan tambah anggota.
     */
    private static void addMember() {
        try {
            String name = validateInput("Masukkan nama: ");
            String address = validateInput("Masukkan alamat: ");
            String phoneNumber = validatePhoneNumber("Masukkan no. telp: ");

            if (memberController.addMember(name, address, phoneNumber)) {
                JOptionPane.showMessageDialog(null, "Anggota Berhasil ditambahkan.");
            } else {
                JOptionPane.showMessageDialog(null, "Anggota Gagal Ditambahkan.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (ExceptionBackToMenu e) {
            JOptionPane.showMessageDialog(null, "Kembali ke Menu Utama");
        }
    }

    /**
     * Fungsi untuk menghapus anggota berdasarkan ID.
     * Meminta input ID dari pengguna dan memeriksa validitasnya.
     * Memanggil fungsi removeMember dari memberController.
     * Menampilkan pesan kesuksesan atau kegagalan penghapusan anggota.
     */
    private static void removeMember() {
        try {
            String id = validateInput("Masukkan ID yang ingin dihapus: ");

            if (!id.matches("\\d+")) {
                JOptionPane.showMessageDialog(null, "Input harus berupa angka!", "Error", JOptionPane.ERROR_MESSAGE);
                removeMember();
            } else if (memberController.isMemberExistByID(Integer.parseInt(id))) {
                if (memberController.removeMember(Integer.parseInt(id))) {
                    JOptionPane.showMessageDialog(null, "Anggota Berhasil dihapus.");
                } else {
                    JOptionPane.showMessageDialog(null, "Anggota Gagal Dihapus.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(null,
                        "ID Tidak Ditemukan\n" +
                                "Kembali ke Menu Utama");
            }

        } catch (ExceptionBackToMenu e) {
//        JOptionPane.showMessageDialog(null, "Kembali ke Menu Utama");
        }
    }

    /**
     * Fungsi untuk memperbarui data anggota.
     * Meminta input ID anggota yang ingin diperbarui.
     * Memeriksa validitas ID dan menampilkan menu pilihan update.
     * Menjalankan fungsi update sesuai dengan pilihan pengguna.
     */
    private static void updateMember() {
        try {
            String id = validateInput("Masukkan ID yang ingin diupdate: ");

            if (!id.matches("\\d+")) {
                JOptionPane.showMessageDialog(null, "Input harus berupa angka!", "Error", JOptionPane.ERROR_MESSAGE);
                updateMember();
            } else if (memberController.isMemberExistByID(Integer.parseInt(id))) {
                int pilihan = JOptionPane.showOptionDialog(null,
                        "ID Member: " + id +
                                "\nNama Member: " + memberController.getNameByID(Integer.parseInt(id)) +
                                "\nPilih yang ingin di update: ",
                        "Menu", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE,
                        null,
                        new String[]{"Nama", "Alamat", "No. Telp"},
                        "Pilihan 1");

                switch (pilihan) {
                    case -1 -> JOptionPane.showMessageDialog(null, "Kembali ke Menu Utama");
                    case 0 -> updateMemberName(Integer.parseInt(id));
                    case 1 -> updateMemberAddress(Integer.parseInt(id));
                    case 2 -> updateMemberPhoneNumber(Integer.parseInt(id));
                }
            } else {
                JOptionPane.showMessageDialog(null,
                        "ID Tidak Ditemukan\n" +
                                "Kembali ke Menu Utama");
            }
        } catch (ExceptionBackToMenu e) {
//        JOptionPane.showMessageDialog(null, "Kembali ke Menu Utama.");
        }
    }

    /**
     * Fungsi untuk memperbarui nama anggota berdasarkan ID.
     * Meminta input nama baru dari pengguna dan memanggil fungsi updateMemberName dari memberController.
     * Menampilkan pesan kesuksesan atau kegagalan pembaruan nama.
     *
     * @param id ID anggota yang namanya akan diperbarui.
     * @throws ExceptionBackToMenu Jika terjadi kesalahan yang mengharuskan kembali ke menu utama.
     */
    private static void updateMemberName(int id) throws ExceptionBackToMenu{
        try {
            String name = validateInput("Masukkan nama baru: ");
            if (memberController.updateMemberName(id, name)) {
                JOptionPane.showMessageDialog(null, "Nama Berhasil di Update.");
            } else {
                throw new ExceptionBackToMenu();
            }
        } catch (ExceptionBackToMenu e) {
            JOptionPane.showMessageDialog(null, "Nama Gagal di Update.");
            throw new ExceptionBackToMenu();
        }
    }

    /**
     * Fungsi untuk memperbarui alamat anggota berdasarkan ID.
     * Meminta input alamat baru dari pengguna dan memanggil fungsi updateMemberAddress dari memberController.
     * Menampilkan pesan kesuksesan atau kegagalan pembaruan alamat.
     *
     * @param id ID anggota yang alamatnya akan diperbarui.
     * @throws ExceptionBackToMenu Jika terjadi kesalahan yang mengharuskan kembali ke menu utama.
     */
    private static void updateMemberAddress(int id) throws ExceptionBackToMenu{
        try {
            String address = validateInput("Masukkan alamat baru: ");
            if (memberController.updateMemberAddress(id, address)) {
                JOptionPane.showMessageDialog(null, "Alamat Berhasil di Update.");
            } else {
                throw new ExceptionBackToMenu();
            }
        } catch (ExceptionBackToMenu e) {
            JOptionPane.showMessageDialog(null, "Alamat Gagal di Update.");
            throw new ExceptionBackToMenu();
        }
    }

    /**
     * Fungsi untuk memperbarui nomor telepon anggota berdasarkan ID.
     * Meminta input nomor telepon baru dari pengguna dan memanggil fungsi updateMemberPhone dari memberController.
     * Menampilkan pesan kesuksesan atau kegagalan pembaruan nomor telepon.
     *
     * @param id ID anggota yang nomor teleponnya akan diperbarui.
     * @throws ExceptionBackToMenu Jika terjadi kesalahan yang mengharuskan kembali ke menu utama.
     */
    private static void updateMemberPhoneNumber(int id) throws ExceptionBackToMenu{
        try {
            String phoneNumber = validatePhoneNumber("Masukkan nomor telepon baru: ");

            if (memberController.updateMemberPhone(id, phoneNumber)) {
                JOptionPane.showMessageDialog(null, "Nomor Telepon Berhasil di Update.");
            } else {
                throw new ExceptionBackToMenu();
            }
        } catch (ExceptionBackToMenu e) {
            JOptionPane.showMessageDialog(null, "Nomor Telepon Gagal di Update.");
            throw new ExceptionBackToMenu();
        }
    }

    /**
     * Fungsi untuk menampilkan daftar anggota dalam bentuk tabel.
     * Membuat JTable dari data anggota yang diperoleh dari memberController.
     * Menentukan lebar kolom dan tampilannya, lalu menampilkannya menggunakan dialog JOptionPane.
     */
    private static void showMembers() {
        JTable table = new JTable(
                memberController.getListMembers(),
                new String[]
                        { "ID", "Nama", "Alamat", "No Telp" });
        table.setEnabled(false);
        table.getColumnModel().getColumn(0).setPreferredWidth(30);
        table.getColumnModel().getColumn(1).setPreferredWidth(160);
        table.getColumnModel().getColumn(2).setPreferredWidth(210);
        table.getColumnModel().getColumn(3).setPreferredWidth(100);

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setPreferredSize(new Dimension(500, 300));

        JOptionPane.showMessageDialog(null, scrollPane, "Daftar Anggota", JOptionPane.PLAIN_MESSAGE);
    }

    //========================================== CRUD =============================================

    /**
     * Fungsi untuk memvalidasi nomor telepon.
     * Meminta input nomor telepon dari pengguna melalui dialog JOptionPane.
     * Melakukan validasi terhadap input nomor telepon dengan beberapa aturan:
     * - Jika input adalah "null", kembali ke menu utama.
     * - Jika input kosong, tampilkan pesan error.
     * - Jika input bukan angka, tampilkan pesan error.
     * Jika input memenuhi aturan validasi, fungsi akan mengembalikan nomor telepon.
     *
     * @param message Pesan yang akan ditampilkan saat meminta input nomor telepon.
     * @return Nomor telepon yang valid.
     * @throws ExceptionBackToMenu Jika terjadi kesalahan yang mengharuskan kembali ke menu utama.
     */
    private static String validatePhoneNumber(String message) throws ExceptionBackToMenu{
        String phoneNumber = JOptionPane.showInputDialog(message);
        while (true) {
            if (String.valueOf(phoneNumber).equals("null")) {
                throw new ExceptionBackToMenu();
            } else if (phoneNumber.equals("")) {
                JOptionPane.showMessageDialog(null,
                        "Input Tidak Boleh Kosong!", "Error", JOptionPane.ERROR_MESSAGE);
            } else if (!phoneNumber.matches("\\d+")) {
                JOptionPane.showMessageDialog(null, "Harus Berupa Angka!", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                break;
            }

            phoneNumber = JOptionPane.showInputDialog(message);
        }
        return phoneNumber;
    }

    /**
     * Fungsi untuk memvalidasi input umum.
     * Meminta input dari pengguna melalui dialog JOptionPane.
     * Melakukan validasi terhadap input dengan beberapa aturan:
     * - Jika input adalah "null", kembali ke menu utama.
     * - Jika input kosong, tampilkan pesan error.
     * Jika input memenuhi aturan validasi, fungsi akan mengembalikan input.
     *
     * @param message Pesan yang akan ditampilkan saat meminta input.
     * @return Input yang valid.
     * @throws ExceptionBackToMenu Jika terjadi kesalahan yang mengharuskan kembali ke menu utama.
     */
    private static String validateInput(String message) throws ExceptionBackToMenu {
        String input = JOptionPane.showInputDialog(message);
        if (String.valueOf(input).equals("null")) {
            throw new ExceptionBackToMenu();
        } else if (input.equals("")) {
            JOptionPane.showMessageDialog(null,
                    "Input Tidak Boleh Kosong!", "Error", JOptionPane.ERROR_MESSAGE);
        }
        return input;
    }
}