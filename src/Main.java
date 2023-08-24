import javax.swing.*;
import java.awt.*;

public class Main {
    private static final MemberController memberController = new MemberController();
    private static int IDMember = -1;
    public static void main(String[] args) {
        memberController.addMember("Udin", "Bandung", "082312342321");
        memberController.addMember("Santi", "Jakarta", "089238483934");
        memberController.addMember("Budi", "Surabaya", "08523892383");

        while (true) {
            JTable table = new JTable(
                    memberController.getListMembers(),
                    new String[]
                            { "ID", "Nama", "Alamat", "No Telp" });
            table.getColumnModel().getColumn(0).setWidth(0);
            table.getColumnModel().getColumn(0).setMinWidth(0);
            table.getColumnModel().getColumn(0).setMaxWidth(0);
            table.getColumnModel().getColumn(1).setPreferredWidth(180);
            table.getColumnModel().getColumn(2).setPreferredWidth(220);
            table.getColumnModel().getColumn(3).setPreferredWidth(100);

            ListSelectionModel selectionModel = table.getSelectionModel();
            selectionModel.addListSelectionListener(e -> {
                if (!e.getValueIsAdjusting()) {
                    IDMember = Integer.parseInt(String.valueOf(table.getValueAt(table.getSelectedRow(), 0)));
                }
            });

            JScrollPane scrollPane = new JScrollPane(table);
            scrollPane.setPreferredSize(new Dimension(500, 300));

            int choice = JOptionPane.showOptionDialog(null,
                    scrollPane,
                    "Menu", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE,
                    null,
                    new String[]{"Tambah", "Hapus", "Update"},
                    "Pilihan 1");

            if (choice == -1) {
                choice = JOptionPane.showConfirmDialog(null,
                        "Apakah Anda yakin?", "Konfirmasi", JOptionPane.YES_NO_OPTION);

                if (choice == JOptionPane.YES_OPTION) break;
            }
            else if (choice == 0) addMember();
            else if (choice == 1) removeMember();
            else if (choice == 2) updateMember();
            IDMember = -1;
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
        } catch (BackToMenuException e) {
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
            if (IDMember < 0) throw new BackToMenuException();

            if (memberController.isMemberExistByID(IDMember)) {
                int choice = JOptionPane.showConfirmDialog(null, "Apakah Anda yakin?", "Konfirmasi", JOptionPane.YES_NO_OPTION);
                if (choice == JOptionPane.NO_OPTION) return;

                if (memberController.removeMember(IDMember)) {
                    JOptionPane.showMessageDialog(null, "Anggota Berhasil dihapus.");
                } else {
                    JOptionPane.showMessageDialog(null, "Anggota Gagal Dihapus.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(null,
                        "ID Tidak Ditemukan\n" +
                                "Kembali ke Menu Utama");
            }

        } catch (BackToMenuException e) {
            JOptionPane.showMessageDialog(null, "Pilih kolom yang ingin dihapus!", "Error", JOptionPane.ERROR_MESSAGE);
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
            if (IDMember < 0) throw new BackToMenuException();
            if (memberController.isMemberExistByID(IDMember)) {
                int choice = JOptionPane.showOptionDialog(null,
                        "ID Member: " + IDMember +
                                "\nNama Member: " + memberController.getNameByID(IDMember) +
                                "\nPilih yang ingin di update: ",
                        "Menu", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE,
                        null,
                        new String[]{"Nama", "Alamat", "No. Telp"},
                        "Pilihan 1");

                switch (choice) {
                    case -1 -> JOptionPane.showMessageDialog(null, "Kembali ke Menu Utama");
                    case 0 -> updateMemberName(IDMember);
                    case 1 -> updateMemberAddress(IDMember);
                    case 2 -> updateMemberPhoneNumber(IDMember);
                }
            } else {
                JOptionPane.showMessageDialog(null,
                        "ID Tidak Ditemukan\n" +
                                "Kembali ke Menu Utama");
            }
        } catch (BackToMenuException e) {
            JOptionPane.showMessageDialog(null, "Pilih kolom yang ingin diupdate!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Fungsi untuk memperbarui nama anggota berdasarkan ID.
     * Meminta input nama baru dari pengguna dan memanggil fungsi updateMemberName dari memberController.
     * Menampilkan pesan kesuksesan atau kegagalan pembaruan nama.
     *
     * @param id ID anggota yang namanya akan diperbarui.
     * @throws BackToMenuException Jika terjadi kesalahan yang mengharuskan kembali ke menu utama.
     */
    private static void updateMemberName(int id) throws BackToMenuException {
        try {
            String name = validateInput("Masukkan nama baru: ");
            if (memberController.updateMemberName(id, name)) {
                JOptionPane.showMessageDialog(null, "Nama Berhasil di Update.");
            } else {
                throw new BackToMenuException();
            }
        } catch (BackToMenuException e) {
            JOptionPane.showMessageDialog(null, "Nama Gagal di Update.");
        }
    }

    /**
     * Fungsi untuk memperbarui alamat anggota berdasarkan ID.
     * Meminta input alamat baru dari pengguna dan memanggil fungsi updateMemberAddress dari memberController.
     * Menampilkan pesan kesuksesan atau kegagalan pembaruan alamat.
     *
     * @param id ID anggota yang alamatnya akan diperbarui.
     * @throws BackToMenuException Jika terjadi kesalahan yang mengharuskan kembali ke menu utama.
     */
    private static void updateMemberAddress(int id) throws BackToMenuException {
        try {
            String address = validateInput("Masukkan alamat baru: ");
            if (memberController.updateMemberAddress(id, address)) {
                JOptionPane.showMessageDialog(null, "Alamat Berhasil di Update.");
            } else {
                throw new BackToMenuException();
            }
        } catch (BackToMenuException e) {
            JOptionPane.showMessageDialog(null, "Alamat Gagal di Update.");
        }
    }

    /**
     * Fungsi untuk memperbarui nomor telepon anggota berdasarkan ID.
     * Meminta input nomor telepon baru dari pengguna dan memanggil fungsi updateMemberPhone dari memberController.
     * Menampilkan pesan kesuksesan atau kegagalan pembaruan nomor telepon.
     *
     * @param id ID anggota yang nomor teleponnya akan diperbarui.
     * @throws BackToMenuException Jika terjadi kesalahan yang mengharuskan kembali ke menu utama.
     */
    private static void updateMemberPhoneNumber(int id) throws BackToMenuException {
        try {
            String phoneNumber = validatePhoneNumber("Masukkan nomor telepon baru: ");

            if (memberController.updateMemberPhone(id, phoneNumber)) {
                JOptionPane.showMessageDialog(null, "Nomor Telepon Berhasil di Update.");
            } else {
                throw new BackToMenuException();
            }
        } catch (BackToMenuException e) {
            JOptionPane.showMessageDialog(null, "Nomor Telepon Gagal di Update.");
        }
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
     * @throws BackToMenuException Jika terjadi kesalahan yang mengharuskan kembali ke menu utama.
     */
    private static String validatePhoneNumber(String message) throws BackToMenuException {
        String phoneNumber = JOptionPane.showInputDialog(message);
        while (true) {
            if (String.valueOf(phoneNumber).equals("null")) {
                throw new BackToMenuException();
            } else if (phoneNumber.equals("")) {
                JOptionPane.showMessageDialog(null,
                        "Input Tidak Boleh Kosong!", "Error", JOptionPane.ERROR_MESSAGE);
            } else if (!phoneNumber.matches("\\d+")) { // Cek apakah nomor telepon berupa bilangan bulat positif
                JOptionPane.showMessageDialog(null, "Harus Berupa Angka!", "Error", JOptionPane.ERROR_MESSAGE);
            } else if (phoneNumber.length() < 10 || phoneNumber.length() > 13) {
                JOptionPane.showMessageDialog(null, "Harus berupa 10-13 digit!", "Error", JOptionPane.ERROR_MESSAGE);
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
     * @throws BackToMenuException Jika terjadi kesalahan yang mengharuskan kembali ke menu utama.
     */
    private static String validateInput(String message) throws BackToMenuException {
        String input = JOptionPane.showInputDialog(message);
        if (String.valueOf(input).equals("null")) { // Jika pengguna menutup atau membatalkan JOptionPane
            throw new BackToMenuException();
        } else if (input.equals("")) {
            JOptionPane.showMessageDialog(null,
                    "Input Tidak Boleh Kosong!", "Error", JOptionPane.ERROR_MESSAGE);
        }
        return input;
    }
}