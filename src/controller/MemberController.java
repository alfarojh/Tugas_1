package controller;

import service.MemberServices;
import custom_exception.BackToMenuException;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;

public class MemberController {
    private static final MemberServices MEMBER_SERVICES = new MemberServices();
    private static int IDMember = -1;

    public MemberController() {
        MEMBER_SERVICES.addMember("Udin", "Bandung", "082312342321");
        MEMBER_SERVICES.addMember("Santi", "Jakarta", "089238483934");
        MEMBER_SERVICES.addMember("Budi", "Surabaya", "08523892383");
        MEMBER_SERVICES.addMember("Susi", "Medan", "082373743432");
    }
    //========================================== CRUD =============================================

    /**
     * Fungsi untuk menambahkan anggota baru.
     * Menerima input nama, alamat, dan nomor telepon dari pengguna,
     * lalu memanggil fungsi addMember dari memberController.
     * Menampilkan pesan kesuksesan atau kegagalan tambah anggota.
     */
    public void addMember() {
        try {
            String name = validateInputName("Masukkan nama: ");
            String address = validateInput("Masukkan alamat: ");
            String phoneNumber = validatePhoneNumber("Masukkan no. telp: ");

            if (MEMBER_SERVICES.addMember(name, address, phoneNumber)) {
                JOptionPane.showMessageDialog(null, "Anggota Berhasil ditambahkan.");
            } else {
                JOptionPane.showMessageDialog(null, "Anggota Gagal Ditambahkan.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (BackToMenuException e) {
//            JOptionPane.showMessageDialog(null, "Kembali ke Menu Utama");
        }
    }

    /**
     * Fungsi untuk menghapus anggota berdasarkan ID.
     * Meminta input ID dari pengguna dan memeriksa validitasnya.
     * Memanggil fungsi removeMember dari memberController.
     * Menampilkan pesan kesuksesan atau kegagalan penghapusan anggota.
     */
    public void removeMember() {
        try {
            if (IDMember < 0) throw new BackToMenuException();

            if (MEMBER_SERVICES.isMemberExistByID(IDMember)) {
                int choice = JOptionPane.showConfirmDialog(null, "Apakah Anda yakin?", "Konfirmasi", JOptionPane.YES_NO_OPTION);
                if (choice == JOptionPane.NO_OPTION) return;

                if (MEMBER_SERVICES.removeMember(IDMember)) {
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
    public void updateMember() {
        try {
            if (IDMember < 0) throw new BackToMenuException();
            else if (MEMBER_SERVICES.isMemberExistByID(IDMember)) {
                int choice = JOptionPane.showOptionDialog(null,
                        "ID Model.Member: " + IDMember +
                                "\nNama Model.Member: " + MEMBER_SERVICES.getNameByID(IDMember) +
                                "\nPilih yang ingin di update: ",
                        "Menu", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE,
                        null,
                        new String[]{"Nama", "Alamat", "No. Telp"},
                        "Pilihan 1");

                switch (choice) {
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
    private void updateMemberName(int id) throws BackToMenuException {
        try {
            String name = validateInputName("Masukkan nama baru: ");
            if (MEMBER_SERVICES.updateMemberName(id, name)) {
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
    private void updateMemberAddress(int id) throws BackToMenuException {
        try {
            String address = validateInput("Masukkan alamat baru: ");
            if (MEMBER_SERVICES.updateMemberAddress(id, address)) {
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
    private void updateMemberPhoneNumber(int id) throws BackToMenuException {
        try {
            String phoneNumber = validatePhoneNumber("Masukkan nomor telepon baru: ");

            if (MEMBER_SERVICES.updateMemberPhone(id, phoneNumber)) {
                JOptionPane.showMessageDialog(null, "Nomor Telepon Berhasil di Update.");
            } else {
                throw new BackToMenuException();
            }
        } catch (BackToMenuException e) {
            JOptionPane.showMessageDialog(null, "Nomor Telepon Gagal di Update.");
        }
    }

    /**
     * Fungsi untuk menampilkan daftar anggota dalam bentuk tabel dengan opsi aksi.
     * Membuat JTable dari data anggota yang diperoleh dari MEMBER_SERVICES.
     * Menentukan lebar kolom dan tampilannya, lalu menampilkannya menggunakan dialog JOptionPane.
     * Memberikan opsi aksi: "Tambah", "Hapus", "Update".
     * Menggunakan listSelectionModel untuk mendapatkan ID anggota yang dipilih saat baris dipilih.
     * Mengembalikan indeks opsi yang dipilih dari dialog option.
     *
     * @return Indeks opsi yang dipilih dari dialog option.
     */
    public int showMembers() {
        JTable table = new JTable(
                MEMBER_SERVICES.getListMembers(),
                new String[]
                        { "ID", "Nama", "Alamat", "No Telp" });
        table.getColumnModel().getColumn(0).setMinWidth(0);
        table.getColumnModel().getColumn(0).setMaxWidth(0);
        table.getColumnModel().getColumn(1).setPreferredWidth(180);
        table.getColumnModel().getColumn(2).setPreferredWidth(220);
        table.getColumnModel().getColumn(3).setPreferredWidth(100);
        table.setDefaultEditor(Object.class, null);

        table.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

                if (isSelected) {
                    c.setBackground(new Color(50, 253, 50, 255));
                }else if (row % 2 == 1) {
                    c.setBackground(new Color(216, 216, 216));
                } else {
                    c.setBackground(Color.WHITE);
                }


                return c;
            }
        });

        ListSelectionModel selectionModel = table.getSelectionModel();
        selectionModel.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                if (table.getSelectedRow() >= 0) {
                    table.getSelectionModel().setSelectionInterval(table.getSelectedRow(), table.getSelectedRow());
                    IDMember = Integer.parseInt(String.valueOf(table.getValueAt(table.getSelectedRow(), 0)));
                }
            }
        });

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setPreferredSize(new Dimension(500, 300));

        return JOptionPane.showOptionDialog(null,
                scrollPane,
                "Menu", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE,
                null,
                new String[]{"Tambah", "Hapus", "Update"},
                "Pilihan 1");
    }

    //========================================== CRUD =============================================

    /**
     * Fungsi untuk memvalidasi input teks.
     * Meminta input teks dari pengguna melalui dialog JOptionPane.
     * Melakukan validasi terhadap input dengan beberapa aturan:
     * - Jika pengguna menutup atau membatalkan JOptionPane, lemparkan BackToMenuException.
     * - Jika input kosong setelah di-trim, tampilkan pesan error.
     * Jika input memenuhi aturan validasi, fungsi akan mengembalikan input.
     *
     * @param message Pesan yang akan ditampilkan saat meminta input teks.
     * @return Input teks yang valid.
     * @throws BackToMenuException Jika pengguna menutup atau membatalkan JOptionPane.
     */
    private String validateInput(String message) throws BackToMenuException{
        String input;
        while (true) {
            input = JOptionPane.showInputDialog(message);

            if (input == null) { // Jika pengguna menutup atau membatalkan JOptionPane
                throw new BackToMenuException();
            } else if (input.trim().equals("")) {
                JOptionPane.showMessageDialog(null,
                        "Input Tidak Boleh Kosong!", "Error", JOptionPane.ERROR_MESSAGE);
            } else break;
        }
        return input.trim();
    }

    /**
     * Fungsi untuk memvalidasi nomor telepon.
     * Meminta input nomor telepon dari pengguna melalui fungsi validateInput.
     * Melakukan validasi terhadap nomor telepon dengan beberapa aturan:
     * - Jika nomor telepon tidak berupa bilangan bulat positif, tampilkan pesan error.
     * - Jika panjang nomor telepon tidak dalam rentang 10-13 digit, tampilkan pesan error.
     * Jika nomor telepon memenuhi aturan validasi, fungsi akan mengembalikan nomor telepon.
     *
     * @param message Pesan yang akan ditampilkan saat meminta input nomor telepon.
     * @return Nomor telepon yang valid.
     * @throws BackToMenuException Jika pengguna menutup atau membatalkan JOptionPane.
     */
    private String validatePhoneNumber(String message) throws BackToMenuException {
        String phoneNumber;
        while (true) {
            phoneNumber = validateInput(message);

            if (!phoneNumber.matches("\\d+")) { // Cek apakah nomor telepon berupa bilangan bulat positif
                JOptionPane.showMessageDialog(null, "Harus Berupa Angka!", "Error", JOptionPane.ERROR_MESSAGE);
            } else if (phoneNumber.length() < 10 || phoneNumber.length() > 13) {
                JOptionPane.showMessageDialog(null, "Harus berupa 10-13 digit!", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                break;
            }
        }
        return phoneNumber;
    }

    /**
     * Fungsi untuk memvalidasi nama.
     * Meminta input nama dari pengguna melalui fungsi validateInput.
     * Melakukan validasi terhadap nama dengan beberapa aturan:
     * - Jika nama mengandung simbol atau karakter khusus selain huruf dan angka, tampilkan pesan error.
     * Jika nama memenuhi aturan validasi, fungsi akan mengembalikan nama.
     *
     * @param message Pesan yang akan ditampilkan saat meminta input nama.
     * @return Nama yang valid.
     * @throws BackToMenuException Jika pengguna menutup atau membatalkan JOptionPane.
     */
    private String validateInputName(String message) throws BackToMenuException {
        String name;
        while (true) {
            name = validateInput(message);

            if (!name.matches("[a-zA-Z0-9\\s]+")) {
                JOptionPane.showMessageDialog(null,
                        "Nama tidak boleh menggunakan simbol!", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                break;
            }
        }
        return name;
    }

    /**
     * Fungsi untuk mengatur ulang nilai IDMember menjadi -1.
     * Berguna untuk menghapus nilai IDMember yang sebelumnya dipilih.
     */
    public void refreshIDMember() {
        IDMember = -1;
    }

}
